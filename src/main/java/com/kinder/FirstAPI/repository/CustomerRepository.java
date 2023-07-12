package com.kinder.FirstAPI.repository;

import com.kinder.FirstAPI.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {}
