package com.banco.api.published.response.collectService.list;

public class CollectServiceError {

    private String code;
    private String message;

    public CollectServiceError() {
    }

    public CollectServiceError(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "CollectServiceError{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
