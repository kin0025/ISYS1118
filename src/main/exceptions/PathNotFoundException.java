/*
 * Copyright (c) 2017. Alexander Kinross-Smith, s3603437
 */

package main.exceptions;

public class PathNotFoundException extends Exception {
    public PathNotFoundException() {
    }

    public PathNotFoundException(String message) {
        super(message);
    }
}
