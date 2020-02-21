package MOPE.Lab1;

import java.util.Random;

public class Modeling {
    int a0;
    int a1;
    int a2;
    int a3;

    int[] x1 = new int[8];
    int[] x2 = new int[8];
    int[] x3 = new int[8];
    int[] y = new int[8];

    double x01;
    double x02;
    double x03;

    double dx1;
    double dx2;
    double dx3;

    double[] xn1 = new double[8];
    double[] xn2 = new double[8];
    double[] xn3 = new double[8];

    double yBencchmark;

    public Modeling(int a0, int a1, int a2, int a3, int range) {
        this.a0 = a0;
        this.a1 = a1;
        this.a2 = a2;
        this.a3 = a3;
        generate(range);
        calculate();
    }

    private void generate(int range) {
        Random random = new Random();
        for (int i = 0; i < 8; i++) {
            x1[i] = random.nextInt(range);
            x2[i] = random.nextInt(range);
            x3[i] = random.nextInt(range);
            y[i] = a0 + a1*x1[i] + a2*x2[i] + a3*x3[i];
        }
    }

    private void calculate(){
        x01 = (float)(findMax(x1)+ findMin(x1))/2;
        x02 = (float)(findMax(x2)+ findMin(x2))/2;
        x03 = (float)(findMax(x3)+ findMin(x3))/2;

        dx1 = x01 - findMin(x1);
        dx2 = x02 - findMin(x2);
        dx3 = x03 - findMin(x3);

        for (int i = 0; i < 8; i++) {
            xn1[i] = (x1[i] - x01)/dx1;
            xn2[i] = (x2[i] - x02)/dx2;
            xn3[i] = (x3[i] - x03)/dx3;
        }

        yBencchmark = a0 + a1*x01 + a2*x02 + a3*x03;
    }

    private int findMax(int[] mass){
        int result = mass[0];
        for (int elem:mass
             ) {
            result = Math.max(elem, result);
        }
        return result;
    }

    private int findMin(int[] mass){
        int result = mass[0];
        for (int elem:mass
        ) {
            result = Math.min(elem, result);
        }
        return result;
    }

    public int findIndexOfMin(int[] mass){
        int index = 0;
        for (int i = 0; i < mass.length; i++) {
            index = (mass[i] < mass[index])? i : index;
        }
        return index;
    }

    public String[][] combineAll(){
        String[][] data = new String[10][8];
        String[] firstCol = {"1", "2", "3", "4", "5", "6", "7", "8", "X0", "dx"};

        for (int i = 0; i < 8; i++) {
            data[i][0] = firstCol[i];
            data[i][1] = String.valueOf(x1[i]);
            data[i][2] = String.valueOf(x2[i]);
            data[i][3] = String.valueOf(x3[i]);
            data[i][4] = String.valueOf(y[i]);
            data[i][5] = String.valueOf(xn1[i]);
            data[i][6] = String.valueOf(xn2[i]);
            data[i][7] = String.valueOf(xn3[i]);
        }
        data[8][0] = firstCol[8];
        data[8][1] = String.valueOf(x01);
        data[8][2] = String.valueOf(x02);
        data[8][3] = String.valueOf(x03);
        data[9][0] = firstCol[9];
        data[9][1] = String.valueOf(dx1);
        data[9][2] = String.valueOf(dx2);
        data[9][3] = String.valueOf(dx3);

        return data;
    }

    public int[] getX1() {
        return x1;
    }

    public int[] getX2() {
        return x2;
    }

    public int[] getX3() {
        return x3;
    }

    public int[] getY() {
        return y;
    }

    public double getX01() {
        return x01;
    }

    public double getX02() {
        return x02;
    }

    public double getX03() {
        return x03;
    }

    public double getDx1() {
        return dx1;
    }

    public double getDx2() {
        return dx2;
    }

    public double getDx3() {
        return dx3;
    }

    public double[] getXn1() {
        return xn1;
    }

    public double[] getXn2() {
        return xn2;
    }

    public double[] getXn3() {
        return xn3;
    }

    public double getyBencchmark() {
        return yBencchmark;
    }


}
