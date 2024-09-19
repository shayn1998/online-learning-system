package com.company_name.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company_name.model.Learner;
import com.company_name.repository.LearnerRepo;

//LearnerService Implementation Class

@Service
public class LearnerServiceImpl implements LearnerService{
	
	@Autowired
	LearnerRepo learnerRepo;

	@Override
	public void save(Learner learner) {
		// TODO Auto-generated method stub
		
		learnerRepo.save(learner);
		
	}

	@Override
	public Optional<Learner> findById(String emailId) {
		// TODO Auto-generated method stub
		return learnerRepo.findById(emailId);
	}

	@Override
	public List<Learner> findAll() {
		// TODO Auto-generated method stub
		return learnerRepo.findAll();
	}


}
