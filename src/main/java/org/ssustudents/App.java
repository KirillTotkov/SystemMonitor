package org.ssustudents;


import cn.hutool.core.io.unit.DataSizeUtil;
import com.formdev.flatlaf.FlatIntelliJLaf;
import org.ssustudents.models.CPU;
import org.ssustudents.views.MainFrame;
import oshi.SystemInfo;
import oshi.hardware.*;
import oshi.software.os.OperatingSystem;
import oshi.util.GlobalConfig;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class App {
    public static oshi.SystemInfo si;

    public static void main(String[] args) {
        if (System.getProperty("os.name").toLowerCase().contains("windows")) {
            GlobalConfig.set(GlobalConfig.OSHI_OS_WINDOWS_CPU_UTILITY, true);
        }

        si = new oshi.SystemInfo();

        FlatIntelliJLaf.setup();
        MainFrame mainFrame = new MainFrame();
        mainFrame.setVisible(true);
    }
}