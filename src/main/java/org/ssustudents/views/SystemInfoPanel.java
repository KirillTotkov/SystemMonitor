/*
 * Created by JFormDesigner on Fri Nov 24 21:39:50 MSK 2023
 */

package org.ssustudents.views;

import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import cn.hutool.core.io.unit.DataSizeUtil;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import net.miginfocom.swing.*;
import org.ssustudents.models.GPU;
import org.ssustudents.models.SystemInfo;
import oshi.hardware.PhysicalMemory;
import oshi.util.FormatUtil;

import javax.swing.*;

/**
 * @author kirill
 */
public class SystemInfoPanel extends JPanel {
    SystemInfo systemInfo;

    public SystemInfoPanel() {
        initComponents();
        systemInfo = SystemInfo.getInstance();
        setupUi();
        setupInfo();
    }


    private void setupUi() {
        lblDeviceName.setIcon(new FlatSVGIcon("icons/pc.svg", 24, 24));

//        lblProcessorHint.setIcon(new FlatSVGIcon("icons/cpu.svg",20,20));
//        lblRamHint.setIcon(new FlatSVGIcon("icons/res.svg",20,20));
//        lblDiskHint.setIcon(new FlatSVGIcon("icons/hard-disk.svg",20,20));
    }

    public void setupInfo() {
        systemInfo.update();
        showInfo();
    }

    private void showInfo() {
        lblDeviceName.setText(systemInfo.getDeviceName());
        lblProcessor.setText(systemInfo.getCpu().getName());
        lblMemory.setText(getMemoryInfo());
        lblGpu.setText(getGPUInfo());
        lblDisk.setText(getDisksInfo());
    }

    private String getMemoryInfo() {
        StringBuilder memoryInfoBuilder = new StringBuilder();
        List<String> detailList = new ArrayList<>();

        List<PhysicalMemory> physicalMemories = systemInfo.getMemory().getMemories();
        StringBuilder detailBuilder;
        for (PhysicalMemory physicalMemory : physicalMemories) {
            detailBuilder = new StringBuilder();
            detailBuilder.append(physicalMemory.getManufacturer());
            detailBuilder.append(" ").append(physicalMemory.getMemoryType());
            detailBuilder.append(" ").append(new BigDecimal(physicalMemory.getClockSpeed()).divide(new BigDecimal(1000000), 0, RoundingMode.HALF_UP)).append("MHz");
            detailBuilder.append(" ").append(DataSizeUtil.format(physicalMemory.getCapacity()));
            detailList.add(detailBuilder.toString());
        }
        memoryInfoBuilder.append(DataSizeUtil.format(systemInfo.getMemory().getTotal()));
        memoryInfoBuilder.append(" (").append(String.join(" + ", detailList)).append(")");

        return memoryInfoBuilder.toString();
    }

    private String getGPUInfo() {
        StringBuilder gpuBuilder = new StringBuilder();
        List<GPU.Card> cards = systemInfo.getGpu().getCards();
        for (GPU.Card card : cards) {
            gpuBuilder.append(card.getName());
            gpuBuilder.append(" ");
            gpuBuilder.append(FormatUtil.formatBytes(card.getMemory()));
            gpuBuilder.append("; ");
        }
        return gpuBuilder.deleteCharAt(gpuBuilder.length() - 2).toString();
    }

    private String getDisksInfo() {
        return systemInfo.getDisk().getModel() + " " + FormatUtil.formatBytesDecimal(systemInfo.getDisk().getTotalSpace());
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - aaa
        panel2 = new JPanel();
        lblDeviceName = new JLabel();
        separator1 = new JSeparator();
        panel3 = new JPanel();
        lblProcessorHint = new JLabel();
        lblProcessor = new JLabel();
        lblMemoryHint = new JLabel();
        lblMemory = new JLabel();
        lblGpuHint = new JLabel();
        lblGpu = new JLabel();
        lblDiskHint = new JLabel();
        lblDisk = new JLabel();

        //======== this ========
        setFont(new Font("sansserif", Font.BOLD, 17));
        setBorder(new javax.swing.border.CompoundBorder(new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder
        (0,0,0,0), "JFor\u006dDesi\u0067ner \u0045valu\u0061tion",javax.swing.border.TitledBorder.CENTER,javax.swing.border
        .TitledBorder.BOTTOM,new java.awt.Font("Dia\u006cog",java.awt.Font.BOLD,12),java.awt
        .Color.red), getBorder())); addPropertyChangeListener(new java.beans.PropertyChangeListener(){@Override public void
        propertyChange(java.beans.PropertyChangeEvent e){if("bord\u0065r".equals(e.getPropertyName()))throw new RuntimeException()
        ;}});
        setLayout(new MigLayout(
            "fillx,hidemode 3",
            // columns
            "[fill]",
            // rows
            "[]" +
            "[]" +
            "[]"));

