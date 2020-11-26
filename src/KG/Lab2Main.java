package KG;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

public class Lab2Main extends JFrame{
    private JPanel panel;


    public Lab2Main(){
        setSize(400, 200);
        init();
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Lab 2");
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void init(){
        panel = new MyPaintingPanel();
        this.add(panel);

        this.validate(); // because you added panel after setVisible was called
        this.repaint();
    }
}


class MyPaintingPanel extends JPanel{

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawLine(100,200,300,400);
    }

}