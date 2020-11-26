package Teor_ver.sixthLab;

import junit.framework.TestCase;

public class MatrixTester extends TestCase {
    ProbabilityCalculator probabilityCalculator = new ProbabilityCalculator(new State[5], 2);

    double[][] matrix = new double[][]{{5,6,7},
            {4,3,2},
            {8,9,0}};

    public void testCalculateDeterminant() {
        System.out.println(probabilityCalculator.calculateDeterminant(matrix));
    }

    public void testKramerCalculate() {

        double[] ansers = new double[]{1,2,3};

        for (float i:probabilityCalculator.kramerCalculate(matrix, ansers)
             ) {
            System.out.println(i);
        }
    }
}
