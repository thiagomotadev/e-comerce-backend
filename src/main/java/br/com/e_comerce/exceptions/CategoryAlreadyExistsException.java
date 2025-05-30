package br.com.e_comerce.exceptions;

public class CategoryAlreadyExistsException extends RuntimeException {
    public CategoryAlreadyExistsException(String message) {
        super(message);
    }
}
