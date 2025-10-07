package com.example.quizgenerator.service;

import com.example.quizgenerator.model.Quiz;
import com.example.quizgenerator.model.User;
import com.example.quizgenerator.repository.QuizRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class QuizServiceImplTest {

    @Mock
    private QuizRepository quizRepository;

    @InjectMocks
    private QuizServiceImpl quizService;

    @Test
    public void testGetQuizzesForUser() {
        User user = new User();
        user.setUsername("testuser");

        Quiz quiz = new Quiz();
        quiz.setTitle("Test Quiz");
        quiz.setUser(user);

        when(quizRepository.findByUser(user)).thenReturn(Collections.singletonList(quiz));

        List<Quiz> quizzes = quizService.getQuizzesForUser(user);

        assertEquals(1, quizzes.size());
        assertEquals("Test Quiz", quizzes.get(0).getTitle());
    }
}
