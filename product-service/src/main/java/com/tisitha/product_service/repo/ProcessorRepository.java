package com.tisitha.product_service.repo;

import com.tisitha.product_service.model.Processor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ProcessorRepository extends JpaRepository<Processor, UUID> {

    List<Processor> findAllByIsNew(boolean b);

    List<Processor> findAllByIsTop(boolean b);

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

    @Query("SELECT DISTINCT c.brand FROM Cpu c")
    List<String> findDistinctBrand();

    @Query("SELECT DISTINCT c.cpuSeries FROM Cpu c")
    List<String> findDistinctCpuSeries();

    @Query("SELECT DISTINCT c.cpuSocket FROM Cpu c")
    List<String> findDistinctCpuSocket();

    @Query("SELECT DISTINCT c.coreCount FROM Cpu c")
    List<String> findDistinctCoreCount();

    @Query("SELECT DISTINCT c.threadCount FROM Cpu c")
    List<String> findDistinctThreadCount();

    @Query("SELECT DISTINCT c.baseClockSpeedGHz FROM Cpu c")
    List<String> findDistinctBaseClockSpeedGHz();

    @Query("SELECT DISTINCT c.integratedGraphics FROM Cpu c")
    List<String> findDistinctIntegratedGraphics();
}
