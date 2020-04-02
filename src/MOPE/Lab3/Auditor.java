package MOPE.Lab3;
import org.apache.commons.math3.util.Precision;

import java.util.ArrayList;
import java.util.HashMap;

import static java.lang.StrictMath.sqrt;

public class Auditor {
    double[][] KohrenTable = new double[][]{{0.9065,0.7679,0.6841,0.6287,0.5892,0.5598,0.5365,0.5175, 0.5017, 0.4884,0.25},//f2=4
                                            {0.6798, 0.5157,0.4377,3910,3595,3362,3185,3043,0.2926,0.2829,0.1250}};//f2 = 8
    // there`re data for f2 = 4 or 8 and last num in each row for inf, q = 0.05

    double[][] FisherTable = new double[][]{
            {0,1,2,3,4,5,6,12,24,1000},//100 - inf, 0 - column for f3
            {1,164.4,199.5,215.7,224.6,230.2,234.0,244.9,249.0,254.3},
            {2,18.5,19.2,19.2,19.3,19.3,19.3,19.4,19.4,19.5},
            {3,10.1,9.6,9.3,9.1,9.0,8.9,8.7,8.6,8.5},
            {4,7.7,6.9,6.6,6.4,6.3,6.2,5.9,5.8,5.6},
            {5,6.6,5.8,5.4,5.2,5.1,5.0,4.7,4.5,4.4},
            {6,6.0,5.1,4.8,4.5,4.4,4.3,4.0,3.8,3.7},
            {7,5.5,4.7,4.4,4.1,4.0,3.9,3.6,3.4,3.2},
            {8,5.3,4.5,4.1,3.8,3.7,3.6,3.3,3.1,2.9},
            {9,5.1,4.3,3.9,3.6,3.5,3.4,3.1,2.9,2.7},
            {10,5.0,4.1,3.7,3.5,3.3,3.2,2.9,2.7,2.5},
            {11,4.8,4.0,3.6,3.4,3.2,3.1,2.8,2.6,2.4},
            {12,4.8,3.9,3.5,3.3,3.1,3.0,2.7,2.5,2.3},
            {13,4.7,3.8,3.4,3.2,3.0,2.9,2.6,2.4,2.2},
            {14,4.6,3.7,3.3,3.1,3.0,2.9,2.5,2.3,2.1},
            {15,4.5,3.7,3.3,3.1,2.9,2.8,2.5,2.3,2.1},
            {16,4.5,3.6,3.2,3.0,2.9,2.7,2.4,2.2,2.0},
            {17,4.5,3.6,3.2,3.0,2.8,2.7,2.4,2.2,2.0},
            {18,4.4,3.6,3.2,2.9,2.8,2.7,2.3,2.1,1.9},
            {19,4.4,3.5,3.1,2.9,2.7,2.6,2.3,2.1,1.9},
            {20,4.4,3.5,3.1,2.9,2.7,2.6,2.3,2.1,1.9},
            {22,4.3,3.4,3.1,2.8,2.7,2.6,2.2,2.0,1.8},
            {24,4.3,3.4,3.0,2.8,2.6,2.5,2.2,2.0,1.7},
            {26,4.2,3.4,3.0,2.7,2.6,2.5,2.2,2.0,1.7},
            {28,4.2,3.3,3.0,2.7,2.6,2.4,2.1,1.9,1.7},
            {30,4.2,3.3,2.9,2.7,2.5,2.4,2.1,1.9,1.6},
            {40,4.1,3.2,2.9,2.6,2.5,2.3,2.0,1.8,1.5},
            {60,4.0,3.2,2.8,2.5,2.4,2.3,1.9,1.7,1.4},
            {120,3.9,3.1,2.7,2.5,2.3,2.2,1.8,1.6,1.3},
            {1000,3.8,3.0,2.6,2.4,2.2,2.1,1.8,1.5,1.0}};


    int f1,f2,f3,f4;
    int d;
    double[] studentResults;
    double s2B;

    HashMap <Integer, Double> Student = new HashMap<>();
    MatrixCalculator matrixCalculator = new MatrixCalculator();

    public Auditor() {
        //fill Student`s factor N = 4 or 8 - const, so we can add only raws that multiple to N
        Student.put(8,2.306);
        Student.put(12,2.179);
        Student.put(16, 2.120);
        Student.put(20,2.084);
        Student.put(24, 2.064);
        Student.put(28, 2.048);
    }

