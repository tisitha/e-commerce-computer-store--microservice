package com.tisitha.product_service.repo;

import com.tisitha.product_service.model.Casing;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface CasingRepository extends JpaRepository<Casing, UUID> {

    List<Casing> findAllByLatest(boolean b);

    List<Casing> findAllByTop(boolean b);

    List<Casing> findAllByDealNot(int i);

    List<Casing> findByNameContainingIgnoreCase(String text);

    Page<Casing> findByBrandInAndCaseTypeInAndMaxGPULengthInAndIncludedFansIn(List<String> brand, List<String> caseType, List<String> maxGPULength, List<String> includedFans, Pageable pageable);

    @Query("SELECT DISTINCT c.caseType FROM Casing c")
    List<String> findDistinctCaseType();

    @Query("SELECT DISTINCT c.maxGPULength FROM Casing c")
    List<String> findDistinctMaxGPULength();

    @Query("SELECT DISTINCT c.includedFans FROM Casing c")
    List<String> findDistinctIncludedFans();

    @Query("SELECT DISTINCT c.brand FROM Casing c")
    List<String> findDistinctBrand();
}
