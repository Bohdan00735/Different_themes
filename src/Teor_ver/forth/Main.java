package Teor_ver.forth;

public class Main {
    public static void main(String[] args) {
        BinomianFunctional b1 = new BinomianFunctional(50,0.6,5000);
        BinomianFunctional b2 = new BinomianFunctional(60,0.4,5000);

        ErrorsCalculator calculator = new ErrorsCalculator(b1,b2,0.6,2,1);

        float[] res = calculator.calculate();
        double[] bounds = calculator.calculateBOunds();

        System.out.printf("Error of first level is %f; \n" +
                "Error of second level is %f \n", res[0], res[1]);
        System.out.printf("Bounds is {%.2f;%.2f)", bounds[0], bounds[1]);

    }
}
