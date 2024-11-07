package com.example.beskbd.repositories;

import com.example.beskbd.entities.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TestRepo extends JpaRepository<Test, Integer> {
        Optional<Test> findByName(String name);

}
