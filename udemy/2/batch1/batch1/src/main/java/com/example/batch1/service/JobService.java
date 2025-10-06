package com.example.batch1.service;

import com.example.batch1.request.JobParamRequest;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JobService {

    @Autowired
    JobLauncher jobLauncher;

    @Qualifier("firstJob")
    @Autowired
    Job firstJob;

    @Qualifier("secondJob")
    @Autowired
    Job secondJob;

    @Async
    public void startJob(String jobName, List<JobParamRequest> jobParamRequests){
        Map<String, JobParameter> params = new HashMap<>();
        params.put("currentTime", new JobParameter(System.currentTimeMillis()));

        jobParamRequests.forEach(j -> {
            params.put(j.getParamKey(),
                    new JobParameter(j.getParamValue()));
        });

        JobParameters jobParameters = new JobParameters(params);

        try {
            JobExecution jobExecution = null;
            if (jobName.equals("first job")) {
                jobExecution = jobLauncher.run(firstJob, jobParameters);
            } else if (jobName.equals("second job")) {
                jobExecution = jobLauncher.run(secondJob, jobParameters);
            }

            System.out.println("Job execution ID = " + jobExecution.getId());
        } catch (Exception e) {
            System.out.println("Exception in job");
        }
    }


}
