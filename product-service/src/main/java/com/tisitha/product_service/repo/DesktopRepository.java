package com.tisitha.product_service.repo;

import com.tisitha.product_service.model.Desktop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface DesktopRepository extends JpaRepository<Desktop, UUID> {

    List<Desktop> findAllByIsNew(boolean b);

    List<Desktop> findAllByIsTop(boolean b);

    List<Desktop> findAllByDealNot(int i);

    List<Desktop> findByNameContainingIgnoreCase(String text);

    List<Desktop> findByBrandInAndProductTypeInAndProcessorBrandInAndProcessorSeriesInAndGpuManufacturerInAndGpuSeriesInAndRamCapacityInAndStorageTypeInAndStorageCapacityInAndOperatingSystemIn(
            List<String> brand,
            List<String> productType,
            List<String> processorBrand,
            List<String> processorSeries,
            List<String> gpuManufacturer,
            List<String> gpuSeries,
            List<String> ramCapacity,
            List<String> storageType,
            List<String> storageCapacity,
            List<String> operatingSystem
    );

    @Query("SELECT DISTINCT p.brand FROM Desktop p")
    List<String> findDistinctBrand();

    @Query("SELECT DISTINCT p.productType FROM Desktop p")
    List<String> findDistinctProductType();

    @Query("SELECT DISTINCT p.processorBrand FROM Desktop p")
    List<String> findDistinctProcessorBrand();

    @Query("SELECT DISTINCT p.processorSeries FROM Desktop p")
    List<String> findDistinctProcessorSeries();

    @Query("SELECT DISTINCT p.gpuManufacturer FROM Desktop p")
    List<String> findDistinctGpuManufacturer();

    @Query("SELECT DISTINCT p.gpuSeries FROM Desktop p")
    List<String> findDistinctGpuSeries();

    @Query("SELECT DISTINCT p.ramCapacity FROM Desktop p")
    List<String> findDistinctRamCapacity();

    @Query("SELECT DISTINCT p.storageType FROM Desktop p")
    List<String> findDistinctStorageType();

    @Query("SELECT DISTINCT p.storageCapacity FROM Desktop p")
    List<String> findDistinctStorageCapacity();

    @Query("SELECT DISTINCT p.operatingSystem FROM Desktop p")
    List<String> findDistinctOperatingSystem();
}
