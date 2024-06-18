package elasticsearch.indexer.batch.config;
/****************************************************************************************
 * Copyright(c) 2021-2023 Kyobo Book Centre All right reserved.
 * This software is the proprietary information of Kyobo Book.
 *
 * Revision History
 * Author                         Date          Description
 * --------------------------     ----------    ----------------------------------------
 * hys1753@kyobobook.co.kr        2024-06-13
 *
 ****************************************************************************************/

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.TaskExecutorJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.SyncTaskExecutor;
import org.springframework.stereotype.Component;

/**
 * @author : hys1753@kyobobook.co.kr
 * @Project : elasticsearchIndexer
 * @FileName : CustomJobLauncher
 * @Date : 2024-06-13
 * @description :
 */
@Component
@RequiredArgsConstructor
public class CustomJobLauncher {
    private final JobRepository jobRepository;

    /**
     * 동기 형태로 Job 실행
     * ex. step 안에 delay 가 3초 있으면 응답도 3초 있다가 옴
     * @return
     */
    public JobLauncher defaultJobLauncher() {
        TaskExecutorJobLauncher jobLauncher = new TaskExecutorJobLauncher();
        jobLauncher.setJobRepository(jobRepository);
        jobLauncher.setTaskExecutor(new SyncTaskExecutor());
        return jobLauncher;
    }

    /**
     * 비동기 형태로 Job 실행
     * ex. Step 안에 delay가 3초 있더라도 응답은 즉각적으로, 내부적으로 job 이 실행됨.
     * @return
     */
    public JobLauncher asyncJobLauncher() {
        TaskExecutorJobLauncher jobLauncher = new TaskExecutorJobLauncher();
        jobLauncher.setJobRepository(jobRepository);
        jobLauncher.setTaskExecutor(new SimpleAsyncTaskExecutor());
        return jobLauncher;
    }

}