package elasticsearch.indexer.batch.job;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
public class BookIndexJob {

    @Bean(name = "bookIndexChunkJob")
    public Job bookIndexJob(JobRepository jobRepository,
                        @Qualifier("bookIndexChunkStep") Step bookIndexChunkStep,
                        @Qualifier("bookIndexResultStep") Step bookIndexResultStep) {
        return new JobBuilder("bookIndexChunkJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(bookIndexChunkStep)
                .next(bookIndexResultStep)
                .build();
    }
}
