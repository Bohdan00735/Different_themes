package MOPE.Lab1;

import javafx.application.Application;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.control.TableView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Main  extends JFrame {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame.setDefaultLookAndFeelDecorated(true);
                createGUI();
            }
        });
    }

    public static void createGUI() {
        JFrame frame = new JFrame("Test frame");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Modeling modeling = new Modeling(1,2,3,4,20);
        String[] columnNames = {"№", "X1", "X2", "X3", "Y" , "XN1", "XN2", "XN3"};
        JTable table = new JTable(modeling.combineAll(), columnNames);
        int minIndex = modeling.findIndexOfMin(modeling.getY());
        table.setRowSelectionInterval(minIndex,minIndex);

        JScrollPane scrollPane = new JScrollPane(table);

        frame.getContentPane().add(scrollPane);
        frame.setPreferredSize(new Dimension(450, 200));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}



