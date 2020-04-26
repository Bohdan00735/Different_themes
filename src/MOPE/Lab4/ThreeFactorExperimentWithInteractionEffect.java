package MOPE.Lab4;

import MOPE.Lab3.FractionalThreefactorExperiment;

public class ThreeFactorExperimentWithInteractionEffect extends FractionalThreefactorExperiment {
    //use methods from previous lab to check all methods and save structure of experiment

    public ThreeFactorExperimentWithInteractionEffect(int m, int x1Max, int x1Min, int x2Max, int x2Min, int x3Max, int x3Min) {
        super(m, x1Max, x1Min, x2Max, x2Min, x3Max, x3Min);
    }

    @Override
    public void calculateNormCoefficient() {
        if (N == 4){super.calculateNormCoefficient();// that means that we dont have interaction effect
        }else {
            //calculate with normalized factors
            yMediums = calculateMediums(yExperimentalValues);
            bCoefficents = new double[N];
            bCoefficents[0] = calculateSumDouble(yMediums)/N;
            for (int i = 1; i < bCoefficents.length; i++) {
                bCoefficents[i] = matrixCalculator.calculateSumTwoArrayForEach
                        (matrixCalculator.getColumn(i-1,normalizedFactorValues),yMediums)/N;
            }
            naturalizeCoefficients();// naturalize our coefficients
        }
    }

    void naturalizeCoefficients(){
        for (int i = 1; i < bCoefficents.length; i++) {
            double xmax = findMaxInColumn(naturalizedFactorValues,i-1);
            double xmin = findMinInColumn(naturalizedFactorValues, i-1);
            double xi0 = (xmax+xmin)/2.0;
            double xi = Math.abs(xmax-xmin)/2.0;
            bCoefficents[0] -= bCoefficents[i]*(xi0/xi);
            bCoefficents[i] = bCoefficents[i]/xi;
        }
    }

    public double findMaxInColumn(double[][] matrix, int column){
        double result = matrix[0][column];
        for (double[] raw:matrix
        ) {
            if (raw[column] > result){result = raw[column];}
        }return result;
    }

    public double findMinInColumn(double[][] matrix, int column){
        double result = matrix[0][column];
        for (double[] raw:matrix
        ) {
            if (raw[column] < result){result = raw[column];}
        }return result;
    }

    public void addInteractionEffect(){
        System.out.println("add Interaction Effect \n y = bo + b1*x1 + b2*x2 + b3*x3 + b12*x1*x2" +
                " + b13*x1*x3 + b23*x2*x3 + b123*x1*x2*x3");
        N = 8;
        m = 2;
        naturalizedFactorValues = addInteractionFactors(addParams(xValues, new double[8][7]));
        normalizedFactorValues = addInteractionFactors(
                addParams(new int[][]{{-1,1},{-1,1},{-1,1}}, new double[8][7]));
    }

    public double[][] addInteractionFactors(double[][] table){
        //rewrite plan table

        //size of the plan matrix for Three Factor Experiment With Interaction Effect

        for (double [] raw:table
             ) {
            raw[3] = raw[0]*raw[1];
            raw[4] = raw[0]*raw[2];
            raw[5] = raw[1]*raw[2];
            raw[6] = raw[0]*raw[1]*raw[2];
        }
        return table;
    }

    public double[][] addParams(int[][] xValues, double[][] table){
        //size of the plan matrix for Three Factor Experiment With Interaction Effect
        byte config = 0;
        for (double [] raw:table
        ) {
            int index = Integer.toBinaryString(config).length() - 1;
            for (int i = 2; i >= 0; i--) {
                try {
                    raw[i] = xValues[i][Character.getNumericValue(Integer.toBinaryString(config).charAt(index))];
                } catch (StringIndexOutOfBoundsException e) {
                    raw[i] = xValues[i][0];// in case when config < 100b
                }
                index--;
            }
            config++;
        }return table;
    }
}
