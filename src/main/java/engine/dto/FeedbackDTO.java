package engine.dto;

public class FeedbackDTO {

    public static FeedbackDTO SUCCESS = new FeedbackDTO(true, "Congratulations, you're right!");
    public static FeedbackDTO FAILURE = new FeedbackDTO(false, "Wrong answer! Please, try again.");

    private final boolean success;
    private final String feedback;

    public FeedbackDTO(boolean success, String feedback) {
        this.success = success;
        this.feedback = feedback;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getFeedback() {
        return feedback;
    }
}
