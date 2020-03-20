package MOPE.Lab3;


import MOPE.Lab2.FullFactorExperiment;

public class FractionalThreefactorExperiment extends FullFactorExperiment {
    int[][] naturalizedFactorValues;
    int[][] normalizedFactorValues;
    int [][] yExperimentalValues;
    double[] bCoefficents = new double[4];// because we have three factor fractional experiment
    // where y = b0 + b1*x1+ b2*x2+b3+x3

    MatrixCalculator matrixCalculator = new MatrixCalculator();
    Auditor auditor = new Auditor();

    int x0Normalized = 1;
    double[] yMediums;

    String[][] makeTable(){
        String[][] result = new String[8][m+4];
        for (int i = 0; i < normalizedFactorValues.length; i++) {
            for (int j = 0; j < normalizedFactorValues[0].length; j++) {
                result[i][j+1] = String.valueOf(naturalizedFactorValues[i][j]);
                result[i+4][j+1] = String.valueOf(normalizedFactorValues[i][j]);
            }
            result[i+4][0] = String.valueOf(x0Normalized);
        }

        for (int i = 0; i < yExperimentalValues.length; i++) {
            for (int j = 0; j < yExperimentalValues[0].length; j++) {
                result[i][j+4]= result[i+4][j+4] = String.valueOf(yExperimentalValues[i][j]);
            }
        }
        return result;
    }

    public FractionalThreefactorExperiment(int m, int x1Max, int x1Min, int x2Max, int x2Min, int x3Max, int x3Min) {
        super();
        super.m = m;
        naturalizedFactorValues = new int[4][3];
        normalizedFactorValues = new int[4][3];
        fillByCoefficients(normalizedFactorValues,-1,1,1,-1,1,-1);
        fillByCoefficients(naturalizedFactorValues, x1Min, x1Max,x2Max,x2Min,x3Max,x3Min);
        super.setyMax(200 + (x1Max+x2Max+x3Max)/3.0);
        super.setyMin(200 + (x1Min+x2Min+x3Min)/3.0);

        yExperimentalValues = generateInDiap((int)super.yMin, (int)super.yMax, super.m, 4);
    }

    public void setm(int m) {
        //for change number of investigation each time when we need it
        super.setM(m);
    }

    @Override
    public void calculateNormCoefficient() {
        //find medium values of function for rows
        yMediums = calculateMediums(yExperimentalValues);
        double mediumY = calculateSumDouble(yMediums)/yMediums.length;
        int[] firstColumnInNaturalized = matrixCalculator.getColumn(0,naturalizedFactorValues);
        int[] secondColumnInNaturalized = matrixCalculator.getColumn(1,naturalizedFactorValues);
        int[] thirdColumnInNaturalized = matrixCalculator.getColumn(2,naturalizedFactorValues);

        double mx1 = calculateSum(firstColumnInNaturalized)/4;
        double mx2 = calculateSum(secondColumnInNaturalized)/4;
        double mx3 = calculateSum(thirdColumnInNaturalized)/4;

        double a1 = matrixCalculator.calculateSumTwoArrayForEach(firstColumnInNaturalized, yMediums)/4;
        double a2 = matrixCalculator.calculateSumTwoArrayForEach(secondColumnInNaturalized, yMediums)/4;
        double a3 = matrixCalculator.calculateSumTwoArrayForEach(thirdColumnInNaturalized, yMediums)/4;

        double a11 = calculateSumOfEachSquare(firstColumnInNaturalized)/4;//4 = length of each column in this table
        double a22 = calculateSumOfEachSquare(secondColumnInNaturalized)/4;
        double a33 = calculateSumOfEachSquare(thirdColumnInNaturalized)/4;

        double a13 = matrixCalculator.calculateSumTwoArrayForEach(firstColumnInNaturalized,
                converIntArrayToDoubleArray(thirdColumnInNaturalized))/4;//a13 = 31; 4 - num of experiments
        double a12 = matrixCalculator.calculateSumTwoArrayForEach(firstColumnInNaturalized,
                converIntArrayToDoubleArray(secondColumnInNaturalized))/4;//a12 = a21
        double a23 =  matrixCalculator.calculateSumTwoArrayForEach(secondColumnInNaturalized,
                converIntArrayToDoubleArray(thirdColumnInNaturalized))/4;// a23 = a32

        matrixCalculator.formMatrixOfTheFourthOrder(1, mx1, mx2,mx3,
                                                    mx1,a11,a12,a13,
                                                    mx2,a12,a22,a23,
                                                    mx3,a13,a23,a33);

        double[] columnToChange = new double[]{mediumY, a1,a2,a3};
        makeCalculationOfBCoefficient(columnToChange);
    }

    void makeAudit(){
        auditor.checkRegressionEquation(bCoefficents, naturalizedFactorValues, yMediums);
        double[] dispersions = new double[yMediums.length];
        for (int i = 0; i < dispersions.length; i++) {
            dispersions[i] = calculateDispersion(yMediums[i], yExperimentalValues[i]);
        }
        if(auditor.checkKohren(dispersions, m-1, 4)){
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

    double calculateSumOfEachSquare(int [] array){
        int result = 0;
        for (int value : array) {
            result += Math.pow(value, 2);
        }
        return result;
    }

    public double[] converIntArrayToDoubleArray(int[] array) {
        double[] doubles = new double[array.length];
        for (int i = 0; i < array.length; i++) {
            doubles[i] =array[i];
        }return doubles;
    }


    private double calculateSumDouble(double [] mass) {
        double result = 0;
        for (double i:mass
             ) {result+=i;
        }
        return result;
    }

    void fillByCoefficients(int[][] matrix,int x1Min, int x1Max, int x2Max, int x2Min, int x3Max, int x3Min){
        matrix[0] = new int[]{x1Min, x2Min, x3Min};
        matrix[1] = new int[]{x1Min, x2Max, x3Max};
        matrix[2] = new int[]{x1Max, x2Min, x3Max};
        matrix[3] = new int[]{x1Max, x2Max, x3Min};
    }
}
