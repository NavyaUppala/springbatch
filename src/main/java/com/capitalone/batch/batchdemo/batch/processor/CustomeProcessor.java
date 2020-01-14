package com.capitalone.batch.batchdemo.batch.processor;

import java.util.Map;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.capitalone.batch.batchdemo.model.Guest;

@Component
public class CustomeProcessor implements ItemProcessor<Map<String, String>, Guest> {

	@Override
	public Guest process(Map<String, String> item) throws Exception {
		Guest guest = new Guest();
		guest.setFirstName(item.get("firstName"));
		guest.setLastName(item.get("lastName"));
		guest.setEmail(item.get("email"));
		guest.setId(Long.valueOf(item.get("id")));
		guest.setIpAddress(item.get("ipAddress"));
		guest.setGender(item.get("gender"));
		return guest;
	}

}
