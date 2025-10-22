package com.example.quizgenerator.config;

import com.example.quizgenerator.model.Question;
import com.example.quizgenerator.model.Quiz;
import com.example.quizgenerator.model.User;
import com.example.quizgenerator.service.QuizService;
import com.example.quizgenerator.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserService userService;
    private final QuizService quizService;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DataInitializer(UserService userService, QuizService quizService, JdbcTemplate jdbcTemplate) {
        this.userService = userService;
        this.quizService = quizService;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        jdbcTemplate.execute("CREATE SCHEMA IF NOT EXISTS \"quiz-generator\"");
        // Create Admin User
        if (userService.findByUsername("admin") == null) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword("adminpass");
            userService.saveUserWithRoles(admin, Set.of("ROLE_ADMIN", "ROLE_USER"));
        }

        // Create User 1
        User user1;
        if (userService.findByUsername("user1") == null) {
            user1 = new User();
            user1.setUsername("user1");
            user1.setPassword("user1pass");
            userService.saveUser(user1);
        } else {
            user1 = userService.findByUsername("user1");
        }


        // Create User 2
        User user2;
        if (userService.findByUsername("user2") == null) {
            user2 = new User();
            user2.setUsername("user2");
            user2.setPassword("user2pass");
            userService.saveUser(user2);
        } else {
            user2 = userService.findByUsername("user2");
        }


        // Create a quiz for User 1 if they don't have one
        if (quizService.getQuizzesForUser(user1).isEmpty()) {
            Quiz quiz = new Quiz();
            quiz.setTitle("Java Basics Quiz");
            quiz.setUser(user1);

            Question q1 = new Question();
            q1.setText("What is the main purpose of the 'static' keyword in Java?");
            q1.setOptions(List.of("To make a variable constant.", "To allow a method/variable to be accessed without creating an instance of the class.", "To define a method that can be overridden.", "To indicate that a method is final."));
            q1.setCorrectAnswer(1);

            Question q2 = new Question();
            q2.setText("Which collection class does not allow duplicate elements?");
            q2.setOptions(List.of("ArrayList", "HashMap", "HashSet", "LinkedList"));
            q2.setCorrectAnswer(2);

            quiz.setQuestions(List.of(q1, q2));
            quizService.saveQuiz(quiz);

            // Share the quiz with User 2
            quizService.shareQuiz(quiz, user2);
        }
    }
}
