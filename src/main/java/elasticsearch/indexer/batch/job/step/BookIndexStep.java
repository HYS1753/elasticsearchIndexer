package elasticsearch.indexer.batch.job.step;

import elasticsearch.indexer.batch.config.CustomKafkaChunkListener;
import elasticsearch.indexer.batch.config.CustomKafkaItemReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.kafka.KafkaItemReader;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@Slf4j
public class BookIndexStep {

    private CustomKafkaItemReader kafkaItemReader;

    public BookIndexStep(CustomKafkaItemReader bookIndexItemReader) {
        this.kafkaItemReader = bookIndexItemReader;
    }

    @Bean(name = "bookIndexChunkStep")
    public Step bookIndexChunkStep(JobRepository jobRepository, PlatformTransactionManager tx) {
        KafkaItemReader<String, String> itemReader = kafkaItemReader.ItemReader();
        return new StepBuilder("bookIndexChunkStep", jobRepository)
                .<String, String>chunk(2, tx)  // <> 안에 Input Output Type 을 적어줌. 위 청크 사이즈 만큼 트랜젝션을 묶어 처리
//                .listener(new CustomKafkaChunkListener(itemReader))
                .reader(itemReader)
                .processor(new ItemProcessor<String, String>() {
                    @Override
                    public String process(String item) throws Exception {
                        log.info("current processor data : " + item);
                        return item;
                    }
                })
                .writer(new ItemWriter<String>() {
                    @Override
                    public void write(Chunk<? extends String> chunk) throws Exception {
                        chunk.forEach(item -> log.info("current item writer chunk item : " + chunk.toString()));
                    }
                })
                .build();
    }

    @Bean(name = "bookIndexResultStep")
    public Step chunkStep2(JobRepository jobRepository, PlatformTransactionManager tx) {
        return new StepBuilder("chunkStep2", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    System.out.println(" ====================== ");
                    System.out.println(" >> bookIndexResultStep has executed");
                    System.out.println(" ====================== ");

                    return RepeatStatus.FINISHED;
                }, tx).build();
    }
}
