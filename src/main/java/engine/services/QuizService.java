package engine.services;

import engine.dto.FeedbackDTO;
import engine.dto.SolutionDTO;
import engine.exceptions.NotCreatorException;
import engine.exceptions.QuizNotFoundException;
import engine.models.Quiz;
import engine.repositories.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class QuizService {
    @Autowired
    QuizRepository quizRepository;

    @Autowired
    CompletionService completionService;

    public Quiz getQuiz(long id) {
        return quizRepository.findById(id)
                .orElseThrow(() -> new QuizNotFoundException(id));
    }

    public Page<Quiz> getAllQuizzes(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        return quizRepository.findAll(paging);
    }

    public Quiz createQuiz(Quiz quiz, Authentication authentication) {
        quiz.setCreator(authentication.getName());
        return quizRepository.save(quiz);
    }

    public FeedbackDTO checkAnswer(long id, SolutionDTO solutionDTO, Authentication authentication) {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new QuizNotFoundException(id));
        if (solutionDTO.answer.containsAll(quiz.getAnswer()) && quiz.getAnswer().containsAll(solutionDTO.answer)) {
            completionService.addCompletion(authentication.getName(), id);
            return FeedbackDTO.SUCCESS;
        }
        return FeedbackDTO.FAILURE;
    }

    public Quiz updateQuiz(long id, Quiz newQuiz, Authentication authentication) {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new QuizNotFoundException(id));
        checkQuizCreator(quiz, authentication);
        newQuiz.setId(id);
        newQuiz.setCreator(authentication.getName());
        return quizRepository.save(newQuiz);
    }

    public void deleteQuiz(long id, Authentication authentication) {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new QuizNotFoundException(id));
        checkQuizCreator(quiz, authentication);
        quizRepository.deleteById(id);
    }

    private void checkQuizCreator(Quiz quiz, Authentication authentication) {
        if (!Objects.equals(quiz.getCreator(), authentication.getName())) {
            throw new NotCreatorException(quiz.getId());
        }
    }

}
