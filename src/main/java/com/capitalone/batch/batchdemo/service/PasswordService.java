package com.capitalone.batch.batchdemo.service;

import org.jasypt.util.text.BasicTextEncryptor;

public class PasswordService {

	public static void main(String[] args) {
		BasicTextEncryptor encryptor = new BasicTextEncryptor();
		encryptor.setPassword("abc123");
		
		String encrytedPassword = encryptor.encrypt("myPassword");
		System.out.println(encrytedPassword);
		System.out.println("ENC(" + encrytedPassword + ")");
		
		String decrytedPassword =	encryptor.decrypt("7OYk8i2eshS7TatMDEpnZhzGfv/a8tO8");
		System.out.println(decrytedPassword);
	}

}
