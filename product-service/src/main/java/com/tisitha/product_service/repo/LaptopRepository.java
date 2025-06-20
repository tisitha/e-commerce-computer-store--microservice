package com.tisitha.product_service.repo;

import com.tisitha.product_service.model.Laptop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface LaptopRepository extends JpaRepository<Laptop, UUID> {

    List<Laptop> findAllByLatest(boolean b);

    List<Laptop> findAllByTop(boolean b);

    List<Laptop> findAllByDealNot(int i);

    List<Laptop> findByNameContainingIgnoreCase(String text);

    Page<Laptop> findByBrandInAndProcessorBrandInAndProcessorSeriesInAndRamCapacityInAndStorageCapacityInAndDisplayResolutionInAndOperatingSystemInAndGraphicsCardTypeInAndFeaturesIncludedIn(
            List<String> brand,
            List<String> processorBrand,
            List<String> processorSeries,
            List<String> ramCapacity,
            List<String> storageCapacity,
            List<String> displayResolution,
            List<String> operatingSystem,
            List<String> graphicsCardType,
            List<String> featuresIncluded,
            Pageable pageable
    );

    @Query("SELECT DISTINCT l.brand FROM Laptop l")
    List<String> findDistinctBrand();

    @Query("SELECT DISTINCT l.processorBrand FROM Laptop l")
    List<String> findDistinctProcessorBrand();

    @Query("SELECT DISTINCT l.processorSeries FROM Laptop l")
    List<String> findDistinctProcessorSeries();

    @Query("SELECT DISTINCT l.ramCapacity FROM Laptop l")
    List<String> findDistinctRamCapacity();

    @Query("SELECT DISTINCT l.storageCapacity FROM Laptop l")
    List<String> findDistinctStorageCapacity();

    @Query("SELECT DISTINCT l.displayResolution FROM Laptop l")
    List<String> findDistinctDisplayResolution();

    @Query("SELECT DISTINCT l.operatingSystem FROM Laptop l")
    List<String> findDistinctOperatingSystem();

    @Query("SELECT DISTINCT l.graphicsCardType FROM Laptop l")
    List<String> findDistinctGraphicsCardType();

    @Query("SELECT DISTINCT l.featuresIncluded FROM Laptop l")
    List<String> findDistinctFeaturesIncluded();
}
