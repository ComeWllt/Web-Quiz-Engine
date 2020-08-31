package engine.controllers;


import engine.dto.FeedbackDTO;
import engine.dto.SolutionDTO;
import engine.models.Quiz;
import engine.services.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class QuizController {

    @Autowired
    private QuizService quizService;

    @GetMapping(path = "/api/quizzes")
    public ResponseEntity<Page<Quiz>> getQuizzes(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        Page<Quiz> quizzes = quizService.getAllQuizzes(page, pageSize, sortBy);
        return new ResponseEntity<>(quizzes, HttpStatus.OK);
    }

    @GetMapping(path = "/api/quizzes/{id}")
    public ResponseEntity<Quiz> getQuiz(@PathVariable long id) {
        Quiz quiz = quizService.getQuiz(id);
        return new ResponseEntity<>(quiz, HttpStatus.OK);
    }

    @PostMapping(path = "/api/quizzes")
    public ResponseEntity<Quiz> createQuiz(@Valid @RequestBody Quiz quiz, Authentication authentication) {
        Quiz newQuiz = quizService.createQuiz(quiz, authentication);
        return new ResponseEntity<>(newQuiz, HttpStatus.OK);
    }

    @PutMapping(path = "/api/quizzes/{id}")
    public ResponseEntity<Quiz> updateQuiz(@PathVariable long id,
                                           @Valid @RequestBody Quiz quiz,
                                           Authentication authentication) {
        Quiz newQuiz = quizService.updateQuiz(id, quiz, authentication);
        return new ResponseEntity<>(newQuiz, HttpStatus.OK);
    }

    @PostMapping(path = "/api/quizzes/{id}/solve")
    public ResponseEntity<FeedbackDTO> checkAnswer(@PathVariable long id,
                                                   @Valid @RequestBody SolutionDTO solutionDTO,
                                                   Authentication authentication) {
        FeedbackDTO feedbackDTO = quizService.checkAnswer(id, solutionDTO, authentication);
        return new ResponseEntity<>(feedbackDTO, HttpStatus.OK);
    }

    @DeleteMapping(path = "/api/quizzes/{id}")
    public ResponseEntity<Long> deleteQuiz(@PathVariable long id, Authentication authentication) {
        quizService.deleteQuiz(id, authentication);
        return new ResponseEntity<>(id, HttpStatus.NO_CONTENT);
    }
}
