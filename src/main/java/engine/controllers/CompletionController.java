package engine.controllers;

import engine.models.Completion;
import engine.services.CompletionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CompletionController {

    @Autowired
    CompletionService completionService;

    @GetMapping(path = "/api/quizzes/completed")
    public ResponseEntity<Page<Completion>> getUserCompletions(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize,
            Authentication authentication
    ) {
        Page<Completion> completions = completionService.getCompletions(authentication.getName(), page, pageSize);
        return new ResponseEntity<>(completions, HttpStatus.OK);
    }
}
