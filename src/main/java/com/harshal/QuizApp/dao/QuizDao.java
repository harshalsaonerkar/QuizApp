package com.harshal.QuizApp.dao;

import com.harshal.QuizApp.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizDao extends JpaRepository<Quiz, Integer>{

}

