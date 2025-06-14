package com.tisitha.product_service.repo;

import com.tisitha.product_service.model.GraphicsCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface GraphicsCardRepository extends JpaRepository<GraphicsCard, UUID> {

    List<GraphicsCard> findAllByIsNew(boolean b);

    List<GraphicsCard> findAllByIsTop(boolean b);

    List<GraphicsCard> findAllByDealNot(int i);

    List<GraphicsCard> findByNameContainingIgnoreCase(String text);

    List<GraphicsCard> findByGpuManufacturerInAndGpuSeriesInAndVramGbIn(
            List<String> gpuManufacturer,
            List<String> gpuSeries,
            List<String> vramGb
    );

    @Query("SELECT DISTINCT g.gpuManufacturer FROM GraphicsCard g")
    List<String> findDistinctGpuManufacturer();

    @Query("SELECT DISTINCT g.gpuSeries FROM GraphicsCard g")
    List<String> findDistinctGpuSeries();

    @Query("SELECT DISTINCT g.vramGb FROM GraphicsCard g")
    List<String> findDistinctVRamCapacity();
}
