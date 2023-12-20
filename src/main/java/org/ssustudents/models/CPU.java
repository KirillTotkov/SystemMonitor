package org.ssustudents.models;

import lombok.Getter;
import org.ssustudents.App;
import oshi.hardware.CentralProcessor;


public class CPU {
    @Getter
    private String name;
    @Getter
    private int socketsCount;
    @Getter
    private int kernelsCount;
    @Getter
    private int logicalProcessorCount;
    @Getter
    private long baseSpeed;
    private int processCount;
    private int threadCount;
    private double systemCpuLoadBetweenTicks;
    private long[] systemCpuLoadTicks;
    private double[] processorCpuLoadBetweenTicks;
    private long[] processorCpuLoadTicks;
    private CentralProcessor processor;

    public CPU() {
        processor = App.si.getHardware().getProcessor();
    }

    public int getProcessCount() {
        return App.si.getOperatingSystem().getProcessCount();
    }

    public int getThreadCount() {
        return  App.si.getOperatingSystem().getThreadCount();
    }

    public double getSystemCpuLoadBetweenTicks(long[] oldTicks) {
        return processor.getSystemCpuLoadBetweenTicks(oldTicks);
    }

    public long[] getSystemCpuLoadTicks() {
        return processor.getSystemCpuLoadTicks();
    }

    public double[] getProcessorCpuLoadBetweenTicks(long[][] oldProcTicks) {
        return processor.getProcessorCpuLoadBetweenTicks(oldProcTicks);
    }
    public long[][] getProcessorCpuLoadTicks(){
        return processor.getProcessorCpuLoadTicks();
    }

    public double getSystemCpuLoadWithDelay(long delay) {
        return processor.getSystemCpuLoad(delay);
    }

    public void updateInfo() {
        CentralProcessor.ProcessorIdentifier processorIdentifier = processor.getProcessorIdentifier();
        name = processorIdentifier.getName();
        socketsCount = processor.getPhysicalPackageCount();
        kernelsCount = processor.getPhysicalProcessorCount();
        logicalProcessorCount = processor.getLogicalProcessorCount();
        baseSpeed = processor.getProcessorIdentifier().getVendorFreq();
        while (baseSpeed<0){
            baseSpeed = processor.getProcessorIdentifier().getVendorFreq();
        }
    }
}
