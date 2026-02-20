package com.tecniserviceartefactos.api.exception;

public class StorageFileNotFoundException extends RecursoNoEncontradoException {
    public StorageFileNotFoundException(String message) {
        super(message);
    }

    public StorageFileNotFoundException(String message, Throwable cause) {
        super(message);
    }
}
