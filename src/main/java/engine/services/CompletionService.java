package engine.services;

import engine.models.Completion;
import engine.models.User;
import engine.repositories.CompletionRepository;
import engine.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CompletionService {

    @Autowired
    CompletionRepository completionRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    public Page<Completion> getCompletions(String username, Integer pageNo, Integer pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("completedAt").descending());
        User user = userService.loadUserByUsername(username);
        return completionRepository.findAllByUserId(user.getId(), paging);
    }

    public void addCompletion(String username, long quizId) {
        User user = userService.loadUserByUsername(username);
        Completion completion = new Completion(quizId);
        user.addCompletion(completion);
        userRepository.save(user);
    }

}
