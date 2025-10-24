package com.cheong.ecommerce_ai_driven.common.paging.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Connection<T> {

    private List<Edge> edges;
    private PageInfo pageInfo;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class Edge {
        private T node;
        private String cursor;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PageInfo {
        private String startCursor;
        private String endCursor;
        private boolean hasPreviousPage;
        private boolean hasNextPage;
    }

    public static <T> Connection<T> createConnection(List<T> items, String before, int limit, Function<T, String> idExtractor) {

        Connection<T> connection = new Connection<>();
        Connection.PageInfo pageInfo = new Connection.PageInfo();
        List<Connection<T>.Edge> edges = new ArrayList<>();

        boolean hasExtraItems = items.size() > limit;
        List<T> nodes = hasExtraItems ? items.subList(0, limit) : items;

        nodes.forEach(item -> {
            Connection<T>.Edge edge = connection.new Edge();
            edge.setNode(item);
            edge.setCursor(idExtractor.apply(item));
            edges.add(edge);
        });

        if(!edges.isEmpty()) {
            pageInfo.setStartCursor(edges.getFirst().getCursor());
            pageInfo.setEndCursor(edges.getLast().getCursor());
        }

        pageInfo.setHasPreviousPage(hasExtraItems && !StringUtils.hasText(before));
        pageInfo.setHasNextPage(hasExtraItems && StringUtils.hasText(before));

        connection.setEdges(edges);
        connection.setPageInfo(pageInfo);

        return connection;
    }

}