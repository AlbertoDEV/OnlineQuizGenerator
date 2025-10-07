package com.example.quizgenerator.service;

import com.example.quizgenerator.model.Quiz;
import com.example.quizgenerator.model.QuizPermission;
import com.example.quizgenerator.model.User;
import com.example.quizgenerator.repository.QuizPermissionRepository;
import com.example.quizgenerator.repository.QuizRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuizServiceImpl implements QuizService {

    private final QuizRepository quizRepository;
    private final QuizPermissionRepository quizPermissionRepository;

    public QuizServiceImpl(QuizRepository quizRepository, QuizPermissionRepository quizPermissionRepository) {
        this.quizRepository = quizRepository;
        this.quizPermissionRepository = quizPermissionRepository;
    }

    @Override
    public List<Quiz> getQuizzesForUser(User user) {
        return quizRepository.findByUser(user);
    }

    @Override
    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll();
    }

    @Override
    public Quiz getQuizById(Long id) {
        return quizRepository.findById(id).orElse(null);
    }

    @Override
    public Quiz saveQuiz(Quiz quiz) {
        return quizRepository.save(quiz);
    }

    @Override
    public void deleteQuiz(Long id) {
        quizRepository.deleteById(id);
    }

    @Override
    public List<Quiz> getSharedQuizzesForUser(User user) {
        return quizPermissionRepository.findByUser(user).stream()
                .map(QuizPermission::getQuiz)
                .collect(Collectors.toList());
    }

    @Override
    public void shareQuiz(Quiz quiz, User user) {
        QuizPermission permission = new QuizPermission(quiz, user);
        quizPermissionRepository.save(permission);
    }
}
