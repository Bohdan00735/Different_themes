package MOPE.Lab3;
import org.apache.commons.math3.util.Precision;

import java.util.ArrayList;
import java.util.HashMap;

import static java.lang.StrictMath.sqrt;

public class Auditor {
    double[] KohrenTable = new double[]{0.9065,0.7679,0.6841,0.6287,0.5892,0.5598,0.5365,0.5175, 0.5017, 0.4884};
    // there`re data for f2 = 4 and m lower then 11, q = 0.05

    int f1,f2,f3,f4;
    int d;
    double[] studentResults;
    double s2B;

    HashMap <Integer, Double> Student = new HashMap<>();
    MatrixCalculator matrixCalculator = new MatrixCalculator();

    public Auditor() {
        //fill Student`s factor N = 4 - const, so we can add only raws that multiple to N
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
                    output.append("+").append(Precision.round(bCoefficient[j],2)).append("*").
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
        return gp < KohrenTable[f1];
    }

    public void checkStudent(double[] dispersions, int m,double[] yMediums, int[][] normalisedCoefficients,
                                double[] bCoefficients, int[][] naturalisedFactors){
        s2B = calculateSum(dispersions)/dispersions.length;
        double sFromB = sqrt(s2B/(dispersions.length*m));
        double[] betaValues = new double[dispersions.length];
        betaValues[0] = matrixCalculator.calculateSumTwoArrayForEach(new int[]{1,1,1,1},yMediums);// column with x0 = 1
        for (int i = 1; i < betaValues.length; i++) {
            betaValues[i] = matrixCalculator.calculateSumTwoArrayForEach
                    (matrixCalculator.getColumn(i-1,normalisedCoefficients),yMediums);
        }
        double[] thetaValues = new double[betaValues.length];
        for (int i = 0; i < thetaValues.length; i++) {
            thetaValues[i] = betaValues[i]/sFromB;
        }
        f3 = f1*f2;
        double tetaTable = Student.get(f3);
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
        studentResults = new double[4];
        int i =0;
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

    void checkFisher(int m, double[] yMediums){
        f4 = 4-d;// N always 4 in our case
        double sAD = m/(double)(f4)*matrixCalculator.sumOfDivisionInSquare(studentResults,yMediums);
        double fp = sAD/s2B;
        //the table is big and dispersion of values to, so I just print it and user should determine adequacy independently
        System.out.printf("For Fisher:\n f3 = %d f4 = %d , Fp %.2f \n Check table for determine",f3,f4,fp);
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
