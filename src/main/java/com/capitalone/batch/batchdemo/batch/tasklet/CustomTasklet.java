package com.capitalone.batch.batchdemo.batch.tasklet;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.capitalone.batch.batchdemo.repo.GuestRepo;
@Component
public class CustomTasklet implements Tasklet{

	@Autowired
	private GuestRepo guestRepo;
	
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		
		guestRepo.findAll()
		.stream()
		.filter(row -> "Female".equals(row.getGender()))
		.map(row -> row.getId())
		.forEach(id -> guestRepo.deleteById(id));
		
		return RepeatStatus.FINISHED;
	}

}
