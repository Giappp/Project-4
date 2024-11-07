package com.example.beskbd.services;

import com.example.beskbd.repositories.TestRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService {
    @Autowired
    TestRepo testRepo;

    public String getTest(String name) {
        if (testRepo.findByName(name) != null) {
            return name;
        }
        return "not found";
    }

}
