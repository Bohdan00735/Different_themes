package MOPE.Lab4;


import javax.swing.*;
import java.awt.*;

public class GUI {
    //to build experiment plan table
    int m;
    public String[][] experementTable;
    String[] columnNames;

    public GUI(int m, String[][] table) {
        this.m = m;
        this.experementTable = table;
        columnNames = new String[experementTable[0].length];
    }

    public void start(){
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame.setDefaultLookAndFeelDecorated(true);
                createGUI(experementTable, m, columnNames);
            }
        });
    }

    public void formFactorColumnNames(String[] names){
        System.arraycopy(names, 0, columnNames, 0, names.length);
    }

    public static void createGUI(String[][] experementTable, int m, String[] columnNames) {
        JFrame frame = new JFrame("Full-factor experiment");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        for (int i = columnNames.length-m; i < columnNames.length; i++) {
            columnNames[i] = "Y"+(i-columnNames.length+m+1);
        }

        JTable table = new JTable(experementTable, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);

        frame.getContentPane().add(scrollPane);
        frame.setPreferredSize(new Dimension(450, 250));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
