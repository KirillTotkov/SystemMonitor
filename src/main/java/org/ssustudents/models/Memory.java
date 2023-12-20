package org.ssustudents.models;

import cn.hutool.core.io.unit.DataSizeUtil;
import lombok.Data;
import org.ssustudents.App;
import oshi.hardware.GlobalMemory;
import oshi.hardware.PhysicalMemory;
import oshi.util.FormatUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Data
public class Memory {
    private List<PhysicalMemory> memories;
    private long total;
    private long used;
    private long free;

    public Memory() {
    }

    public void updateRAMInfo() {
        GlobalMemory globalMemory = App.si.getHardware().getMemory();
        total = 0;
        memories = globalMemory.getPhysicalMemory();
        for (PhysicalMemory physicalMemory : memories) {
            total += physicalMemory.getCapacity();
        }

        used = globalMemory.getTotal() - globalMemory.getAvailable();
        free = globalMemory.getAvailable();

    }
}
