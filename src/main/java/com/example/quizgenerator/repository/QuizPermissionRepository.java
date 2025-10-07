package com.example.quizgenerator.repository;

import com.example.quizgenerator.model.Quiz;
import com.example.quizgenerator.model.QuizPermission;
import com.example.quizgenerator.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizPermissionRepository extends JpaRepository<QuizPermission, Long> {
    List<QuizPermission> findByUser(User user);
    List<QuizPermission> findByQuiz(Quiz quiz);
}
