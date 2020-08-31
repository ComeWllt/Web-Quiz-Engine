package engine.dto;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class AccountDTO {

    @NotBlank(message = "Please provide an email")
    @Email(message = "Please provide a valid email address")
    @Pattern(regexp=".+@.+\\..+", message="Please provide a valid email address")
    private String email;
    @NotBlank(message = "Password should be at least 5 characters long")
    @Size(min = 5, message = "Password should be at least 5 characters long")
    private String password;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
