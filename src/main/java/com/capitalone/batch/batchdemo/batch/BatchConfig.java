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
import com.capitalone.batch.batchdemo.batch.reader.CustomJdbcPagingReader;
import com.capitalone.batch.batchdemo.batch.tasklet.CustomTasklet;
import com.capitalone.batch.batchdemo.batch.writer.CustomPipeDelimitedWriter;
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
	private CustomPipeDelimitedWriter customPipeDelimitedWriter;
	
	@Autowired
	private CustomeProcessor customeProcessor;
	
	@Autowired
	private CustomJdbcPagingReader customJdbcPagingReader;
	
	@Autowired
	private CustomTasklet customTasklet;
	
	@Bean
	public Job job1() {
		return jobBuilderFactory.get("my-job")
				.incrementer(new RunIdIncrementer())
				.start(step1())
				.next(step2()) 
				.next(stet3())
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
	public Step step2( ) {
		return stepBuilderFactory.get("step2")
				.<Guest, Guest>chunk(10)
				.reader(customJdbcPagingReader.dbpagingReader())
				.writer(customPipeDelimitedWriter.pipeDelimitedWriter())
				.build();
	}
	
	@Bean
	public Step stet3( ) {
		return stepBuilderFactory.get("clean-db")
				.tasklet(customTasklet)
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
