package MOPE.lab5;

import MOPE.Lab3.Auditor;
import MOPE.Lab4.GUI;

public class Experimenter {
    ThreeFactorExperimentWithSquareMembers experiment;
    double[] dispersions;
    Auditor auditor;
    GUI gui;

    public Experimenter(int m, int x1Max, int x1Min, int x2Max, int x2Min, int x3Max, int x3Min){
        experiment = new ThreeFactorExperimentWithSquareMembers(m, x1Max, x1Min, x2Max, x2Min, x3Max, x3Min);
        experiment.addSquareMembers();
        experiment.generateNewExperementalValues();
        auditor = new Auditor();
        makeACheck();
    }

    private void makeACheck() {
        checkForHomogenity();
        experiment.calculateNaturalizedBCoeficients();
        auditor.checkRegressionEquation(experiment.bCoefficents,
                experiment.naturalizedFactorValues, experiment.yMediums);
        auditor.checkStudent(dispersions, experiment.m, experiment.calculateMediums(experiment.yExperimentalValues),
                experiment.normalizedFactorValues, experiment.bCoefficents, experiment.naturalizedFactorValues);
        auditor.checkFisher(experiment.m,experiment.calculateMediums(experiment.yExperimentalValues));
        gui = new GUI(experiment.m, experiment.makeTable());
        gui.formFactorColumnNames(new String[]{"X0", "X1", "X2", "X3", "X12", "X13", "X23", "X123","X11","X22","X33"});
        gui.start();
    }


    void checkForHomogenity(){//set our experiment for right m
        while (true){
            dispersions = experiment.calculateDispersionMatrix(experiment.yExperimentalValues);
            if(auditor.checkKohren(dispersions, experiment.m-1, experiment.N)){
                System.out.println("the dispersion is homogeneous by Kohren with q = 0.05, m = " + experiment.m);
                break;
            }else {System.out.println("the dispersion is NOT homogeneous by Kohren, m = " + experiment.m);
                experiment.setM(experiment.m + 1);
                experiment.generateNewExperementalValues();
            }
        }
    }
}
