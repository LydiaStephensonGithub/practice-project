package com.example.ducks.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.ducks.entity.Duck;
import com.example.ducks.repo.DuckRepo;

@Service
public class DuckService implements ServiceMethods<Duck> {

	//not making a new object, creating a variable of the type repo
	private DuckRepo repo;
	
	//constructor
	public DuckService(DuckRepo repo) {
		this.repo = repo;
	}
	
	@Override
	public Duck create(Duck t) {
		return this.repo.save(t);
	}

	@Override
	public List<Duck> readAll() {
		return this.repo.findAll();
	}

	@Override
	public Duck readById(long id) {
		Optional<Duck> getDuck = this.repo.findById(id);
		if (getDuck.isPresent()) {
			return getDuck.get();
		}
		return null;
	}

	@Override
	public Duck update(long id, Duck t) {
		Optional<Duck> existingDuck = this.repo.findById(id);
		if (existingDuck.isPresent()) {
			Duck exists = existingDuck.get();
			exists.setAge(t.getAge());
			exists.setName(t.getName());
			exists.setHabitat(t.getHabitat());
			exists.setGender(t.getGender());
			
			return this.repo.saveAndFlush(exists);
		}
		return null;
	}

	@Override
	public boolean delete(long id) {
		this.repo.deleteById(id);
		return !this.repo.existsById(id);
	}

}
