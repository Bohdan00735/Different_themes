package MOPE.Lab4;


public class Main {
    public static void main(String[] args) {
        ThreeFactorExperimentWithInteractionEffect experiment = new ThreeFactorExperimentWithInteractionEffect(3,
                50,-10,60,-20,5,-20);

        Controler controler = new Controler(experiment);
        controler.secondWay();// change to main algorithm to do all from start
    }
}
