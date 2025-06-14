package com.tisitha.product_service.repo;

import com.tisitha.product_service.model.Cooling;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface CoolingRepository extends JpaRepository<Cooling, UUID> {

    List<Cooling> findAllByIsNew(boolean b);

    List<Cooling> findAllByIsTop(boolean b);

    List<Cooling> findAllByDealNot(int i);

    List<Cooling> findByBrandInAndCoolingTypeInAndSocketCompatibilityInAndFanSizeInAndRgbLightingIn(
            List<String> brand,
            List<String> coolingType,
            List<String> socketCompatibility,
            List<String> fanSize,
            List<String> rgbLighting
    );

    @Query("SELECT DISTINCT c.brand FROM Cooling c")
    List<String> findDistinctBrand();

    @Query("SELECT DISTINCT c.coolingType FROM Cooling c")
    List<String> findDistinctCoolingType();

    @Query("SELECT DISTINCT c.socketCompatibility FROM Cooling c")
    List<String> findDistinctSocketCompatibility();

    @Query("SELECT DISTINCT c.fanSize FROM Cooling c")
    List<String> findDistinctFanSize();

    @Query("SELECT DISTINCT c.rgbLighting FROM Cooling c")
    List<String> findDistinctRgbLighting();

    List<Cooling> findByNameContainingIgnoreCase(String text);
}
