package com.company_name.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company_name.model.*;
import com.company_name.repository.AppUsersRepo;

//Service Layer Implementation Class

@Service
public class AppUsersServiceImpl implements AppUsersService{
	
	@Autowired
	private AppUsersRepo userRepo;

	@Override
	public boolean existsById(String emailId) {
		// TODO Auto-generated method stub
		return userRepo.existsById(emailId);
	}

	@Override
	public void save(AppUsers appusers) {
		// TODO Auto-generated method stub	
		userRepo.save(appusers);		
	}

	@Override
	public List<AppUsers> findAll() {
		// TODO Auto-generated method stub
		return userRepo.findAll();
	}

	@Override
	public Optional<AppUsers> findById(String emailId) {
		// TODO Auto-generated method stub
		return userRepo.findById(emailId);
	}

}
