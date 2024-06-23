package elasticsearch.indexer.batch.controller;

import elasticsearch.indexer.batch.config.CustomJobLauncher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping(value = "/jobs")
public class IndexerLaunchController {
    private final CustomJobLauncher jobLauncher;
    private final JobRegistry jobRegistry;

    public IndexerLaunchController(CustomJobLauncher jobLauncher, JobRegistry jobRegistry) {
        this.jobLauncher = jobLauncher;
        this.jobRegistry = jobRegistry;
    }

    @GetMapping(value = "/{jobName}")
    public ResponseEntity<String> jobLaunch(@PathVariable(name = "jobName") String jobName) throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("name", jobName)
                .addLong("timestamp", System.currentTimeMillis())
                .toJobParameters();

        Job job = jobRegistry.getJob(jobName);
        jobLauncher.defaultJobLauncher().run(job, jobParameters);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }
}
