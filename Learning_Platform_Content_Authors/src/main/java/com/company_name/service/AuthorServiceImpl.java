package com.company_name.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.company_name.model.Author;
import com.company_name.repository.AuthorRepository;

//Service Layer Implementation Class

@Service
public class AuthorServiceImpl implements AuthorService{
	
	@Autowired
	private AuthorRepository authorRepo;

	@Override
	public void save(Author author1) {
		// TODO Auto-generated method stub
		authorRepo.save(author1);
	}

	@Override
	public boolean existsById(String authorEmailId) {
		// TODO Auto-generated method stub
		return authorRepo.existsById(authorEmailId);
	}

	@Override
	public Optional<Author> findById(String authorEmailId) {
		// TODO Auto-generated method stub
		return authorRepo.findById(authorEmailId);
	}

	@Override
	public List<Author> findAll() {
		// TODO Auto-generated method stub
		return authorRepo.findAll();
	}

	@Override
	public void deleteById(String authorEmailId) {
		// TODO Auto-generated method stub
		authorRepo.deleteById(authorEmailId);
	}
	

}