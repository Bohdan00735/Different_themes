package Teor_ver.forth;

public class ErrorsCalculator {
    BinomianFunctional b1;
    BinomianFunctional b2;
    double p;
    double c1;
    double c2;

    public ErrorsCalculator(BinomianFunctional b1, BinomianFunctional b2, double p, double c1, double c2) {
        this.b1 = b1;
        this.b2 = b2;
        this.p = p;
        this.c1 = c1;
        this.c2 = c2;
    }

    public float[] calculate(){
        int counter1 = 0, counter2 = 0;
        float[] raw1 = b1.getRM();
        float[] raw2 = b2.getRM();

        for (int i = 0; i < b1.getC();i++){
            if (!expression(raw1[i])){
                counter2++;
            }
        }

        for (int i = 0; i < b1.getC();i++){
            if (expression(raw2[i])){
                counter1++;
            }
        }
        return new float[]{(float) counter1/b1.getC(), (float) counter2/b2.getC()};
    }

    public double[] calculateBOunds(){
        double s1 = b1.getS();
        double s2 = b2.getS();
        double m1 = b1.getM();
        double m2 = b2.getM();
        double l = p*c1/((1-p)*c2);

        double a = s2*s2-s1*s1;
        double b = 2*m2*s1*s1-2*m1*s2*s2;
        double c = m1*m1*s2*s2-m2*m2*s1*s1-2*s1*s1*s2*s2*Math.log(s2*l/s1);
        double d = b*b-4*a*c;
        double y1 = (-b-Math.sqrt(d))/(2*a);
        double y2 = (-b+Math.sqrt(d))/(2*a);

        return new double[]{y1,y2};
    }

    public boolean expression(double x){
        return b2.functionMaker(x)/b1.functionMaker(x) < p*c1/((1-p)*c2);
    }
}
