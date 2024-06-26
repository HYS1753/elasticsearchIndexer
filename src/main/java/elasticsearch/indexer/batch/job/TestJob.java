package elasticsearch.indexer.batch.job;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Date;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class TestJob {
    @Bean(name = "helloJob")
    public Job helloJob(JobRepository jobRepository, @Qualifier("helloStep1") Step helloStep1, @Qualifier("helloStep2") Step helloStep2) {
        return new JobBuilder("helloJob", jobRepository)
                .start(helloStep1)
                .next(helloStep2)
                .build();
    }

    @Bean(name = "helloStep1")
    public Step helloStep1(JobRepository jobRepository, PlatformTransactionManager tx) {
        return new StepBuilder("helloStep1", jobRepository)
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        Thread.sleep(3000);
                        System.out.println(" ====================== ");
                        System.out.println(" >> Hello Spring Batch !!");
                        System.out.println(" ====================== ");
                        return RepeatStatus.FINISHED;
                    }
                }, tx) // or .chunk(chunkSize, transactionManager)
                .build();
    }

    @Bean(name = "helloStep2")
    public Step helloStep2(JobRepository jobRepository, PlatformTransactionManager tx) {
        return new StepBuilder("helloStep2", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    // 객체로 반환됨.
                    JobParameters contributionJobParams = contribution.getStepExecution().getJobParameters();
                    String con_name = contributionJobParams.getString("name");
                    Long con_seq = contributionJobParams.getLong("seq");
                    Double con_age = contributionJobParams.getDouble("age");
                    Date con_date = contributionJobParams.getDate("date");

                    // Map 으로 반환됨(값만 확인 가능)
                    Map<String, Object> chunkContextJobParams = chunkContext.getStepContext().getJobParameters();

                    System.out.println(" ====================== ");
                    System.out.println(" >> Job parameters");
                    System.out.println(" >> contribution - name: "+con_name+" seq: "+con_seq+" age: "+con_age+" date: "+con_date);
                    System.out.println(" >> chunkContext - Map: "+ chunkContextJobParams.toString());
                    System.out.println(" ====================== ");

                    return RepeatStatus.FINISHED;
                }, tx).build();
    }
}
