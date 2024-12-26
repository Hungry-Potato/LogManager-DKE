package com.hungry.discordpub;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "spring.kafka")
public class KafkaProperties {
    private String bootstrapServers;
    private final Consumer consumer = new Consumer();

    @Getter
    @Setter
    public static class Consumer {
        private String groupId;
        private String keyDeserializer;
        private String valueDeserializer;
        private String topicname;
    }
}
