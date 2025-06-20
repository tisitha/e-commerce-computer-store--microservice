package com.tisitha.product_service.repo;

import com.tisitha.product_service.model.Peripheral;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface PeripheralRepository extends JpaRepository<Peripheral, UUID> {

    List<Peripheral> findAllByLatest(boolean b);

    List<Peripheral> findAllByTop(boolean b);

    List<Peripheral> findAllByDealNot(int i);

    List<Peripheral> findByNameContainingIgnoreCase(String text);

    Page<Peripheral> findByBrandInAndPeripheralTypeInAndConnectivityTypeInAndRgbLightingIn(
            List<String> brand,
            List<String> peripheralType,
            List<String> connectivityType,
            List<String> rgbLighting,
            Pageable pageable
    );

    @Query("SELECT DISTINCT p.brand FROM Peripheral p")
    List<String> findDistinctBrand();

    @Query("SELECT DISTINCT p.peripheralType FROM Peripheral p")
    List<String> findDistinctPeripheralType();

    @Query("SELECT DISTINCT p.connectivityType FROM Peripheral p")
    List<String> findDistinctConnectivityType();

    @Query("SELECT DISTINCT p.rgbLighting FROM Peripheral p")
    List<String> findDistinctRgbLighting();
}
