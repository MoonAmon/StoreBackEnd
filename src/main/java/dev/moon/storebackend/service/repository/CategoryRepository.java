package dev.moon.storebackend.service.repository;

import dev.moon.storebackend.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.validation.ObjectError;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String name);
    boolean existsByName(String name);
}
