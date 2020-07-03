package com.rup.pms.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class PMSException {

    public static void badRequestIfTrue(boolean result, String message) {
        if (result) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
        }
    }
}
