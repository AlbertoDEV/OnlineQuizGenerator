package com.example.quizgenerator.service;

import com.example.quizgenerator.model.Quiz;
import com.example.quizgenerator.model.User;

import java.util.List;

public interface QuizService {
    List<Quiz> getQuizzesForUser(User user);
    List<Quiz> getAllQuizzes();
    Quiz getQuizById(Long id);
    Quiz saveQuiz(Quiz quiz);
    void deleteQuiz(Long id);
    List<Quiz> getSharedQuizzesForUser(User user);
    void shareQuiz(Quiz quiz, User user);
}
