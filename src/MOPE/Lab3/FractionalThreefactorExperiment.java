package MOPE.Lab3;

import MOPE.Lab2.FullFactorExperiment;

public class FractionalThreefactorExperiment extends FullFactorExperiment {// extends for using some methods
    public double[][] naturalizedFactorValues;
    public double[][] normalizedFactorValues;
    public int [][] yExperimentalValues;
    public int [][] xValues;
    public int N;
    public double[] bCoefficents = new double[4];// because we have three factor fractional experiment
    // where y = b0 + b1*x1+ b2*x2+b3+x3

    public MatrixCalculator matrixCalculator = new MatrixCalculator();
    Auditor auditor = new Auditor();

    int x0Normalized = 1;
    public double[] yMediums;

    public String[][] makeTable(){
        // set table for print in future
        String[][] result = new String[2*N][m+bCoefficents.length];
        for (int i = 0; i < normalizedFactorValues.length; i++) {
            for (int j = 0; j < normalizedFactorValues[0].length; j++) {
                result[i][j+1] = String.valueOf(naturalizedFactorValues[i][j]);
                result[i+N][j+1] = String.valueOf(normalizedFactorValues[i][j]);
            }
            result[i+N][0] = String.valueOf(x0Normalized);
        }

        for (int i = 0; i < yExperimentalValues.length; i++) {
            for (int j = 0; j < m; j++) {
                result[i][j+bCoefficents.length] = result[i+N][j+bCoefficents.length-1] =
                        String.valueOf(yExperimentalValues[i][j]);
            }
        }
        return result;
    }

    public FractionalThreefactorExperiment(int m, int x1Max, int x1Min, int x2Max, int x2Min, int x3Max, int x3Min) {
        super();
        super.m = m;
        System.out.println("y = bo + b1*x1 + b2*x2 + b3*x3");
        N = 4;
        xValues = new int[][]{{x1Min, x1Max},{x2Min,x2Max},{x3Min,x3Max}};
        naturalizedFactorValues = new double[N][3];
        normalizedFactorValues = new double[N][3];
        fillByCoefficients(normalizedFactorValues,-1,1,1,-1,1,-1);
        fillByCoefficients(naturalizedFactorValues, x1Min, x1Max,x2Max,x2Min,x3Max,x3Min);
        super.setyMax(200 + (x1Max+x2Max+x3Max)/3.0);
        super.setyMin(200 + (x1Min+x2Min+x3Min)/3.0);

        yExperimentalValues = generateInDiap((int)yMin, (int)yMax, m, N);
    }

    @Override
    public void calculateNormCoefficient() {
        //find medium values of function for rows
        yMediums = calculateMediums(yExperimentalValues);
        double mediumY = calculateSumDouble(yMediums)/yMediums.length;
        // get each column of factors
        double[] firstColumnInNaturalized = matrixCalculator.getColumn(0,naturalizedFactorValues);
        double[] secondColumnInNaturalized = matrixCalculator.getColumn(1,naturalizedFactorValues);
        double[] thirdColumnInNaturalized = matrixCalculator.getColumn(2,naturalizedFactorValues);

        double mx1 = calculateSum(firstColumnInNaturalized)/N;
        double mx2 = calculateSum(secondColumnInNaturalized)/N;
        double mx3 = calculateSum(thirdColumnInNaturalized)/N;

        double a1 = matrixCalculator.calculateSumTwoArrayForEach(firstColumnInNaturalized, yMediums)/N;
        double a2 = matrixCalculator.calculateSumTwoArrayForEach(secondColumnInNaturalized, yMediums)/N;
        double a3 = matrixCalculator.calculateSumTwoArrayForEach(thirdColumnInNaturalized, yMediums)/N;

        double a11 = calculateSumOfEachSquare(firstColumnInNaturalized)/N;//N = length of each column in this table
        double a22 = calculateSumOfEachSquare(secondColumnInNaturalized)/N;
        double a33 = calculateSumOfEachSquare(thirdColumnInNaturalized)/N;

        double a13 = matrixCalculator.calculateSumTwoArrayForEach(firstColumnInNaturalized,
                thirdColumnInNaturalized)/N;//a13 = 31; N - num of experiments
        double a12 = matrixCalculator.calculateSumTwoArrayForEach(firstColumnInNaturalized,
                secondColumnInNaturalized)/N;//a12 = a21
        double a23 =  matrixCalculator.calculateSumTwoArrayForEach(secondColumnInNaturalized,
                thirdColumnInNaturalized)/N;// a23 = a32

        //make our base matrix for determinant
        matrixCalculator.formMatrixOfTheFourthOrder(1, mx1, mx2,mx3,
                                                    mx1,a11,a12,a13,
                                                    mx2,a12,a22,a23,
                                                    mx3,a13,a23,a33);

        double[] columnToChange = new double[]{mediumY, a1,a2,a3};// our right part of equation
        makeCalculationOfBCoefficient(columnToChange);
    }

    public void makeAudit(){
        // start of all audit methods
        auditor.checkRegressionEquation(bCoefficents, naturalizedFactorValues, yMediums);
        double[] dispersions = calculateDispersionMatrix(yExperimentalValues);
        if(auditor.checkKohren(dispersions, m-1, N)){
            System.out.println("the dispersion is homogeneous by Kohren with q = 0.05");
        }else {System.out.println("the dispersion is NOT homogeneous by Kohren");}
        auditor.checkStudent(dispersions,m,yMediums,normalizedFactorValues,bCoefficents,naturalizedFactorValues);
        auditor.checkFisher(m,yMediums);
    }

    void makeCalculationOfBCoefficient(double[] changeColumn){

        double mainDeterminant = matrixCalculator.calculateDeterminant();
        for (int i = 0; i < bCoefficents.length; i++) {
            // there we calculate each coefficient by division determinant of changed matrix on main matrix determinant
            bCoefficents[i] = matrixCalculator.calculateDeterminant(matrixCalculator.changeSomeColmn(changeColumn,i))/
                    mainDeterminant;
        }
    }

    public double calculateSumOfEachSquare(double [] array){
        double result = 0;
        for (double value : array) {
            result += Math.pow(value, 2);
        }
        return result;
    }

    public double calculateSumDouble(double [] mass) {
        double result = 0;
        for (double i:mass
             ) {result+=i;
        }
        return result;
    }

    void fillByCoefficients(double[][] matrix,int x1Min, int x1Max, int x2Max, int x2Min, int x3Max, int x3Min){
        //bringing together all data in right order as in plan matrix
        matrix[0] = new double[]{x1Min, x2Min, x3Min};
        matrix[1] = new double[]{x1Min, x2Max, x3Max};
        matrix[2] = new double[]{x1Max, x2Min, x3Max};
        matrix[3] = new double[]{x1Max, x2Max, x3Min};
    }
}
