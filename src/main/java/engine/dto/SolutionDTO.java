package engine.dto;

import javax.validation.constraints.NotNull;
import java.util.List;

public class SolutionDTO {
    @NotNull(message = "Please send answers (empty list eventually)")
    public List<Integer> answer;

    public List<Integer> getAnswer() {
        return answer;
    }
}
