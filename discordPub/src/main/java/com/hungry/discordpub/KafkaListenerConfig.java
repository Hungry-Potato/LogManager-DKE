package com.hungry.discordpub;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaListenerConfig {
    private final KafkaProperties kafkaProperties;
    private final DiscordMsgPub discordMsgPub;

    public KafkaListenerConfig(KafkaProperties kafkaProperties, DiscordMsgPub discordMsgPub) {
        this.kafkaProperties = kafkaProperties;
        this.discordMsgPub = discordMsgPub;
    }

    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        config.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaProperties.getConsumer().getGroupId());
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(config);
    }

    @Bean
    public KafkaMessageListenerContainer<String, String> messageListenerContainer(ConsumerFactory<String, String> consumerFactory){
        final String topicName = kafkaProperties.getConsumer().getTopicname();
        ContainerProperties containerProperties = new ContainerProperties(topicName);
        containerProperties.setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        containerProperties.setMessageListener(discordMsgPub);

        return new KafkaMessageListenerContainer<>(consumerFactory, containerProperties);
    }
}
