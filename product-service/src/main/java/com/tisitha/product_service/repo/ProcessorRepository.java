package com.tisitha.product_service.repo;

import com.tisitha.product_service.model.Processor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ProcessorRepository extends JpaRepository<Processor, UUID> {

    List<Processor> findAllByLatest(boolean b);

    List<Processor> findAllByTop(boolean b);

    List<Processor> findAllByDealNot(int i);

    List<Processor> findByNameContainingIgnoreCase(String text);

    List<Processor> findByBrandInAndCpuSeriesInAndCpuSocketInAndCoreCountInAndThreadCountInAndBaseClockSpeedGHzInAndIntegratedGraphicsIn(
            List<String> brand,
            List<String> cpuSeries,
            List<String> cpuSocket,
            List<String> coreCount,
            List<String> threadCount,
            List<String> baseClockSpeedGHz,
            List<String> integratedGraphics
    );

    @Query("SELECT DISTINCT c.brand FROM Processor c")
    List<String> findDistinctBrand();

    @Query("SELECT DISTINCT c.cpuSeries FROM Processor c")
    List<String> findDistinctCpuSeries();

    @Query("SELECT DISTINCT c.cpuSocket FROM Processor c")
    List<String> findDistinctCpuSocket();

    @Query("SELECT DISTINCT c.coreCount FROM Processor c")
    List<String> findDistinctCoreCount();

    @Query("SELECT DISTINCT c.threadCount FROM Processor c")
    List<String> findDistinctThreadCount();

    @Query("SELECT DISTINCT c.baseClockSpeedGHz FROM Processor c")
    List<String> findDistinctBaseClockSpeedGHz();

    @Query("SELECT DISTINCT c.integratedGraphics FROM Processor c")
    List<String> findDistinctIntegratedGraphics();
}
