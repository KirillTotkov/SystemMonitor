package org.ssustudents.views;

import org.ssustudents.models.ProcessList;
import oshi.util.FormatUtil;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.List;

public class ProcessListTableModel extends AbstractTableModel {
    private final List<ProcessList.ProcessInfo> processes;
    private final String[] columnNames = {"Имя", "ЦП", "Память"};

    public ProcessListTableModel(List<ProcessList.ProcessInfo> processes) {
        this.processes = processes;
    }

    @Override
    public int getRowCount() {
        return processes.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ProcessList.ProcessInfo processInfo = processes.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return processInfo.getName();
            case 1:
                return processInfo.getCpuLoadPercent();
            case 2:
                return processInfo.getRamUsed();
            case 3:
                return processInfo.getId();
            default:
                return null;
        }
    }

    public void setProcesses(List<ProcessList.ProcessInfo> processes) {
        this.processes.clear();
        this.processes.addAll(processes);
        fireTableDataChanged();
    }

    public void setCellRenderers(JTable table) {
        table.getColumnModel().getColumn(1).setCellRenderer(new CellRenderer());
        table.getColumnModel().getColumn(2).setCellRenderer(new CellRenderer());
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return String.class;
            case 1:
                return Double.class;
            case 2:
                return Long.class;
            default:
                return null;
        }
    }

    class CellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (value instanceof Double) {
                value = String.format("%.1f%%", (Double) value);
            } else if (value instanceof Long) {
                value = FormatUtil.formatBytes((Long) value);
            }
            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
    }
}

