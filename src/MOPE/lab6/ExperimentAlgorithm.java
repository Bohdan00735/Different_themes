package MOPE.lab6;

import MOPE.Lab3.Auditor;
import MOPE.Lab4.GUI;

public class ExperimentAlgorithm {
    ThreeFactorExperimentLastEdition experiment;
    Auditor auditor = new Auditor();
    double[] dispersions;
    GUI gui;

    public ExperimentAlgorithm(int m, int x1Max, int x1Min, int x2Max, int x2Min, int x3Max, int x3Min) {
        //on this stage creates linear equipment
        experiment = new ThreeFactorExperimentLastEdition(m, x1Max, x1Min, x2Max, x2Min, x3Max, x3Min);
    }

    public void start(){
        experiment.generateNewExperimentalValues();
        dispersions = experiment.calculateDispersionMatrix(experiment.yExperimentalValues);

        if (experiment.N == 8){//additional task
            //if in experiment with interaction effect dispersion isn`t homogeneous - add square members
            if(!auditor.checkKohren(dispersions, experiment.m-1, experiment.N)){
                experiment.addSquareMembers();
                start();
            }
        }

        auditor.checkForHomogenity(experiment);//set right m for our experiment, use Kohren check
        experiment.calculateNaturalizedBCoeficients();// calculate Coefficients by division of matrix
        auditor.checkRegressionEquation(experiment.bCoefficents,
                experiment.naturalizedFactorValues, experiment.yMediums);//calculate and show loyalty of coefficients

        //detects isn`t significant coefficients
        auditor.checkStudent(dispersions, experiment.m, experiment.calculateMediums(experiment.yExperimentalValues),
                experiment.normalizedFactorValues, experiment.bCoefficents, experiment.naturalizedFactorValues);

        // check model adequacy
        if (auditor.checkFisher(experiment.m,experiment.calculateMediums(experiment.yExperimentalValues))){
            gui = new GUI(experiment.m, experiment.makeTable());
            String[] columnNames;
            switch (experiment.N){
                case 8://for experiment with interaction effect
                    columnNames = new String[]{"X0", "X1", "X2", "X3", "X12", "X13", "X23", "X123"};
                    break;
                case 4://for linear form
                    columnNames = new String[]{"X0","X1", "X2","X3"};
                    break;
                default: columnNames = new String[]{"X0", "X1", "X2", "X3", "X12", "X13", "X23", "X123","X11","X22","X33"};
                //for experiment with square members
            }
            gui.formFactorColumnNames(columnNames);
            gui.start();//to show table of the experiment
        }else{
            switch (experiment.N){
                case 8://if interaction is not enough add square members to our equipment
                    experiment.addSquareMembers();
                    break;
                case 4://if linear equipment is adequate, add interaction effect to our equipment
                    experiment.addInteractionEffect();
                    break;
            }
            start();// restart algorithm with form of equipment
        }
    }
}
