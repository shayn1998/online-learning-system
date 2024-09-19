package com.company_name.service;

import java.util.List;
import java.util.Optional;

import com.company_name.model.Author;

//Service Layer Interface

public interface AuthorService {

	void save(Author author1);

	boolean existsById(String authorEmailId);

	Optional<Author> findById(String authorEmailId);

	List<Author> findAll();

	void deleteById(String authorEmailId);


}