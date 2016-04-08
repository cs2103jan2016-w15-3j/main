package common;

public class Result {
    private boolean isSuccess;
    private String message;
    private Exception error;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public Result(boolean isSuccess, String message, Exception error) {

        this.isSuccess = isSuccess;
        this.message = message;
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Exception getError() {
        return error;
    }

    public void setError(Exception error) {
        this.error = error;
    }
}
