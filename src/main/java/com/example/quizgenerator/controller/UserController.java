package com.example.quizgenerator.controller;

import com.example.quizgenerator.model.Quiz;
import com.example.quizgenerator.model.User;
import com.example.quizgenerator.service.QuizService;
import com.example.quizgenerator.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class UserController {

    private final QuizService quizService;
    private final UserService userService;

    public UserController(QuizService quizService, UserService userService) {
        this.quizService = quizService;
        this.userService = userService;
    }

    @GetMapping("/user")
    public String userDashboard(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByUsername(userDetails.getUsername());
        List<Quiz> myQuizzes = quizService.getQuizzesForUser(user);
        List<Quiz> sharedQuizzes = quizService.getSharedQuizzesForUser(user);

        model.addAttribute("myQuizzes", myQuizzes);
        model.addAttribute("sharedQuizzes", sharedQuizzes);
        return "user";
    }

    @GetMapping("/quiz/{id}")
    public String takeQuiz(@PathVariable Long id, Model model) {
        Quiz quiz = quizService.getQuizById(id);
        model.addAttribute("quiz", quiz);
        return "quiz";
    }

    @PostMapping("/quiz/submit/{id}")
    public String submitQuiz(@PathVariable Long id, @RequestParam Map<String, String> answers, Model model) {
        Quiz quiz = quizService.getQuizById(id);
        int score = 0;
        for (int i = 0; i < quiz.getQuestions().size(); i++) {
            String questionId = String.valueOf(quiz.getQuestions().get(i).getId());
            String selectedOption = answers.get("question_" + questionId);
            if (selectedOption != null && Integer.parseInt(selectedOption) == quiz.getQuestions().get(i).getCorrectAnswer()) {
                score++;
            }
        }
        model.addAttribute("quiz", quiz);
        model.addAttribute("score", score);
        return "result";
    }
}
