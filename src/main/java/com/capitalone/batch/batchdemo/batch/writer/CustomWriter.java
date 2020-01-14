package com.capitalone.batch.batchdemo.batch.writer;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.capitalone.batch.batchdemo.model.Guest;
import com.capitalone.batch.batchdemo.repo.GuestRepo;

@Component
public class CustomWriter implements ItemWriter<Guest> {

	@Autowired
	private GuestRepo guestRepo;

	@Override
	public void write(List<? extends Guest> guests) throws Exception {
		guestRepo.saveAll(guests);

	}

}
