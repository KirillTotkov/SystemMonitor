package org.ssustudents.models;

import lombok.Data;
import org.ssustudents.App;
import oshi.hardware.ComputerSystem;

@Data
public class SystemInfo {
    private String deviceName;
    private GPU gpu;
    private Memory memory;
    private Disk disk;
    private CPU cpu;

    private static SystemInfo instance;

    private SystemInfo() {
        this.gpu = new GPU();
        this.memory = new Memory();
        this.disk = new Disk();
        this.cpu = new CPU();
    }

    public static SystemInfo getInstance() {
        if (instance == null) {
            instance = new SystemInfo();
        }
        return instance;
    }
    public void update() {

        ComputerSystem computerSystem = App.si.getHardware().getComputerSystem();
        StringBuilder deviceNameBuilder = new StringBuilder();
        deviceNameBuilder.append(computerSystem.getManufacturer());
        if (!"unknown".equalsIgnoreCase(computerSystem.getModel())) {
            deviceNameBuilder.append(" ").append(computerSystem.getModel());
        }
        deviceName = deviceNameBuilder.toString();

        gpu.updateGPUInfo();
        memory.updateRAMInfo();
        disk.updateInfo();
        cpu.updateInfo();
    }
}
