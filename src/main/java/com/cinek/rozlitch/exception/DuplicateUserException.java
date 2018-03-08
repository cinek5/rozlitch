package com.cinek.rozlitch.exception;

/**
 * Created by Cinek on 17.11.2017.
 */
public class DuplicateUserException extends RuntimeException {
    public DuplicateUserException() {
        super("error: username you've chosen already exists");
    }
}
