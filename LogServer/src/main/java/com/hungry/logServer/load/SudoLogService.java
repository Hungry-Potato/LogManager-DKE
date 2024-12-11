package com.hungry.logServer.load;

import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import static org.elasticsearch.index.query.QueryBuilders.rangeQuery;

@Service
public class SudoLogService {
    private final ElasticsearchRestTemplate elasticsearchRestTemplate;

    public SudoLogService(ElasticsearchRestTemplate elasticsearchRestTemplate) {
        this.elasticsearchRestTemplate = elasticsearchRestTemplate;
    }

    public List<SudoLog> fetchNewLogs(String lastTimestamp) {
        Query query = new NativeSearchQueryBuilder()
                .withQuery(rangeQuery("@timestamp").gt(lastTimestamp))
                .build();

        return elasticsearchRestTemplate.search(query, SudoLog.class)
                .stream()
                .map(hit -> hit.getContent())
                .toList();
    }
}

