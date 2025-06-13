package com.tisitha.product_service.repo;

import com.tisitha.product_service.model.GraphicCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface GraphicCardRepository extends JpaRepository<GraphicCard, UUID> {

    List<GraphicCard> findAllByIsNew(boolean b);

    List<GraphicCard> findAllByIsTop(boolean b);

    List<GraphicCard> findAllByDealNot(int i);

    List<GraphicCard> findByNameContainingIgnoreCase(String text);

    List<GraphicCard> findByGpuManufacturerInAndGpuSeriesInAndVRamCapacityIn(
            List<String> gpuManufacturer,
            List<String> gpuSeries,
            List<String> vRamCapacity
    );

    @Query("SELECT DISTINCT g.gpuManufacturer FROM GraphicsCard g")
    List<String> findDistinctGpuManufacturer();

    @Query("SELECT DISTINCT g.gpuSeries FROM GraphicsCard g")
    List<String> findDistinctGpuSeries();

    @Query("SELECT DISTINCT g.vRamCapacity FROM GraphicsCard g")
    List<String> findDistinctVRamCapacity();
}
