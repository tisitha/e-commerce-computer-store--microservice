package com.tisitha.product_service.repo;

import com.tisitha.product_service.model.Monitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface MonitorRepository extends JpaRepository<Monitor, UUID> {

    List<Monitor> findAllByLatest(boolean b);

    List<Monitor> findAllByTop(boolean b);

    List<Monitor> findAllByDealNot(int i);

    List<Monitor> findByNameContainingIgnoreCase(String text);

    List<Monitor> findByBrandInAndDisplayResolutionInAndRefreshRateHzInAndResponseTimeMsInAndPanelTypeInAndAspectRatioInAndAdaptiveSyncTechnologyIn(
            List<String> brand,
            List<String> displayResolution,
            List<String> refreshRateHz,
            List<String> responseTimeMs,
            List<String> panelType,
            List<String> aspectRatio,
            List<String> adaptiveSyncTechnology
    );

    @Query("SELECT DISTINCT m.brand FROM Monitor m")
    List<String> findDistinctBrand();

    @Query("SELECT DISTINCT m.displayResolution FROM Monitor m")
    List<String> findDistinctDisplayResolution();

    @Query("SELECT DISTINCT m.refreshRateHz FROM Monitor m")
    List<String> findDistinctRefreshRateHz();

    @Query("SELECT DISTINCT m.responseTimeMs FROM Monitor m")
    List<String> findDistinctResponseTimeMs();

    @Query("SELECT DISTINCT m.panelType FROM Monitor m")
    List<String> findDistinctPanelType();

    @Query("SELECT DISTINCT m.aspectRatio FROM Monitor m")
    List<String> findDistinctAspectRatio();

    @Query("SELECT DISTINCT m.adaptiveSyncTechnology FROM Monitor m")
    List<String> findDistinctAdaptiveSyncTechnology();
}
