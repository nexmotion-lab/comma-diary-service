package com.coders.diaryservice.exception.eventTag;

public class DuplicateEventTagUpdateException extends RuntimeException{

    public DuplicateEventTagUpdateException(String message) {
        super(message);
    }
}
