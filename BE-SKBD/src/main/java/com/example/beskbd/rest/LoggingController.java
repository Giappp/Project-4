package com.example.beskbd.rest;

import com.example.beskbd.dto.response.ApiResponse;
import com.example.beskbd.dto.response.AuthenticationResponse;
import com.example.beskbd.services.AuthenticationService;
import com.example.beskbd.services.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class LoggingController {
    private static final Logger logger = LoggerFactory.getLogger(LoggingController.class);

    private final AuthenticationService authenticationService;
    private final TestService testService;

    @Autowired
    public LoggingController(AuthenticationService authenticationService, TestService testService) {
        this.authenticationService = authenticationService;
        this.testService = testService;
    }

    @PostMapping("/test")
    public ApiResponse<AuthenticationResponse> handleRequest(@RequestBody String body) {
        logger.info("Received POST request with body: {}", body);
        // Assuming you have logic to process the request and return an AuthenticationResponse
        AuthenticationResponse response = authenticationService.processRequest(body);

        return ApiResponse.<AuthenticationResponse>builder()
                .data(response)
                .message("Request processed successfully")
                .success(true)
                .build();
    }

    @GetMapping("/getTest")
    public ApiResponse<String> handleGetRequest() {
        String body = "test";
        String testResult = testService.getTest(body);  // Assuming getTest returns a String or relevant response
        logger.info("Received GET request, result: {}", testResult);
        return ApiResponse.<String>builder()
                .data(testResult)
                .message("GET request processed successfully")
                .success(true)
                .build();
    }
}