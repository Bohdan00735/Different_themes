package Teor_ver.jeka;

public class Test {
    private double P;
    private double c1;
    private double c2;
    private NormalFunction f0;
    private NormalFunction f1;

    public static void main(String[] args) {
        Test t = new Test();
        int SecontErrorCounter = 0;
        int FirstErrorCounter = 0;

        int i;
        double x;
        for(i = 0; i < 10000; ++i) {
            x = t.f0.generateNumber();
            if (!t.expression(x)) {
                ++SecontErrorCounter;
            }
        }

        for(i = 0; i < 10000; ++i) {
            x = t.f1.generateNumber();
            if (t.expression(x)) {
                ++FirstErrorCounter;
            }
        }

        t.getBounds();
        System.out.println("Probability of first error:" + (double)FirstErrorCounter / 10000.0D);
        System.out.println("Probability of second error:" + (double)SecontErrorCounter / 10000.0D);
    }

    public Test() {
        double m0 = 0.0D;
        double s0 = 10.0D;
        double m1 = 10.0D;
        double s1 = 1.0D;
        this.f0 = new NormalFunction(s0, m0);
        this.f1 = new NormalFunction(s1, m1);
        this.P = 0.5D;
        this.c1 = 1.0D;
        this.c2 = 1.0D;
    }

    private void getBounds() {
        double s0 = this.f0.getSigma();
        double m0 = this.f0.getM();
        double s1 = this.f1.getSigma();
        double m1 = this.f1.getM();
        double l = this.P * this.c1 / ((1.0D - this.P) * this.c2);
        if (s0 != s1) {
            double a = s1 * s1 - s0 * s0;
            double b = 2.0D * m1 * s0 * s0 - 2.0D * m0 * s1 * s1;
            double c = m0 * m0 * s1 * s1 - m1 * m1 * s0 * s0 - 2.0D * s0 * s0 * s1 * s1 * Math.log(s1 * l / s0);
            double D = b * b - 4.0D * a * c;
            double y1 = (-b - Math.sqrt(D)) / (2.0D * a);
            double y2 = (-b + Math.sqrt(D)) / (2.0D * a);
            System.out.println("Bounds: { " + y1 + " ; " + y2 + " }");
        }
        else {
            System.out.println("X = " + (m1 + m0)/2);
        }
    }

    public boolean expression(double x) {
        return this.f1.get(x) / this.f0.get(x) < this.P * this.c1 / ((1.0D - this.P) * this.c2);
    }
}
