package com.tisitha.product_service.repo;

import com.tisitha.product_service.model.MotherBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface MotherBoardRepository extends JpaRepository<MotherBoard, UUID> {

    List<MotherBoard> findAllByIsNew(boolean b);

    List<MotherBoard> findAllByIsTop(boolean b);

    List<MotherBoard> findAllByDealNot(int i);

    List<MotherBoard> findByNameContainingIgnoreCase(String text);

    List<MotherBoard> findByBrandInAndCpuSocketInAndChipsetSeriesInAndFormFactorInAndRamTypeInAndPcieSlotVersionInAndM2SlotsInAndWirelessConnectivityIn(
            List<String> brand,
            List<String> cpuSocket,
            List<String> chipsetSeries,
            List<String> formFactor,
            List<String> ramType,
            List<String> pcieSlotVersion,
            List<String> m2Slots,
            List<String> wirelessConnectivity
    );

    @Query("SELECT DISTINCT m.brand FROM Motherboard m")
    List<String> findDistinctBrand();

    @Query("SELECT DISTINCT m.cpuSocket FROM Motherboard m")
    List<String> findDistinctCpuSocket();

    @Query("SELECT DISTINCT m.chipsetSeries FROM Motherboard m")
    List<String> findDistinctChipsetSeries();

    @Query("SELECT DISTINCT m.formFactor FROM Motherboard m")
    List<String> findDistinctFormFactor();

    @Query("SELECT DISTINCT m.ramType FROM Motherboard m")
    List<String> findDistinctRamType();

    @Query("SELECT DISTINCT m.pcieSlotVersion FROM Motherboard m")
    List<String> findDistinctPcieSlotVersion();

    @Query("SELECT DISTINCT m.m2Slots FROM Motherboard m")
    List<String> findDistinctM2Slots();

    @Query("SELECT DISTINCT m.wirelessConnectivity FROM Motherboard m")
    List<String> findDistinctWirelessConnectivity();
}
