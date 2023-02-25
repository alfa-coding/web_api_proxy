package com.example.proxy.customproxy.controllers;

import java.net.http.HttpHeaders;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class PerformerController {

    @GetMapping("/proxy/**")
    public ResponseEntity<?> proxy(HttpServletRequest request) {
        // split the request path
        String[] path = request.getRequestURI().split("/proxy/");
        if (path.length < 2) {
            return ResponseEntity.badRequest().build();
        }

        // make a request call to the backend
        HttpHeaders headers = new HttpHeaders();
        headers.put("Accept", Arrays.asList("*/*"));
        HttpEntity<?> entity = new HttpEntity<>(headers);
        ResponseEntity<byte[]> response = restTemplate.exchange(path[1], HttpMethod.GET, entity, byte[].class);

        // return the response from the backend
        return ResponseEntity.status(response.getStatusCode()).headers(response.getHeaders()).body(response.getBody());
    }

}
