package com.capitalone.batch.batchdemo.scheduler;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.capitalone.batch.batchdemo.service.BatchService;

@Configuration
@EnableScheduling
public class BatchLauncher {

	Logger logger = LogManager.getLogger(BatchLauncher.class);
	
	@Autowired
	private BatchService batchService;
	
	@Scheduled(cron = "0 0 3 * * *")
	public void runJob() throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		logger.info("Schedule is triggered");
		//batchService.runJob();	
	}
}
