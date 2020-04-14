package MOPE.Lab4;

import MOPE.Lab3.Auditor;

public class Controler {
    //class that save algorithm of actions to do
    ThreeFactorExperimentWithInteractionEffect experiment;
    Auditor auditor;
    double[] dispersions;
    GUI gui;
    boolean wayToDO = false;// true begin from start,
    // false - only second part of algorithm for Three Factor Experiment With Interaction Effect

    public Controler(ThreeFactorExperimentWithInteractionEffect experiment) {
        this.experiment = experiment;
        auditor = new Auditor();
    }

    private void restartAlgorithm(){
        //reload all parameters to restart

        experiment = new ThreeFactorExperimentWithInteractionEffect(3,experiment.xValues[0][0],
                experiment.xValues[0][1],experiment.xValues[1][0],experiment.xValues[1][1],
                experiment.xValues[2][0], experiment.xValues[2][1]);
        if (wayToDO){mainAlgorithm();
        }else {secondWay();}

    }

    public void mainAlgorithm(){
        checkForHomogenity();
        auditor.checkRegressionEquation(experiment.bCoefficents,
                experiment.naturalizedFactorValues, experiment.yMediums);
        auditor.checkStudent(dispersions,experiment.m, experiment.calculateMediums(experiment.yExperimentalValues),
                experiment.normalizedFactorValues, experiment.bCoefficents, experiment.naturalizedFactorValues);

        if (auditor.checkFisher(experiment.m,experiment.calculateMediums(experiment.yExperimentalValues))){
            gui = new GUI(experiment.m, experiment.makeTable());
            gui.formFactorColumnNames();
            gui.start();
        }else{
            wayToDO=true;
            secondWay();}
    }

    public void secondWay(){
        //second part of algorithm
        experiment.addInteractionEffect();
        experiment.generateNewExperementalValues();
        checkForHomogenity();
        auditor.checkRegressionEquation(experiment.bCoefficents,
                experiment.naturalizedFactorValues, experiment.yMediums);
        auditor.checkStudent(dispersions, experiment.m, experiment.calculateMediums(experiment.yExperimentalValues),
                experiment.normalizedFactorValues, experiment.bCoefficents, experiment.naturalizedFactorValues);

        if (auditor.checkFisher(experiment.m,experiment.calculateMediums(experiment.yExperimentalValues))){
            gui = new GUI(experiment.m, experiment.makeTable());
            gui.formFactorColumnNames();
            gui.start();
        }else{restartAlgorithm();}
    }

    void checkForHomogenity(){//set our experiment for right m
        while (true){
            experiment.calculateNormCoefficient();
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
