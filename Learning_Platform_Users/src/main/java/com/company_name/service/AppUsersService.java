package com.company_name.service;

import java.util.List;
import java.util.Optional;

import com.company_name.model.*;

//Service Layer Interface

public interface AppUsersService {

	boolean existsById(String emailId);

	void save(AppUsers appusers);

	List<AppUsers> findAll();

	Optional<AppUsers> findById(String emailId);

}
