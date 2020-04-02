package MOPE.Lab4;


import javax.swing.*;
import java.awt.*;

public class GUI {
    //to build experiment plan table
    int m;
    public String[][] experementTable;

    public GUI(int m, String[][] table) {
        this.m = m;
        this.experementTable = table;
    }

    public void start(){
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame.setDefaultLookAndFeelDecorated(true);
                createGUI(experementTable, m);
            }
        });
    }

    public static void createGUI(String[][] experementTable, int m) {
        JFrame frame = new JFrame("Full-factor experiment");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        String[] columnNames = new String[experementTable[0].length];
        String[] faktorColumnNames = new String[]{"X0", "X1", "X2", "X3", "X12", "X13", "X23", "X123"};
        System.arraycopy(faktorColumnNames, 0, columnNames, 0, 4);
        if (experementTable[0].length - m == 8){
            System.arraycopy(faktorColumnNames, 4, columnNames, 4, faktorColumnNames.length - 4);
        }

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
