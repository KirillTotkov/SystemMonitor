/*
 * Created by JFormDesigner on Fri Nov 24 21:40:03 MSK 2023
 */

package org.ssustudents.views;

import java.awt.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.*;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import net.miginfocom.swing.*;
import org.ssustudents.models.ProcessList;

import javax.swing.*;
import javax.swing.GroupLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author kirill
 */
public class ProcessListPanel extends JPanel {
    private ProcessList processList = new ProcessList();
    private ProcessListTableModel model;
    private TableRowSorter<TableModel> sorter;
    private final int REFRESH_RATE = 1000;
    private Timer timer;
    public ProcessListPanel() {
        initComponents();
        txtSearch.putClientProperty("JTextField.placeholderText", "Поиск");
        setupData();
    }

    @Override
    public void setVisible(boolean aFlag) {
        super.setVisible(aFlag);
        if (timer == null) return;
        if (aFlag) {
            timer.start();
        } else {
            timer.stop();
        }
    }

    public void setupData(){
        setupSearch();
        setupTable();
        setupPopupMenuMenu();
        updateTable();
        timer = new Timer(REFRESH_RATE, e -> {
            updateTable();
        });
    }

    private void setupTable(){
        model = new ProcessListTableModel(processList.getProcesses());
        sorter = new TableRowSorter<>(model);
        tableProcess.setModel(model);
        model.setCellRenderers(tableProcess);
    }

    private void setupSearch() {
        txtSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
               filterByName();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filterByName();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filterByName();
            }

        });
    }
    private void filterByName() {
        String text = txtSearch.getText().toLowerCase().trim();
        List<ProcessList.ProcessInfo> processes = processList.getProcesses();
        List<ProcessList.ProcessInfo> filteredProcesses = processes.stream()
                .filter(processInfo -> processInfo.getName().toLowerCase().contains(text))
                .collect(Collectors.toList());
        model.setProcesses(filteredProcesses);
        tableProcess.setModel(model);
    }

    private void setupSorter(){
        List<RowSorter.SortKey> sortKeys = new ArrayList<>( tableProcess.getRowSorter().getSortKeys());
        sorter.setModel(model);
        sorter.setSortKeys(sortKeys);
        sorter.sort();
    }

    private void killSelectedProcess() {
        int selectedRow = tableProcess.getSelectedRow();
        if (selectedRow != -1) {
            int modelRow = tableProcess.convertRowIndexToModel(selectedRow);
            String name = (String) tableProcess.getModel().getValueAt(modelRow, 0);
            int dialogResult = JOptionPane.showConfirmDialog(null,
                    "Вы уверены, что хотите завершить процесс " + name + "?",
                    "Warning", JOptionPane.YES_NO_OPTION);

            if (dialogResult == JOptionPane.YES_OPTION) {
                int pid = (int) tableProcess.getModel().getValueAt(modelRow, 3);
                processList.killProcess(pid);
            }
        }
    }

    public void updateTable() {
        int selectedRow = tableProcess.getSelectedRow();
        processList.update();
        model.setProcesses(processList.getProcesses());
        tableProcess.setModel(model);
        if (!txtSearch.getText().trim().isEmpty()) {
            filterByName();
        }
        setupSorter();

        model.fireTableDataChanged();
        if (selectedRow != -1 && selectedRow < tableProcess.getRowCount()) {
            tableProcess.setRowSelectionInterval(selectedRow, selectedRow);
        }

    }

    private void setupPopupMenuMenu() {
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem killProcessItem = new JMenuItem("Завершить процесс");
        killProcessItem.setIcon(new FlatSVGIcon("icons/close.svg", 14, 14));
        popupMenu.add(killProcessItem);
        tableProcess.setComponentPopupMenu(popupMenu);
        killProcessItem.addActionListener(e -> killSelectedProcess());
        tableProcess.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                showPopup(e, popupMenu);
            }
        });
    }

    private void showPopup(MouseEvent e, JPopupMenu popupMenu) {
        if (e.isPopupTrigger()) {
            int row = tableProcess.rowAtPoint(e.getPoint());
            tableProcess.setRowSelectionInterval(row, row);
            popupMenu.show(e.getComponent(), e.getX(), e.getY());
        }
    }


    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - aaa
        scrollPane1 = new JScrollPane();
        tableProcess = new JTable();
        txtSearch = new JTextField();

        //======== this ========
        setBorder ( new javax . swing. border .CompoundBorder ( new javax . swing. border .TitledBorder ( new javax . swing. border .
        EmptyBorder ( 0, 0 ,0 , 0) ,  "JF\u006frm\u0044es\u0069gn\u0065r \u0045va\u006cua\u0074io\u006e" , javax. swing .border . TitledBorder. CENTER ,javax . swing
        . border .TitledBorder . BOTTOM, new java. awt .Font ( "D\u0069al\u006fg", java .awt . Font. BOLD ,12 ) ,
        java . awt. Color .red ) , getBorder () ) );  addPropertyChangeListener( new java. beans .PropertyChangeListener ( )
        { @Override public void propertyChange (java . beans. PropertyChangeEvent e) { if( "\u0062or\u0064er" .equals ( e. getPropertyName () ) )
        throw new RuntimeException( ) ;} } );

        //======== scrollPane1 ========
        {

            //---- tableProcess ----
            tableProcess.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            tableProcess.setModel(new DefaultTableModel(
                new Object[][] {
                    {null, null, null},
                    {null, null, null},
                },
                new String[] {
                    "\u0418\u043c\u044f", "\u0426\u041f", "\u041f\u0430\u043c\u044f\u0442\u044c"
                }
            ) {
                boolean[] columnEditable = new boolean[] {
                    false, false, false
                };
                @Override
                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return columnEditable[columnIndex];
                }
            });
            tableProcess.setAutoCreateRowSorter(true);
            scrollPane1.setViewportView(tableProcess);
        }

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(txtSearch, GroupLayout.PREFERRED_SIZE, 223, GroupLayout.PREFERRED_SIZE))
                .addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 910, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup()
                .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(txtSearch, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 490, GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(73, Short.MAX_VALUE))
        );
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - aaa
    private JScrollPane scrollPane1;
    private JTable tableProcess;
    private JTextField txtSearch;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
