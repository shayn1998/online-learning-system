package com.company_name.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company_name.model.Learner;

// Repository

public interface LearnerRepo extends JpaRepository<Learner, String>{


}
