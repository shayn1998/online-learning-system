package com.company_name.service;

import java.util.List;
import java.util.Optional;

import com.company_name.model.Learner;

//Service Layer Interface

public interface LearnerService {

	void save(Learner learner);

	Optional<Learner> findById(String emailId);

	List<Learner> findAll();

}
