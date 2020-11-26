package MOPE.Lab2;

import javax.swing.*;
import java.awt.*;

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
        JFrame frame = new JFrame("Full-factor experiment");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        FullFactorExperiment experiment = new FullFactorExperiment();
        experiment.setAll(2,70,20,65,25,30,130);
        experiment.mainProcess();
        String[] columnNames = {"X1", "X2", "Y1", "Y2" , "Y3", "Y4"};

        JTable table = new JTable(experiment.getY(), columnNames);

        JScrollPane scrollPane = new JScrollPane(table);

        frame.getContentPane().add(scrollPane);
        frame.setPreferredSize(new Dimension(450, 250));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}




