package com.example.demo.repository;

import com.example.demo.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Question repository, uses Spring Data JPA repository infrastructure with generic CRUD methods.
 */
@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

}
