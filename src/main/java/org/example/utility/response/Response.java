package org.example.utility.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
public class Response {
    private int status;
    private List<String> message;

    private static Response get(List<String> message, int status) {
        Response response = new Response();
        response.status = status;
        response.message = message;
        return response;
    }

    public static ResponseEntity<Response> create(List<String> message, HttpStatus status) {
        return new ResponseEntity<>(Response.get(message, status.value()), status);
    }
}


