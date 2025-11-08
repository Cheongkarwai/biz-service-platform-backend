package com.cheong.ecommerce_ai_driven.file;

import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.core.async.AsyncRequestBody;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.transfer.s3.S3TransferManager;
import software.amazon.awssdk.transfer.s3.model.UploadRequest;

@Service
public class S3StorageService implements FileUploadService {

    private final S3TransferManager s3TransferManager;

    public S3StorageService(S3TransferManager s3TransferManager) {
        this.s3TransferManager = s3TransferManager;
    }

    @Override
    public Mono<Void> uploadFile(String bucket, String directory, Mono<FilePart> filePartMono) {
        return filePartMono
                .flatMap(filePart -> Mono.fromFuture(s3TransferManager.upload(createUploadRequest(filePart.filename(), bucket, filePart))
                        .completionFuture())).flatMap(completedUpload -> {
                    if (!completedUpload.response().sdkHttpResponse().isSuccessful()) {
                        return Mono.error(new UploadFileException("Error occurred during uploading file.request Id" + completedUpload.response().responseMetadata().requestId()));
                    }
                    return Mono.empty();
                })
                .onErrorResume(err-> Mono.error(new UploadFileException(err.getMessage())))
                .then();
    }

    @Override
    public Flux<Void> uploadFiles(String bucket, String directory, Flux<FilePart> filePartFlux) {
        return filePartFlux
                .flatMap(filePart -> Mono.fromFuture(s3TransferManager.upload(createUploadRequest(filePart.filename(), bucket, filePart))
                        .completionFuture())).flatMap(completedUpload -> {
                    if (!completedUpload.response().sdkHttpResponse().isSuccessful()) {
                        return Mono.error(new UploadFileException("Error occurred during uploading file.request Id" + completedUpload.response().responseMetadata().requestId()));
                    }
                    return Mono.empty();
                })
                .onErrorResume(err-> Mono.error(new UploadFileException(err.getMessage())))
                .thenMany(Flux.empty());
    }

    private PutObjectRequest createPutObjectRequest(String bucket, String key, long contentLength, String contentType) {
        return PutObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .contentLength(contentLength)
                .contentType(contentType)
                .build();
    }

    private UploadRequest createUploadRequest(String key, String bucket, FilePart filePart) {
        String contentType = filePart.headers().getContentType() != null
                ? filePart.headers().getContentType().toString()
                : "application/octet-stream";
        return UploadRequest.builder()
                .putObjectRequest(createPutObjectRequest(bucket, key, filePart.headers().getContentLength(), contentType))
                .requestBody(AsyncRequestBody.fromPublisher(filePart.content().flatMapSequential(dataBuffer -> Flux.fromIterable(dataBuffer::readableByteBuffers))))
                .build();
    }
}
