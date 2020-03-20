package MOPE.Lab3;


import javax.swing.*;
import java.awt.*;

public class Main {
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
        FractionalThreefactorExperiment threefactorExperiment = new FractionalThreefactorExperiment(5,
                20,70,25,65,25,35);
        threefactorExperiment.calculateNormCoefficient();
        threefactorExperiment.makeAudit();
        //if you will change m, please add or take away some var here
        String[] columnNames = {"X0","X1", "X2","X3", "Y1", "Y2" , "Y3", "Y4", "Y5"};

        JTable table = new JTable(threefactorExperiment.makeTable(), columnNames);

        JScrollPane scrollPane = new JScrollPane(table);

        frame.getContentPane().add(scrollPane);
        frame.setPreferredSize(new Dimension(450, 250));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
