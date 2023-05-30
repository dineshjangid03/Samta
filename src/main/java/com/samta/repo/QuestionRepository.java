package com.samta.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.samta.model.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
	
}
