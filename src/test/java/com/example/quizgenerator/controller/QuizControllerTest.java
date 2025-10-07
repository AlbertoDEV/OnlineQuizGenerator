package com.example.quizgenerator.controller;

import com.example.quizgenerator.model.Quiz;
import com.example.quizgenerator.model.User;
import com.example.quizgenerator.service.QuizService;
import com.example.quizgenerator.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(QuizController.class)
public class QuizControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QuizService quizService;

    @MockBean
    private UserService userService;

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testGetQuizzes() throws Exception {
        User user = new User();
        user.setUsername("admin");

        Quiz quiz = new Quiz();
        quiz.setTitle("Test Quiz");
        quiz.setUser(user);

        when(userService.findByUsername("admin")).thenReturn(user);
        when(quizService.getQuizzesForUser(user)).thenReturn(Collections.singletonList(quiz));

        mockMvc.perform(get("/quizzes"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin"))
                .andExpect(model().attributeExists("quizzes"))
                .andExpect(model().attribute("quizzes", Collections.singletonList(quiz)));
    }
}