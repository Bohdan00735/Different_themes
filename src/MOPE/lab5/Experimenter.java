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
        experiment.generateNewExperimentalValues();
        auditor = new Auditor();
        makeACheck();
    }

    private void makeACheck() {
        auditor.checkForHomogenity(experiment);//set correct m
        dispersions = experiment.calculateDispersionMatrix(experiment.yExperimentalValues);
        experiment.calculateNaturalizedBCoeficients();
        auditor.checkRegressionEquation(experiment.bCoefficents,
                experiment.naturalizedFactorValues, experiment.yMediums);//calculate and show loyalty of coefficients

        //detects isn`t significant coefficients
        auditor.checkStudent(dispersions, experiment.m, experiment.calculateMediums(experiment.yExperimentalValues),
                experiment.normalizedFactorValues, experiment.bCoefficents, experiment.naturalizedFactorValues);

        // check model adequacy
        auditor.checkFisher(experiment.m,experiment.calculateMediums(experiment.yExperimentalValues));
        gui = new GUI(experiment.m, experiment.makeTable());
        gui.formFactorColumnNames(new String[]{"X0", "X1", "X2", "X3", "X12", "X13", "X23", "X123","X11","X22","X33"});
        gui.start();
    }
}
