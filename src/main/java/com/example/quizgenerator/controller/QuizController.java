package com.example.quizgenerator.controller;

import com.example.quizgenerator.model.Quiz;
import com.example.quizgenerator.model.User;
import com.example.quizgenerator.service.QuizService;
import com.example.quizgenerator.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Controller
@RequestMapping("/quizzes")
public class QuizController {

    private final QuizService quizService;
    private final UserService userService;

    public QuizController(QuizService quizService, UserService userService) {
        this.quizService = quizService;
        this.userService = userService;
    }

    @GetMapping
    public String getQuizzes(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByUsername(userDetails.getUsername());
        List<Quiz> quizzes;
        if (user.getRoles().contains("ROLE_ADMIN")) {
            quizzes = quizService.getAllQuizzes();
        } else {
            quizzes = quizService.getQuizzesForUser(user);
        }
        model.addAttribute("quizzes", quizzes);
        model.addAttribute("isAdmin", user.getRoles().contains("ROLE_ADMIN"));
        return "admin";
    }

    @GetMapping("/new")
    public String showQuizForm(Model model) {
        model.addAttribute("quiz", new Quiz());
        return "quiz-form";
    }

    @PostMapping
    public String saveQuiz(@ModelAttribute("quiz") Quiz quiz, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByUsername(userDetails.getUsername());
        // If it's a new quiz, set the owner
        if (quiz.getId() == null) {
            quiz.setUser(user);
        } else {
            // For existing quizzes, ensure the user is authorized before saving
            Quiz existingQuiz = quizService.getQuizById(quiz.getId());
            if (!isOwnerOrAdmin(existingQuiz, userDetails)) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN);
            }
            quiz.setUser(existingQuiz.getUser()); // Keep the original owner
        }
        quizService.saveQuiz(quiz);
        return "redirect:/quizzes";
    }

    @GetMapping("/edit/{id}")
    public String showEditQuizForm(@PathVariable Long id, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        Quiz quiz = quizService.getQuizById(id);
        if (!isOwnerOrAdmin(quiz, userDetails)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        model.addAttribute("quiz", quiz);
        return "quiz-form";
    }

    @PostMapping("/delete/{id}")
    public String deleteQuiz(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        Quiz quiz = quizService.getQuizById(id);
        if (!isOwnerOrAdmin(quiz, userDetails)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        quizService.deleteQuiz(id);
        return "redirect:/quizzes";
    }

    @GetMapping("/share/{id}")
    public String showShareForm(@PathVariable Long id, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        Quiz quiz = quizService.getQuizById(id);
        if (!isOwnerOrAdmin(quiz, userDetails)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        model.addAttribute("quiz", quiz);
        return "share-quiz";
    }

    @PostMapping("/share/{id}")
    public String shareQuiz(@PathVariable Long id, @RequestParam String username, @AuthenticationPrincipal UserDetails userDetails) {
        Quiz quiz = quizService.getQuizById(id);
        if (!isOwnerOrAdmin(quiz, userDetails)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        User userToShareWith = userService.findByUsername(username);
        if (userToShareWith != null) {
            quizService.shareQuiz(quiz, userToShareWith);
        }
        return "redirect:/quizzes";
    }

    private boolean isOwnerOrAdmin(Quiz quiz, UserDetails userDetails) {
        return userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")) ||
               quiz.getUser().getUsername().equals(userDetails.getUsername());
    }
}
