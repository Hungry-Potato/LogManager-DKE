package com.hungry.logServer.Elasticsearch;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import com.hungry.logServer.Model.LoginLog;
import java.util.Optional;

public interface LoginLogRepository extends ElasticsearchRepository<LoginLog, String> {
    @Query("""
    {
      "bool": {
        "must": [
          { "match": { "tty": "?0" } },
          { "match": { "login_id": "?1" } },
          { "match": { "hostname": "?2" } }
        ]
      }
    }
    """)
    Optional<LoginLog> findFirstByQuery(String tty, String loginId, String hostname);
}