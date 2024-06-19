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

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : hys1753@kyobobook.co.kr
 * @Project : elasticsearchIndexer
 * @FileName : CustomKafkaItemReaderConfig
 * @Date : 2024-06-17
 * @description :
 */
@Configuration
public class CustomKafkaItemReaderConfig {
    @Value("${kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${kafka.consumer.auto-offset-reset}")
    private String autoOffsetReset;

    @Value("${kafka.consumer.enable-auto-commit}")
    private String enableAutoCommit;

    @Value("${kafka.consumer.auto-commit-interval}")
    private String autoCommitInterval;

    /* Kafka ItemReader Setting - 해당 Bean을 주입 시켜 사용 */
    @Bean
    public CustomKafkaItemReader bookIndexItemReader() {
        return new CustomKafkaItemReader("bookIndexItemReader", "test", "test_group", bootstrapServers, autoOffsetReset, enableAutoCommit, autoCommitInterval);
    }
}
