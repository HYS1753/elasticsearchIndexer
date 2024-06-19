package elasticsearch.indexer.batch.config;
/****************************************************************************************
 * Copyright(c) 2021-2023 Kyobo Book Centre All right reserved.
 * This software is the proprietary information of Kyobo Book.
 *
 * Revision History
 * Author                         Date          Description
 * --------------------------     ----------    ----------------------------------------
 * hys1753@kyobobook.co.kr        2024-06-18
 *
 ****************************************************************************************/

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.kafka.KafkaItemReader;
import org.springframework.kafka.core.ConsumerFactory;

/**
 * @author : hys1753@kyobobook.co.kr
 * @Project : elasticsearchIndexer
 * @FileName : CustomKafkaChunkListener
 * @Date : 2024-06-18
 * @description :
 */
@Slf4j
public class CustomKafkaChunkListener implements ChunkListener {
    private final KafkaItemReader<String, String> itemReader;

    public CustomKafkaChunkListener(KafkaItemReader<String, String> itemReader) {
        this.itemReader = itemReader;
    }

    @Override
    public void beforeChunk(ChunkContext context) {}

    @Override
    public void afterChunk(ChunkContext context) {
        // 사실 상 chunk writer 까지 정상적으로 완료 되면 자동으로 update 함.
        if (itemReader != null) {
            ExecutionContext executionContext = context.getStepContext().getStepExecution().getExecutionContext();
            itemReader.update(executionContext);
        }
    }

    @Override
    public void afterChunkError(ChunkContext context) {
        log.error("오류다오류다오류다");
    }
}

