package elasticsearch.indexer.batch.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
