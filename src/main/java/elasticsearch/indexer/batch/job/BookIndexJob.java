package elasticsearch.indexer.batch.job;
/****************************************************************************************
 * Copyright(c) 2021-2023 Kyobo Book Centre All right reserved.
 * This software is the proprietary information of Kyobo Book.
 *
 * Revision History
 * Author                         Date          Description
 * --------------------------     ----------    ----------------------------------------
 * hys1753@kyobobook.co.kr        2024-06-11
 *
 ****************************************************************************************/

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

/**
 * @author : hys1753@kyobobook.co.kr
 * @Project : elasticsearchIndexer
 * @FileName : BookIndexJob
 * @Date : 2024-06-11
 * @description :
 */
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
