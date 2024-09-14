package dev.moon.storebackend.service.repository;

import dev.moon.storebackend.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
