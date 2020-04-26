package MOPE.lab5;

import MOPE.Lab4.ThreeFactorExperimentWithInteractionEffect;

public class ThreeFactorExperimentWithSquareMembers extends ThreeFactorExperimentWithInteractionEffect {
    double l = 1.73;

    public ThreeFactorExperimentWithSquareMembers(int m, int x1Max, int x1Min, int x2Max, int x2Min, int x3Max, int x3Min) {
        super(m, x1Max, x1Min, x2Max, x2Min, x3Max, x3Min);
    }

    public void addSquareMembers() {
        N = 15;
        m = 2;
        System.out.println("Function with Square Members \n y = bo + b1*x1 + b2*x2 + b3*x3 + b12*x1*x2" +
                " + b13*x1*x3 + b23*x2*x3 + b123*x1*x2*x3 + b11*x1^2 + b22*x2^2 + b33*x3^2");
        naturalizedFactorValues = addFactorsForExperimentWithSquareMembers(xValues);
        normalizedFactorValues = addFactorsForExperimentWithSquareMembers(new int[][]{{-1,1},{-1,1},{-1,1}});
    }

    public double[][] addFactorsForExperimentWithSquareMembers(int[][] xParamValues){
        return addSquareFactor(addInteractionFactors(fillAllLinearRows(addParams(xParamValues,new double[15][10]))));
    }
    
    double[][] fillAllLinearRows(double[][] table){
        for (int i = 0; i < 3; i++) {
            double x0i = (findMaxInColumn(table,i)+findMinInColumn(table,i))/2;
            double deltaxi = findMaxInColumn(table,i)- x0i;
            for (int j = 0; j < 7; j++) {
                if(j/2 == i){
                    table[j+8][i] = -l*deltaxi+x0i;
                }else if (j/2.0 == i+0.5){
                    table[j+8][i] = l*deltaxi+x0i;
                }else {
                    table[j+8][i] = x0i;
                }
            }
        }return table;
    }


    double[][] addSquareFactor(double[][] tableWithoutSquare){
        for (double[] raw:tableWithoutSquare
             ) {
            for (int i = 0; i < 3; i++) {
                raw[i+7] = Math.pow(raw[i],2);
            }
        }return tableWithoutSquare;
    }

    public void calculateNaturalizedBCoeficients(){
        double[] xMediums = calculateMediumsInColumns(naturalizedFactorValues);
        yMediums = calculateMediums(yExperimentalValues);
        double[] aiValues = calculateAI();
        double[][] aijValues = calculateAMixedValues();
        double[][] matrix = formMatrix(xMediums,aijValues);
        double[] answerColumn = new double[aiValues.length+1];
        answerColumn[0] = calculateSum(yMediums)/yMediums.length;//my
        System.arraycopy(aiValues, 0, answerColumn, 1, answerColumn.length - 1);
        bCoefficents = new double[naturalizedFactorValues[0].length+1];

        double mainDeterminant = matrixCalculator.calculateDeterminant(matrix);
        for (int i = 0; i <bCoefficents.length ; i++) {
            bCoefficents[i] = matrixCalculator.calculateDeterminant
                    (matrixCalculator.changeSomeColumn(matrix,answerColumn,i))/mainDeterminant;
        }
    }

    private double[][] formMatrix(double[] xMediums, double[][] aijValues) {
        double[][] matrix = new double[aijValues.length + 1][aijValues.length + 1];
        matrix[0][0] = 1;
        System.arraycopy(xMediums,0,matrix[0],1, xMediums.length);
        for (int i = 1; i < matrix.length; i++) {
            matrix[i][0] = xMediums[i-1];
            System.arraycopy(aijValues[i-1],0,matrix[i],1, matrix[i].length-1);
        }
        return matrix;
    }

    private double[][] calculateAMixedValues() {
        double[][] result = new double[naturalizedFactorValues[0].length][naturalizedFactorValues[0].length];
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result.length; j++) {
                result[i][j] = matrixCalculator.calculateSumTwoArrayForEach(
                        matrixCalculator.getColumn(i,naturalizedFactorValues),
                        matrixCalculator.getColumn(j,naturalizedFactorValues)
                )/result.length;
            }
        }return result;
    }

    private double[] calculateAI() {
        double[] result =  new double[naturalizedFactorValues[0].length];
        for (int i = 0; i < result.length; i++) {
            result[i] = matrixCalculator.calculateSumTwoArrayForEach
                    (matrixCalculator.getColumn(i,naturalizedFactorValues),yMediums)/N;
        }return result;
    }

    private double[] calculateMediumsInColumns(double[][] matrix) {
        double[] result = new double[matrix[0].length];
        for (int i = 0; i < result.length; i++) {
            result[i] = calculateSum(matrixCalculator.getColumn(i,matrix))/matrix.length;
        }return result;
    }
}