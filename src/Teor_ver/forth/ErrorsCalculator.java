package Teor_ver.forth;

public class ErrorsCalculator {
    BinomianFunctional b1;
    BinomianFunctional b2;
    double p;
    double c1;
    double c2;
    double s1;
    double s2;
    double m1;
    double m2;

    public ErrorsCalculator(BinomianFunctional b1, BinomianFunctional b2, double p, double c1, double c2) {
        this.b1 = b1;
        this.b2 = b2;
        this.p = p;
        this.c1 = c1;
        this.c2 = c2;
        s1 = b1.getS();
        s2 = b2.getS();
        m1 = b1.getM();
        m2 = b2.getM();
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
        double l = p*c1/((1-p)*c2);

        double a = s2*s2-s1*s1;
        double b = 2*m2*s1*s1-2*m1*s2*s2;
        double c = m1*m1*s2*s2-m2*m2*s1*s1-2*s1*s1*s2*s2*Math.log(s2*l/s1);
        double d = b*b-4*a*c;
        double y1 = (-b-Math.sqrt(d))/(2*a);
        double y2 = (-b+Math.sqrt(d))/(2*a);

        return new double[]{y1,y2};
    }

    public double calculateAndOutNP(){
        System.out.printf("e^((((x-%.1f)/(%.1f))^2)/2-(((x-%.1f)/(%.1f))^2)/2 < p*C0/((1-p)*C1) = %.1f \n",
                m1,s1,m2,s2,p*c1/((1-p)*c2));

        double a = square(s2)- square(s1);
        double b = 2*(m2*square(s1)-m1*square(s2));
        double c = square(m1)*square(s2)-square(m2)*square(s1)-2*square(s1)*square(s2)*Math.log(p*c1/((1-p)*c2));
        double result = calculsteAndChoise(a,b,c);
        System.out.println("From criterion of Neiman Pirson x is lower than: " + result);
        return result;
    }

    public double findPointOfIntersection(){

        double a = square(s2)- square(s1);
        double b = 2*(m2*square(s1)-m1*square(s2));
        double c = square(m1)*square(s2)-square(m2)*square(s1);
        double res = calculsteAndChoise(a,b,c);

        System.out.printf("Point of Intersection is %.1f \n", res);
        return res;
    }

    public boolean expression(double x){
        return b2.functionMaker(x)/b1.functionMaker(x) < p*c1/((1-p)*c2);
    }

    public double square(double x){return x*x;}

    public double[] unleashSquareEquation(double a, double b, double c){
        double x1 = (-1*b + Math.sqrt(square(b) -4*a*c))/(2*a);
        double x2 = (-1*b - Math.sqrt(square(b)-4*a*c))/(2*a);
        return new double[]{x1, x2};
    }

    public double calculsteAndChoise(double a,double b,double c){
        double[] results = unleashSquareEquation(a,b,c);
        double res;

        double x1 = results[0];
        double x2 = results[1];
        if(x1 < b1.n){
            res = x1;
        }else {
            res = (x2 < b1.n)?  x2 : 0;
        }
        return res;
    }
}