    public void checkRegressionEquation(double[] bCoefficient, int[][] factors, double[] mediumY){
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < mediumY.length; i++) {
            double intermediateResult = 0;
            for (int j = 0; j < bCoefficient.length; j++) {
                if (j == 0){
                    intermediateResult += bCoefficient[j];
                    output.append(Precision.round(bCoefficient[j],2));
                }else {
                    intermediateResult += bCoefficient[j]*factors[i][j-1];
                    output.append(" + ").append(Precision.round(bCoefficient[j],2)).append("*").
                            append(Precision.round(factors[i][j-1],2));
                }
            }
            output.append(" = ").append(Precision.round(intermediateResult,2)).append(" = ").
                    append(Precision.round(mediumY[i],2)).append("\n");
        }
        System.out.println(output);
    }

    public boolean checkKohren(double[] dispersions, int f1, int f2){
        this.f1 = f1;
        this.f2 = f2;
        double gp = findMax(dispersions)/calculateSum(dispersions);
        int raw = 0;
        if (f2 == 8) {raw = 1;}// we get only 4 or 8 in this case, f2 = N
        try {
            return gp < KohrenTable[raw][f1];
        }catch (IndexOutOfBoundsException e){
            return gp < KohrenTable[raw][KohrenTable.length-1];//compare with value for inf
        }
    }

    public void checkStudent(double[] dispersions, int m,double[] yMediums, int[][] normalisedCoefficients,
                                double[] bCoefficients, int[][] naturalisedFactors){
        s2B = calculateSum(dispersions)/dispersions.length;
        double sFromB = sqrt(s2B/(dispersions.length*m));
        double[] betaValues = new double[dispersions.length];
        double[] thetaValues = new double[betaValues.length];
        betaValues[0] = calculateSum(yMediums)/yMediums.length;// first column fill by 1
        thetaValues[0] = betaValues[0]/(sFromB);

        for (int i = 1; i < betaValues.length; i++) {
            betaValues[i] = matrixCalculator.calculateSumTwoArrayForEach
                    (matrixCalculator.getColumn(i-1,normalisedCoefficients),yMediums)/yMediums.length;
            thetaValues[i] = betaValues[i]/sFromB;
        }

        f3 = f1*f2;
        double tetaTable = (Student.get(f3) != null)? Student.get(f3) : 1.960;//value for infinity
        identifySignificantOnes(tetaTable,thetaValues,bCoefficients,naturalisedFactors);
    }

    void identifySignificantOnes(double tTable, double[] tetas, double[] bCoefficients, int[][] naturalisedFactors){
        ArrayList<Integer> significant = new ArrayList<>();
        // check what coefficients is significant add sort them, after that calculate and only with  significant
        for (int i = 0; i < tetas.length; i++) {
            if (tetas[i] < tTable){
                System.out.printf("b%d isn`t significant because t%d < %.2f\n",i+1,i+1,tTable);
            }else {significant.add(i);}
        }

        StringBuilder stringBuilder = new StringBuilder();
        studentResults = new double[f2];//f2 = N
        int i = 0;
        for (int[] naturalisedFactor : naturalisedFactors) {
            double intermediateResult = 0;
            for (int elem : significant
            ) {
                if (elem == 0) {
                    stringBuilder.append(Precision.round(bCoefficients[0],1)).append("+");
                    intermediateResult += bCoefficients[0];
                } else {
                    stringBuilder.append(Precision.round(bCoefficients[elem], 2)).
                            append("*").append(Precision.round(naturalisedFactor[elem - 1],2)).append("+");
                    intermediateResult += bCoefficients[elem]*naturalisedFactor[elem - 1];
                }
            }

            stringBuilder.append("=").append(Precision.round(intermediateResult,1)).append("\n");
            studentResults[i] = intermediateResult;
            i++;
        }
        System.out.println("Student equation");
        System.out.println(stringBuilder);
        d = significant.size();
    }

    public boolean checkFisher(int m, double[] yMediums){
        f4 = f2-d;// N = f2
        double sAD = ((double) m/(double) (f4)*matrixCalculator.sumOfDivisionInSquare(studentResults,yMediums));
        float fp = (float) (sAD/s2B);
        int raw = findNextValueInColumnFisher(f3,0);
        int column = findNextValueInRowFisher(f4,0);
        raw = (raw == -1)? FisherTable.length-1:raw;// choose raw for inf
        column = (column == -1)? FisherTable[0].length-1:column;// choose column for inf
        double ft = FisherTable[raw][column];
        if (fp < ft){
            System.out.println("the equation is adequate by Fisher with level of significance == 0,5");
            return true;
        }else {return false;}
    }


    public int findNextValueInColumnFisher(int value, int column){
        //return -1 if value bigger than each from column
        //In another case return num of raw with this value or with value next after that
        for (int i = 0; i <FisherTable.length; i++) {
            if(FisherTable[i][column] >= value){return i;}
        }
        return -1;
    }

    public int findNextValueInRowFisher(int value, int raw) throws NullPointerException{
        //return -1 if value bigger than each from row
        //In another case return num of column with this value or with value next after that
        for (int i = 0; i < FisherTable[raw].length; i++) {
            if(FisherTable[raw][i] >= value){return i;}
        }
        return -1;
    }


    double findMax(double[] array){
        double result = array[0];
        for (double elem:array
             ) {
            result = Math.max(elem, result);
        }return result;
    }

    public double calculateSum(double [] mass){
        double result = 0;
        for (double i:mass
        ) {
            result+=i;
        }return result;
    }
}
