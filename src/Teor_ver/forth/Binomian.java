package Teor_ver.forth;
import java.util.Arrays;

public class Binomian {
    int n ;
    double p;
    int c;
    double r;
    double[] PM;
    double[] SPM;
    float[] RM;

    double m;
    double s;

    public Binomian(int n, double p, int c) {
        this.n = n;
        this.p = p;
        this.c = c;
        m = p*n;
        s = m*(1-p);

        PM = new double [n + 1];
        SPM = new double [n + 1];
        RM = new float[c];

        makeBinomian();
    }

    public int getC() {
        return c;
    }

    public float[] getRM() {
        return RM;
    }

    public double getM() {
        return m;
    }

    public double getS() {
        return s;
    }

    public void makeBinomian(){
            for(int i = 0; i < n+1; i++){
                PM[i] = bernulli(i, n, p);}

            for(int i = 0; i < n + 1; i++){
                for(int j = 1; j <= i; j++)
                    SPM[i] += PM[j];
            }

            boolean flag = true;
            for(int i = 0; i < c; i++){
                do {
                    r = Math.random();
                    if (r > SPM[n]) flag = false;
                } while (!flag);
                for(int j = 0; j < n; j++){
                    if ((r > SPM[j]) && (r < SPM[j+1])){
                        RM[i] = j;
                    }
                }
            }
        }


    public static double bernulli(int k, int n, double p) {

        double Cmk, Pk, Pmk, P;

        Pk = Math.pow(p, k);
        Pmk = Math.pow(1 - p, n - k);
        Cmk = factorial(n)/(factorial(n - k)*factorial(k));
        P = Cmk * Pk * Pmk;
        return P;

    }

    public static double factorial(double a) {

        double r = 1;
        for(int i = 2; i <= a; i++)
            r *= i;
        return r;

    }

}
