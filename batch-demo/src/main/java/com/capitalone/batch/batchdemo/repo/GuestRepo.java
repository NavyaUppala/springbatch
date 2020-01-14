package com.capitalone.batch.batchdemo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capitalone.batch.batchdemo.model.Guest;

@Repository
public interface GuestRepo extends JpaRepository<Guest, Long> {

}
