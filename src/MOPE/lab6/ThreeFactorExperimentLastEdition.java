package MOPE.lab6;

import MOPE.lab5.ThreeFactorExperimentWithSquareMembers;

public class ThreeFactorExperimentLastEdition extends ThreeFactorExperimentWithSquareMembers {
    //extends from all forms of three factor experiment
    public ThreeFactorExperimentLastEdition(int m, int x1Max, int x1Min, int x2Max, int x2Min, int x3Max, int x3Min) {
        super(m, x1Max, x1Min, x2Max, x2Min, x3Max, x3Min);
    }

    @Override
    public void generateNewExperimentalValues() {
        yExperimentalValues = generateByFunc();
    }

    double[][] generateByFunc(){
        double[][] result = new double[N][m];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < m; j++) {
                double x1 = naturalizedFactorValues[i][0];
                double x2 = naturalizedFactorValues[i][1];
                double x3 = naturalizedFactorValues[i][2];
                result[i][j] = 0.6+4.0*x1+2.8*x2+4.7*x3+3.1*x1*x1+0.4*x2*x2+5.4*x3*x3+
                        5.7*x1*x2+0.1*x1*x3+8.8*x2*x3+0.1*x1*x2*x3 + Math.random()*10 - 5;
            }
        }
        return result;
    }
}
