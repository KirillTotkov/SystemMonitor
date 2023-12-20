package org.ssustudents.models;

import cn.hutool.http.useragent.Platform;
import lombok.Data;
import lombok.Getter;
import org.ssustudents.App;
import oshi.PlatformEnum;
import oshi.SystemInfo;
import oshi.software.os.OSProcess;
import oshi.software.os.OperatingSystem;
import oshi.util.GlobalConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.ssustudents.App.si;

@Getter
public class ProcessList {
    private List<ProcessInfo> processes;

    public ProcessList() {
        update();
    }

    public void update() {
        OperatingSystem os = si.getOperatingSystem();
        List<OSProcess> osProcesses = os.getProcesses();
        processes = new ArrayList<>();
        int logicalProcessors = si.getHardware().getProcessor().getLogicalProcessorCount();
        long totalCpuTicks = si.getHardware().getProcessor().getSystemCpuLoadTicks()[0];
        for (OSProcess p : osProcesses) {
            // Игнорировать процесс простоя в Windows
            if (p.getProcessID() > 0 || !SystemInfo.getCurrentPlatform().equals(PlatformEnum.WINDOWS)) {
                ProcessInfo processInfo = new ProcessInfo();
                processInfo.setName(p.getName());
                processInfo.setId(p.getProcessID());


                double processCpu = p.getProcessCpuLoadBetweenTicks(p) *100d;

                processInfo.setCpuLoadPercent(processCpu);

                processInfo.setRamUsed(p.getResidentSetSize());
                processes.add(processInfo);
            }
        }
    }

    public void killProcess(int pid) {
        Optional<ProcessHandle> proc = ProcessHandle.of(pid);
        if (proc.isPresent()) {
            proc.get().destroy();
        }
    }

    @Data
    public static class ProcessInfo {
        private String name;
        private int id;
        private double cpuLoadPercent;
        private long ramUsed;
        private long ramFree;
        private long diskOccupied;
        private long diskFree;
        private List<Double> cpuLoadPercentHistory = new ArrayList<>();
        private static final int HISTORY_SIZE = 2;
        public static long previousTime;

    }
}
