package com.hungry.logServer.load;
import com.hungry.logServer.data.SudoLog;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface SudoLogRepository extends ElasticsearchRepository<SudoLog, String> {
    // 사용자 정의 쿼리 추가 가능
    List<SudoLog> findByUser(String user);
}
