package com.tisitha.product_service.repo;

import com.tisitha.product_service.model.Memory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface MemoryRepository extends JpaRepository<Memory, UUID> {

    List<Memory> findAllByLatest(boolean b);

    List<Memory> findAllByTop(boolean b);

    List<Memory> findAllByDealNot(int i);

    List<Memory> findByNameContainingIgnoreCase(String text);

    Page<Memory> findByMemoryTypeInAndCapacityGBInAndSpeedMHzInAndFormFactorInAndRgbLightingInAndBrandIn(
            List<String> memoryType,
            List<String> capacityGB,
            List<String> speedMHz,
            List<String> formFactor,
            List<String> rgbLighting,
            List<String> brand,
            Pageable pageable
    );

    @Query("SELECT DISTINCT m.memoryType FROM Memory m")
    List<String> findDistinctMemoryType();

    @Query("SELECT DISTINCT m.capacityGB FROM Memory m")
    List<String> findDistinctCapacityGB();

    @Query("SELECT DISTINCT m.speedMHz FROM Memory m")
    List<String> findDistinctSpeedMHz();

    @Query("SELECT DISTINCT m.formFactor FROM Memory m")
    List<String> findDistinctFormFactor();

    @Query("SELECT DISTINCT m.rgbLighting FROM Memory m")
    List<String> findDistinctRgbLighting();

    @Query("SELECT DISTINCT m.brand FROM Memory m")
    List<String> findDistinctBrand();
}
