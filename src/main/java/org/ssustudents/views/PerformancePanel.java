/*
 * Created by JFormDesigner on Mon Dec 04 00:13:27 MSK 2023
 */

package org.ssustudents.views;

import java.awt.*;
import javax.swing.*;

import net.miginfocom.swing.*;
import org.jfree.chart.*;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.DynamicTimeSeriesCollection;
import org.jfree.data.time.Second;
import org.ssustudents.models.CPU;
import org.ssustudents.models.Disk;
import org.ssustudents.models.Memory;
import org.ssustudents.models.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.util.FormatUtil;

/**
 * @author ktotk
 */
public class PerformancePanel extends JPanel {
    private Disk disk;
    private CPU cpu;
    private Memory memory;
    private DynamicTimeSeriesCollection memoryData;
    private DynamicTimeSeriesCollection cpuData;
    private JFreeChart chartMemory;
    private JFreeChart chartCpu;
    private long[] oldTicks;


    public PerformancePanel() {
        initializeData();
        initComponents();
        startTimer();
    }

    private void initializeData() {
        SystemInfo systemInfo = SystemInfo.getInstance();
        disk = systemInfo.getDisk();
        cpu = systemInfo.getCpu();
        memory = systemInfo.getMemory();

        memoryData = createDynamicTimeSeriesCollection();
        chartMemory = createChart(memoryData);

        oldTicks = new long[CentralProcessor.TickType.values().length];
        cpuData =  createDynamicTimeSeriesCollection();
        chartCpu = createChart(cpuData);
    }

    private DynamicTimeSeriesCollection createDynamicTimeSeriesCollection() {
        DynamicTimeSeriesCollection data = new DynamicTimeSeriesCollection(1, 60, new Second());
        data.setTimeBase(new Second());
        data.addSeries(new float[]{0}, 0, "Usage");
        return data;
    }

    private void startTimer() {
        Timer timer = new Timer(1000, e -> {
            updateInfo();
        });
        timer.start();
    }

    private void updateInfo() {
        memory.updateRAMInfo();
        showMemoryInfo();

        cpuData.advanceTime();
        double cpuUsage = cpuData(cpu);
        cpuData.appendData(floatArrayPercent(cpuUsage));
        showCpuInfo(cpuUsage);

        disk.updateInfo();
        showDiskInfo();
    }

    private void showCpuInfo(double cpuUsage){
        lblCpuUsage.setText(String.format("%.2f%%", cpuUsage * 100));
    }

    private JFreeChart createChart(DynamicTimeSeriesCollection data) {
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "",
                "",
                "",
                data, false, false, false);

        XYPlot plot = (XYPlot) chart.getPlot();
        ValueAxis yAxis = plot.getRangeAxis();
        yAxis.setRange(0.0, 100.0);
        plot.setBackgroundPaint(null);
        chart.setBackgroundPaint(null);
        plot.setDomainGridlinePaint(Color.BLACK);
        plot.setRangeGridlinePaint(Color.BLACK);