        //======== panel2 ========
        {
            panel2.setLayout(new MigLayout(
                "hidemode 3",
                // columns
                "[fill]" +
                "[fill]" +
                "[fill]" +
                "[fill]" +
                "[fill]" +
                "[fill]" +
                "[fill]" +
                "[fill]" +
                "[fill]" +
                "[fill]" +
                "[fill]" +
                "[fill]" +
                "[fill]" +
                "[fill]" +
                "[fill]" +
                "[fill]" +
                "[fill]" +
                "[fill]" +
                "[fill]" +
                "[fill]",
                // rows
                "[]"));

            //---- lblDeviceName ----
            lblDeviceName.setText("DeviceName");
            lblDeviceName.setFont(new Font("sansserif", Font.PLAIN, 24));
            panel2.add(lblDeviceName, "cell 0 0");
        }
        add(panel2, "cell 0 0");
        add(separator1, "cell 0 1");

        //======== panel3 ========
        {
            panel3.setLayout(new MigLayout(
                "hidemode 3",
                // columns
                "[fill]" +
                "[fill]",
                // rows
                "[]" +
                "[]" +
                "[]" +
                "[]"));

            //---- lblProcessorHint ----
            lblProcessorHint.setText("\u041f\u0440\u043e\u0446\u0435\u0441\u0441\u043e\u0440:");
            lblProcessorHint.setFont(new Font("sansserif", Font.BOLD, 17));
            panel3.add(lblProcessorHint, "cell 0 0");

            //---- lblProcessor ----
            lblProcessor.setText("text");
            lblProcessor.setFont(new Font("Inter", Font.PLAIN, 16));
            panel3.add(lblProcessor, "cell 1 0");

            //---- lblMemoryHint ----
            lblMemoryHint.setText("\u041f\u0430\u043c\u044f\u0442\u044c:");
            lblMemoryHint.setFont(new Font("sansserif", Font.BOLD, 17));
            panel3.add(lblMemoryHint, "cell 0 1");

            //---- lblMemory ----
            lblMemory.setText("text");
            lblMemory.setFont(new Font("Inter", Font.PLAIN, 16));
            panel3.add(lblMemory, "cell 1 1");

            //---- lblGpuHint ----
            lblGpuHint.setText("GPU:");
            lblGpuHint.setFont(new Font("sansserif", Font.BOLD, 17));
            panel3.add(lblGpuHint, "cell 0 2");

            //---- lblGpu ----
            lblGpu.setText("text");
            lblGpu.setFont(new Font("Inter", Font.PLAIN, 16));
            panel3.add(lblGpu, "cell 1 2");

            //---- lblDiskHint ----
            lblDiskHint.setText("\u0414\u0438\u0441\u043a:");
            lblDiskHint.setFont(new Font("sansserif", Font.BOLD, 17));
            lblDiskHint.setIcon(null);
            panel3.add(lblDiskHint, "cell 0 3");

            //---- lblDisk ----
            lblDisk.setText("text");
            lblDisk.setFont(new Font("Inter", Font.PLAIN, 16));
            panel3.add(lblDisk, "cell 1 3");
        }
        add(panel3, "cell 0 2");
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }


    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - aaa
    private JPanel panel2;
    private JLabel lblDeviceName;
    private JSeparator separator1;
    private JPanel panel3;
    private JLabel lblProcessorHint;
    private JLabel lblProcessor;
    private JLabel lblMemoryHint;
    private JLabel lblMemory;
    private JLabel lblGpuHint;
    private JLabel lblGpu;
    private JLabel lblDiskHint;
    private JLabel lblDisk;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
