package com.cheong.ecommerce_ai_driven.common.util;

import com.cheong.ecommerce_ai_driven.common.data.Connection;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ConnectionUtil {

    public static <S, T> Mono<Connection<T>> mapConnection(Connection<S> sourceConnection,
                                                     java.util.function.Function<S, T> mapper) {
        var edges = Flux.fromIterable(sourceConnection.getEdges())
                .map(edge -> mapEdge(edge, mapper))
                .collectList();

        return edges.map(edgeList -> createConnection(sourceConnection, edgeList));
    }

    public static <S, T> Connection<T>.Edge mapEdge(Connection<S>.Edge sourceEdge,
                                              java.util.function.Function<S, T> mapper) {
        Connection<T> connection = new Connection<>();
        Connection<T>.Edge edge = connection.new Edge();
        edge.setNode(mapper.apply(sourceEdge.getNode()));
        edge.setCursor(sourceEdge.getCursor());
        return edge;
    }

    public static <S, T> Connection<T> createConnection(Connection<S> sourceConnection,
                                                  java.util.List<Connection<T>.Edge> edges) {
        Connection<T> connection = new Connection<>();
        connection.setPageInfo(sourceConnection.getPageInfo());
        connection.setEdges(edges);
        return connection;
    }
}
