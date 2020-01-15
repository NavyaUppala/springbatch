package com.capitalone.batch.batchdemo.batch.reader;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.support.PostgresPagingQueryProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import com.capitalone.batch.batchdemo.model.Guest;

@Configuration
public class CustomJdbcPagingReader {

	@Autowired
	private DataSource dataSource;
	
	@Bean
	public ItemReader<Guest> dbpagingReader() {
		JdbcPagingItemReader<Guest> databaseReader = new JdbcPagingItemReader<>();

		databaseReader.setDataSource(dataSource);
		databaseReader.setPageSize(1);

		PagingQueryProvider queryProvider = createQueryProvider();
		databaseReader.setQueryProvider(queryProvider);

		databaseReader.setRowMapper(new BeanPropertyRowMapper<>(Guest.class));

		return databaseReader;
	}

	private PagingQueryProvider createQueryProvider() {
		PostgresPagingQueryProvider queryProvider = new PostgresPagingQueryProvider();

		queryProvider.setSelectClause("SELECT *");
		queryProvider.setFromClause("FROM guest");
		queryProvider.setWhereClause("WHERE GENDER = 'Female'");
		queryProvider.setSortKeys(sortByFirstName());

		return queryProvider;
	}

	private Map<String, Order> sortByFirstName() {
		Map<String, Order> sortConfiguration = new HashMap<>();
		sortConfiguration.put("first_name", Order.ASCENDING);
		return sortConfiguration;
	}

}