        return chart;
    }

    private static float[] floatArrayPercent(long d) {
        float[] f = new float[1];
        f[0] = (float) d;
        return f;
    }

    private static float[] floatArrayPercent(double d) {
        float[] f = new float[1];
        f[0] = (float) (100d * d);
        return f;
    }


    private double cpuData(CPU proc) {
        double cpuUsage = proc.getSystemCpuLoadBetweenTicks(oldTicks);
        oldTicks = proc.getSystemCpuLoadTicks();
        return cpuUsage;
    }


    private void showMemoryInfo() {
        long total = memory.getTotal();
        long used = memory.getUsed();
        long free = total - used;

        lblMemoryUse.setText(FormatUtil.formatBytes(used));
        lblMemoryFree.setText(FormatUtil.formatBytes(free));
        memoryData.advanceTime();
        memoryData.appendData(floatArrayPercent(used * 100 / total));
    }

    private void showDiskInfo() {
        disk.updateInfo();

        long totalSpace = disk.getTotalSpace();
        long freeSpace = disk.getFreeSpace();
        long usedSpace = totalSpace - freeSpace;

        lblDiskUse.setText(FormatUtil.formatBytes(usedSpace));
        lblDiskFree.setText(FormatUtil.formatBytes(freeSpace));
        progressBarDisk.setValue((int) ((double) usedSpace / totalSpace * 100));
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - aaa
        panel8 = new JPanel();
        label4 = new JLabel();
        label1 = new JLabel();
        lblCpuUsage = new JLabel();
        chartPanelCpu = new ChartPanel(chartCpu);
        separator1 = new JSeparator();
        panel9 = new JPanel();
        label5 = new JLabel();
        label6 = new JLabel();
        lblMemoryUse = new JLabel();
        label7 = new JLabel();
        lblMemoryFree = new JLabel();
        chartPanelMemory = new ChartPanel(chartMemory);
        separator2 = new JSeparator();
        panel10 = new JPanel();
        label8 = new JLabel();
        label9 = new JLabel();
        lblDiskUse = new JLabel();
        label10 = new JLabel();
        lblDiskFree = new JLabel();
        progressBarDisk = new JProgressBar();

        //======== this ========
        setBorder ( new javax . swing. border .CompoundBorder ( new javax . swing. border .TitledBorder ( new javax . swing. border
        .EmptyBorder ( 0, 0 ,0 , 0) ,  "JF\u006frmD\u0065sig\u006eer \u0045val\u0075ati\u006fn" , javax. swing .border . TitledBorder. CENTER ,javax
        . swing. border .TitledBorder . BOTTOM, new java. awt .Font ( "Dia\u006cog", java .awt . Font. BOLD ,
        12 ) ,java . awt. Color .red ) , getBorder () ) );  addPropertyChangeListener( new java. beans
        .PropertyChangeListener ( ){ @Override public void propertyChange (java . beans. PropertyChangeEvent e) { if( "\u0062ord\u0065r" .equals ( e.
        getPropertyName () ) )throw new RuntimeException( ) ;} } );
        setLayout(new MigLayout(
            "fillx,hidemode 3",
            // columns
            "[fill]",
            // rows
            "[166]" +
            "[]" +
            "[152]" +
            "[30]" +
            "[]"));

        //======== panel8 ========
        {

            //---- label4 ----
            label4.setText("\u0426\u041f");
            label4.setFont(new Font("Inter", Font.BOLD, 20));

            //---- label1 ----
            label1.setText("\u0418\u0441\u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u043d\u0438\u0435");

            //---- lblCpuUsage ----
            lblCpuUsage.setText("10%");
            lblCpuUsage.setFont(new Font("sansserif", Font.BOLD, 18));

            GroupLayout panel8Layout = new GroupLayout(panel8);
            panel8.setLayout(panel8Layout);
            panel8Layout.setHorizontalGroup(
                panel8Layout.createParallelGroup()
                    .addGroup(panel8Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panel8Layout.createParallelGroup()
                            .addComponent(label4)
                            .addComponent(label1)
                            .addComponent(lblCpuUsage, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(chartPanelCpu, GroupLayout.PREFERRED_SIZE, 602, GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(59, Short.MAX_VALUE))
            );
            panel8Layout.setVerticalGroup(
                panel8Layout.createParallelGroup()
                    .addGroup(panel8Layout.createSequentialGroup()
                        .addComponent(label4)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(label1)
                        .addGap(8, 8, 8)
                        .addComponent(lblCpuUsage)
                        .addGap(0, 81, Short.MAX_VALUE))
                    .addGroup(panel8Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(chartPanelCpu, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
            );
        }
        add(panel8, "cell 0 0,growy");
        add(separator1, "cell 0 1");

        //======== panel9 ========
        {

            //---- label5 ----
            label5.setText("\u041f\u0430\u043c\u044f\u0442\u044c");
            label5.setFont(new Font("Inter", Font.BOLD, 20));

            //---- label6 ----
            label6.setText("\u0418\u0441\u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u043d\u043e");

            //---- lblMemoryUse ----
            lblMemoryUse.setText("342374");
            lblMemoryUse.setFont(new Font("Inter", Font.BOLD, 18));

            //---- label7 ----
            label7.setText("\u0421\u0432\u043e\u0431\u043e\u0434\u043d\u043e");

            //---- lblMemoryFree ----
            lblMemoryFree.setText("34343");
            lblMemoryFree.setFont(new Font("Inter", Font.BOLD, 18));

            GroupLayout panel9Layout = new GroupLayout(panel9);
            panel9.setLayout(panel9Layout);
            panel9Layout.setHorizontalGroup(
                panel9Layout.createParallelGroup()
                    .addGroup(panel9Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panel9Layout.createParallelGroup()
                            .addComponent(label5)
                            .addComponent(label6)
                            .addComponent(lblMemoryUse)
                            .addComponent(label7)
                            .addComponent(lblMemoryFree))
                        .addGap(18, 18, 18)
                        .addComponent(chartPanelMemory, GroupLayout.PREFERRED_SIZE, 612, GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(53, Short.MAX_VALUE))
            );
            panel9Layout.setVerticalGroup(
                panel9Layout.createParallelGroup()
                    .addGroup(panel9Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panel9Layout.createParallelGroup()
                            .addGroup(panel9Layout.createSequentialGroup()
                                .addGap(0, 7, Short.MAX_VALUE)
                                .addComponent(label5)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(label6)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblMemoryUse)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(label7)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblMemoryFree)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(chartPanelMemory, GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE))
                        .addContainerGap())
            );
        }
        add(panel9, "cell 0 2,growy");
        add(separator2, "cell 0 3");

        //======== panel10 ========
        {

            //---- label8 ----
            label8.setText("\u0414\u0438\u0441\u043a");
            label8.setFont(new Font("Inter", Font.BOLD, 20));

            //---- label9 ----
            label9.setText("\u0418\u0441\u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u043d\u043e");

            //---- lblDiskUse ----
            lblDiskUse.setText("342374");
            lblDiskUse.setFont(new Font("Inter", Font.BOLD, 18));

            //---- label10 ----
            label10.setText("\u0421\u0432\u043e\u0431\u043e\u0434\u043d\u043e");

            //---- lblDiskFree ----
            lblDiskFree.setText("34343");
            lblDiskFree.setFont(new Font("Inter", Font.BOLD, 18));

            //---- progressBarDisk ----
            progressBarDisk.setValue(60);

            GroupLayout panel10Layout = new GroupLayout(panel10);
            panel10.setLayout(panel10Layout);
            panel10Layout.setHorizontalGroup(
                panel10Layout.createParallelGroup()
                    .addGroup(panel10Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panel10Layout.createParallelGroup()
                            .addComponent(lblDiskFree)
                            .addComponent(label8)
                            .addGroup(panel10Layout.createSequentialGroup()
                                .addGroup(panel10Layout.createParallelGroup()
                                    .addComponent(label10)
                                    .addComponent(label9)
                                    .addComponent(lblDiskUse))
                                .addGap(18, 18, 18)
                                .addComponent(progressBarDisk, GroupLayout.PREFERRED_SIZE, 606, GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(59, Short.MAX_VALUE))
            );
            panel10Layout.setVerticalGroup(
                panel10Layout.createParallelGroup()
                    .addGroup(panel10Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(label8)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel10Layout.createParallelGroup()
                            .addGroup(panel10Layout.createSequentialGroup()
                                .addComponent(label9)
                                .addGap(6, 6, 6)
                                .addComponent(lblDiskUse)
                                .addGap(12, 12, 12)
                                .addComponent(label10))
                            .addComponent(progressBarDisk, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblDiskFree)
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );
        }
        add(panel10, "cell 0 4,growy");
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - aaa
    private JPanel panel8;
    private JLabel label4;
    private JLabel label1;
    private JLabel lblCpuUsage;
    private ChartPanel chartPanelCpu;
    private JSeparator separator1;
    private JPanel panel9;
    private JLabel label5;
    private JLabel label6;
    private JLabel lblMemoryUse;
    private JLabel label7;
    private JLabel lblMemoryFree;
    private ChartPanel chartPanelMemory;
    private JSeparator separator2;
    private JPanel panel10;
    private JLabel label8;
    private JLabel label9;
    private JLabel lblDiskUse;
    private JLabel label10;
    private JLabel lblDiskFree;
    private JProgressBar progressBarDisk;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
