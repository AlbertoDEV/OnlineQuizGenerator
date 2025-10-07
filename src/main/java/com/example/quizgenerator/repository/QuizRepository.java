package com.example.quizgenerator.repository;

import com.example.quizgenerator.model.Quiz;
import com.example.quizgenerator.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {
    List<Quiz> findByUser(User user);
}
