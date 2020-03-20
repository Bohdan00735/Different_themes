package MOPE.Lab2;

import java.util.Arrays;
import java.util.Random;

public class FullFactorExperiment {
    public int m;
    int x1Max, x1Min, x2Max, x2Min;
    public double yMin, yMax;
    int[][] y;
    public int x11 = -1;
    public int x21 = -1;
    public int x22 = -1;
    public int x13 = -1;
    public int x12 = 1;
    public int x23 = 1;
    double[] mediumValuesOfy;
    double b0, b1, b2, a0, a1, a2;

    public void setM(int m) {
        this.m = m;
    }

    public void setyMin(double yMin) {
        this.yMin = yMin;
    }

    public void setyMax(double yMax) {
        this.yMax = yMax;
    }

    public String[][] getY() {
        String[][] newY = new String[y.length][y[0].length+2];
        for (int i = 0; i < y.length; i++) {
            for (int j = 0; j < y[0].length; j++) {
                newY[i][j+2] = String.valueOf(y[i][j]);
            }
        }

        newY[0][0] = String.valueOf(x11);
        newY[0][1] = String.valueOf(x21);
        newY[1][0] = String.valueOf(x12);
        newY[1][1] = String.valueOf(x22);
        newY[2][0] = String.valueOf(x13);
        newY[2][1] = String.valueOf(x23);
        return newY;
    }

    void setAll(int m, int x1Max, int x1Min, int x2Max, int x2Min, int yMin, int yMax){
        this.m = m;
        this.x1Max = x1Max;
        this.x1Min = x1Min;
        this.x2Max = x2Max;
        this.x2Min = x2Min;
        this.yMin = yMin;
        this.yMax = yMax;
        y = generateInDiap(yMin, yMax, m,3);
    }

    public void mainProcess(){
        while (!verifyHomogeneity()){
            m+=1;
            y = generateInDiap((int)yMin, (int)yMax, m,3);
        }
        System.out.println("Average values of y: \n" + Arrays.toString(mediumValuesOfy));

        calculateNormCoefficient();
        System.out.printf("Normalized regression equation: \n " +
                "y = %.2f + %.2f*x1 + %.2f*x2 \n",b0,b1,b2);

        System.out.printf("let's do the check: \n " +
                "%.2f == %.2f \n %.2f == %.2f \n %.2f == %.2f \n",
                (b0 +b1*x11 + b2*x21), mediumValuesOfy[0],
                (b0 +b1*x12 + b2*x22), mediumValuesOfy[1],
                (b0 +b1*x13 + b2*x23), mediumValuesOfy[2]);

        makeNaturalizationOfCoefficients();
        System.out.printf("Let`s write the naturalized regression equation: \n" +
                "y = %.2f + %.2f*x1 + %.2f*x2 \n",a0,a1,a2);

        System.out.printf("let's do the check: \n " +
                        "%.2f == %.2f \n %.2f == %.2f \n %.2f == %.2f \n",
                (a0 +a1*x1Min + a2*x2Min), mediumValuesOfy[0],
                (a0 +a1*x1Max + a2*x2Min), mediumValuesOfy[1],
                (a0 +a1*x1Min + a2*x2Max), mediumValuesOfy[2]);
    }

    public int[][] generateInDiap(int min, int max, int m, int raws) {
        int[][] result = new int[raws][m];
        Random random = new Random();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j <raws ; j++) {
                int var = max-min;
                result[j][i] = (int)(Math.random()*++(var)+min);
            }
        }
        return result;
    }


    private boolean verifyHomogeneity(){
        mediumValuesOfy = calculateMediums(y);
        double dispersion1 = calculateDispersion(mediumValuesOfy[0], y[0]);
        double dispersion2 = calculateDispersion(mediumValuesOfy[1], y[1]);
        double dispersion3 = calculateDispersion(mediumValuesOfy[2], y[2]);

        double majorDeviation = Math.sqrt((2*(2*m - 2)/(double)(m*(m - 4))));

        double fuv1 = dispersion1/dispersion2;
        double fuv2 = dispersion3/dispersion1;
        double fuv3 = dispersion3/dispersion2;

        double tetaUV1 = (m - 2/(double)m)*fuv1;
        double tetaUV2 = (m - 2/(double)m)*fuv2;
        double tetaUV3 = (m - 2/(double)m)*fuv3;

        double rUV1 = Math.abs(tetaUV1 - 1)/majorDeviation;
        double rUV2 = Math.abs(tetaUV2 - 1)/majorDeviation;
        double rUV3 = Math.abs(tetaUV3 - 1)/majorDeviation;

        if (m < 6){
            if (rUV1 <= 1.73 &&  rUV2 <= 1.73 && rUV3 <= 1.73){
                System.out.println("The dispersion is homogeneous, m = " + m);
                return true;
            }else return false;
        }else {
            if (rUV1 <= 2.16 &&  rUV2 <= 2.16 && rUV3 <= 2.16){
                System.out.println("The dispersion is homogeneous, m = " + m);
                return true;
            }else return false;
        }
    }

    public double[] calculateMediums(int[][] mass){
        double[] result = new double[mass.length];
        for (int i = 0; i < mass.length; i++) {
            result[i] += calculateSum(mass[i])/mass[0].length;
        }
        return result;
    }

    public double calculateSum(int [] mass){
        double result = 0;
        for (double i:mass
             ) {
            result+=i;
        }return result;
    }

    public double calculateDispersion(double medium, int[] mass){
        double result = 0;
        for (int i :
                mass) {
            result += Math.pow((i - medium), 2);
        }
        return result/mass.length;
    }

    public void calculateNormCoefficient(){
        double mx1 = (x11 + x12 + x13)/3.0;
        double mx2 = (x21 + x22 + x23)/3.0;
        double my = (mediumValuesOfy[0] + mediumValuesOfy[1] + mediumValuesOfy[2])/3.0;
        //double a1 = 1.0;
        double a2 = (x11*x21 + x12*x22 + x13*x23)/3.0;
        //double a3 = 1.0;
        double a11 = (x11*mediumValuesOfy[0] + x12*mediumValuesOfy[1] + x13*mediumValuesOfy[2])/3.0;
        double a22 = (x21*mediumValuesOfy[0] + x22*mediumValuesOfy[1] + x23*mediumValuesOfy[2])/3.0;
        double mainDeterminant = 1 + 2*mx1*a2*mx2 - mx2*mx2 - a2*a2 - mx1*mx1;
        b0 = (my + a11*a2*mx2 + mx1*a2*a22 - a22*mx2 - a11*mx1 - a2*a2*my)/ mainDeterminant;
        b1 = (a11 + mx1*a22*mx2 + my*a2*mx2 - mx2*a11*mx2 - mx1*my - a22*a2)/ mainDeterminant;
        b2 = (a22 + mx1*a2*my + mx1*a11*mx2 - my*mx2 - mx1*a22*mx1 - a2*a11)/ mainDeterminant;
    }

    private void makeNaturalizationOfCoefficients(){
        double deltaX1 = Math.abs(x1Max - x1Min)/2.0;
        double deltaX2 = Math.abs(x2Max - x2Min)/2.0;
        double x10 = (x1Max + x1Min)/2.0;
        double x20 = (x2Max + x2Min)/2.0;

        a0 = b0 - b1*x10/deltaX1 - b2*x20/deltaX2;
        a1 = b1/deltaX1;
        a2 = b2/deltaX2;
    }
}
