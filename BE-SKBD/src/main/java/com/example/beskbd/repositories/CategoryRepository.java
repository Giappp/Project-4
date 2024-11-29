package com.example.beskbd.repositories;

import com.example.beskbd.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Boolean existsByNameAndGender(String name, Category.Gender gender);
}
