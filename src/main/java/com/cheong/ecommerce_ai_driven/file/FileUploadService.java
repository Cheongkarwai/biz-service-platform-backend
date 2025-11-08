package com.cheong.ecommerce_ai_driven.file;

import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FileUploadService {


    Mono<Void> uploadFile(String bucket, String directory, Mono<FilePart> filePart);

    Flux<Void> uploadFiles(String bucket, String directory, Flux<FilePart> fileParts);
}
