package KG;

import javax.swing.*;
import java.awt.*;

public class Test extends JFrame
{
    public void paint(Graphics g)
    {
        super.paint(g);
        g.drawLine(0, 0, 100, 100);
    }

    public static void main(String[] args) {new Test();}

    public Test()
    {
        super("Test");
        JFrame.setDefaultLookAndFeelDecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setSize(800, 600);
        setVisible(true);
    }
}
