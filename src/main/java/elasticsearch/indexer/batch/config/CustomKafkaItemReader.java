package elasticsearch.indexer.batch.config;
/****************************************************************************************
 * Copyright(c) 2021-2023 Kyobo Book Centre All right reserved.
 * This software is the proprietary information of Kyobo Book.
 *
 * Revision History
 * Author                         Date          Description
 * --------------------------     ----------    ----------------------------------------
 * hys1753@kyobobook.co.kr        2024-06-17
 *
 ****************************************************************************************/

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.batch.item.kafka.KafkaItemReader;
import org.springframework.batch.item.kafka.builder.KafkaItemReaderBuilder;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.HashMap;
import java.util.Properties;

/**
 * @author : hys1753@kyobobook.co.kr
 * @Project : elasticsearchIndexer
 * @FileName : CustomKafkaItemReader
 * @Date : 2024-06-17
 * @description :
 */
public class CustomKafkaItemReader {
    private String bootstrapServers;
    private String autoOffsetReset;
    private String enableAutoCommit;
    private String autoCommitInterval;
    private String itemReaderName;
    private String topic;
    private String groupId;

    public CustomKafkaItemReader(String itemReaderName, String topic, String groupId,
                                 String bootstrapServers, String autoOffsetReset,
                                 String enableAutoCommit, String autoCommitInterval) {
        this.itemReaderName = itemReaderName;
        this.topic = topic;
        this.groupId = groupId;
        this.bootstrapServers = bootstrapServers;
        this.autoOffsetReset = autoOffsetReset;
        this.enableAutoCommit = enableAutoCommit;
        this.autoCommitInterval = autoCommitInterval;
    }

    public KafkaItemReader<String, String> ItemReader() {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        // Group ID 설정
        props.put(ConsumerConfig.GROUP_ID_CONFIG, this.groupId);
        // Auto Offset Reset 설정
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, this.autoOffsetReset);
        // Auto Commit 설정
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, this.enableAutoCommit);
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, this.autoCommitInterval);
        // poll 요청을 보내고, 다음 poll 요청을 보내는데 까지의 최대 시간 설정
        props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, 5000); // poll 호출 사이의 최대 간격을 설정하여 소비자가 너무 오래 응답하지 않는 것을 방지 ( default 300000ms)
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, ConsumerConfig.DEFAULT_MAX_POLL_RECORDS);
        // Deserializer
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        return new KafkaItemReaderBuilder<String, String>()
                .name(this.itemReaderName)
                .topic(this.topic)
                .partitions(0) // 여러인자 한번에 전송 가능 ex. partitions(0, 1, 2)
                .consumerProperties(props)
                .pollTimeout(Duration.ofSeconds(30L)) // poll 메서드가 데이터를 가져오기 위해 대기할 시간을 설정 (해당 시간 동안 데이터가 들어오지 않으면 Job 종료)
                .partitionOffsets(new HashMap<>()) // open() 에서 해당 설정이 없으면 if (this.partitionOffsets == null) 해당 조건에 의해 무조건 offset 0 부터 시작. dummy 로 hashMap 전달 하면 groupId 에 적용된 offset 기반으로 poll 가능
                .saveState(true)
                .build();
    }
}
