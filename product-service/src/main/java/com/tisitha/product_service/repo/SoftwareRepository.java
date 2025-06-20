package com.tisitha.product_service.repo;

import com.tisitha.product_service.model.Software;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface SoftwareRepository extends JpaRepository<Software, UUID> {

    List<Software> findAllByLatest(boolean b);

    List<Software> findAllByTop(boolean b);

    List<Software> findAllByDealNot(int i);

    List<Software> findByNameContainingIgnoreCase(String text);

    Page<Software> findByBrandInAndYearsInAndUsesIn(
            List<String> brand,
            List<String> years,
            List<String> uses,
            Pageable pageable
    );

    @Query("SELECT DISTINCT p.brand FROM Software p")
    List<String> findDistinctBrand();

    @Query("SELECT DISTINCT p.years FROM Software p")
    List<String> findDistinctYears();

    @Query("SELECT DISTINCT p.uses FROM Software p")
    List<String> findDistinctUses();
}