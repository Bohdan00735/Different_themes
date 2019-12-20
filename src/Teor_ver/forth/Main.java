package Teor_ver.forth;

public class Main {
    public static void main(String[] args) {
        int numOfCycles = 50000;
        BinomianFunctional b1 = new BinomianFunctional(50,0.6,numOfCycles);
        BinomianFunctional b2 = new BinomianFunctional(60,0.4,numOfCycles);

        ErrorsCalculator calculator = new ErrorsCalculator(b1,b2,0.6,1,2);

        float[] res = calculator.calculate();
        double[] bounds = calculator.calculateBOunds();

        calculator.calculateAndOutNP();
        calculator.findPointOfIntersection();

        System.out.printf("Error of first level is %f; \n" +
                "Error of second level is %f \n", res[0], res[1]);
        System.out.printf("Bounds is {%.2f;%.2f)", bounds[0], bounds[1]);

    }
}
