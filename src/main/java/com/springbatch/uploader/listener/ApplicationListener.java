package com.springbatch.uploader.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationListener implements JobExecutionListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationListener.class);

    @Override
    public void beforeJob(JobExecution jobExecution) {
        LOGGER.info("ApplicationListener : beforeJob");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        LOGGER.info("ApplicationListener : afterJob .. Job status "+jobExecution.getStatus());
    }
}
