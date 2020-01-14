package com.capitalone.batch.batchdemo.batch;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import com.capitalone.batch.batchdemo.batch.processor.CustomeProcessor;
import com.capitalone.batch.batchdemo.batch.writer.CustomWriter;
import com.capitalone.batch.batchdemo.model.Guest;

@Configuration
@EnableBatchProcessing
public class BatchConfig {
	
	@Value("classpath:input/sample.csv")
	public Resource resource;
	
	@Autowired
	public ResourceLoader resourceLoader;
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	private CustomWriter customWriter;
	
	@Autowired
	private CustomeProcessor customeProcessor;
	
	@Bean
	public Job job() {
		return jobBuilderFactory.get("my-job")
				.incrementer(new RunIdIncrementer())
				.start(step1())
				//.next(step2()) //reader -- jdbcpaging reader -- using feign we will make api calls -- added info to the database
				.build();
	}

	@Bean
	public Step step1( ) {
		return stepBuilderFactory.get("step1")
				.<Map<String, String>, Guest>chunk(10)
				.reader(reader())
				.processor(customeProcessor)
				.writer(customWriter)
				.build();
	}

	@Bean
	public FlatFileItemReader<Map<String, String>> reader() {
		FlatFileItemReader<Map<String, String>> reader = new FlatFileItemReader<Map<String,String>>();
		reader.setResource(resource);
		reader.setLinesToSkip(1);
		reader.setStrict(false);
		
		//linemapper
		DefaultLineMapper<Map<String, String>> lineMapper = new DefaultLineMapper();

		lineMapper.setFieldSetMapper(fieldSet -> {
			Map<String, String> map = new HashMap<String, String>();
			
			for (int i = 0; i < fieldSet.getFieldCount(); i++) {
				map.put(fieldSet.getNames()[i], fieldSet.getValues()[i]);
			}
			return map;
		});
		DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer(",");
		tokenizer.setNames("id", "firstName", "lastName", "email", "gender", "ipAddress");
		
		lineMapper.setLineTokenizer(tokenizer);
		reader.setLineMapper(lineMapper);
		
		return reader;
	}
}
