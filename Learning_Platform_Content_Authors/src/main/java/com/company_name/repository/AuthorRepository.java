package com.company_name.repository;

//Repository

import org.springframework.data.jpa.repository.JpaRepository;

import com.company_name.model.Author;

public interface AuthorRepository extends JpaRepository<Author, String>{

}

