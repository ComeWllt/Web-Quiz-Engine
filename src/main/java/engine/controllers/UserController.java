package engine.controllers;

import engine.dto.AccountDTO;
import engine.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(path = "/api/register")
    public long createUser(@Valid @RequestBody AccountDTO accountDTO) {
        return userService.registerUser(accountDTO);
    }
}
