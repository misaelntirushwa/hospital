package com.ntm.hospital.exception;

public class PhysicianNotFoundException extends RuntimeException {

    public PhysicianNotFoundException(String exception) {
        super(exception);
    }
}
