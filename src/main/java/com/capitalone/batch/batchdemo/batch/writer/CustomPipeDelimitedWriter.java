package com.capitalone.batch.batchdemo.batch.writer;

import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import com.capitalone.batch.batchdemo.model.Guest;

@Configuration
public class CustomPipeDelimitedWriter {

	@Value("${output.location}")
	public Resource resource;

	@Bean
	public FlatFileItemWriter<Guest> pipeDelimitedWriter() {
		FlatFileItemWriter<Guest> writer = new FlatFileItemWriter<Guest>();

		writer.setResource(resource);
		writer.setAppendAllowed(true);
		writer.setLineAggregator(new DelimitedLineAggregator<Guest>() {
			{
				setDelimiter("|");
				setFieldExtractor(new BeanWrapperFieldExtractor<Guest>() {
					{
						setNames(new String[] { "id", "firstName" });
					}
				});
			}
		});
		writer.setShouldDeleteIfExists(true);

		return writer;
	}
}
