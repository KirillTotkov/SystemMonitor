/*
 * Created by JFormDesigner on Fri Nov 24 21:36:16 MSK 2023
 */

package org.ssustudents.views;

import javax.swing.event.*;
import com.formdev.flatlaf.extras.FlatSVGUtils;

import javax.swing.*;
import javax.swing.GroupLayout;

/**
 * @author kirill
 */
public class MainFrame extends JFrame {
    public MainFrame() {
        initComponents();
        setIconImages(FlatSVGUtils.createWindowIconImages("/icons/app.svg"));

        addToTabbedPane("Обзор", new SystemInfoPanel());
        addToTabbedPane("Процессы",  new ProcessListPanel());
        addToTabbedPane("Производительность", new PerformancePanel());

    }

    public void addToTabbedPane(String title, JPanel panel){
        tabbedPane1.add(title, panel);
    }

    private void tabbedPaneStateChanged(ChangeEvent e) {
        // TODO add your code here
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - aaa
        tabbedPane1 = new JTabbedPane();

        //======== this ========
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        var contentPane = getContentPane();

        //======== tabbedPane1 ========
        {
            tabbedPane1.addChangeListener(e -> tabbedPaneStateChanged(e));
        }

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addComponent(tabbedPane1, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 860, Short.MAX_VALUE)
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
                    .addComponent(tabbedPane1, GroupLayout.DEFAULT_SIZE, 573, Short.MAX_VALUE)
                    .addContainerGap())
        );
        pack();
        setLocationRelativeTo(null);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - aaa
    private JTabbedPane tabbedPane1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
