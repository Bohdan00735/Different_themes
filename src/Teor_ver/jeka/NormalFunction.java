package Teor_ver.jeka;
import java.util.Random;

public class NormalFunction {



        private double sigma;
        private double m;

        public NormalFunction(double sigma, double m) {
            this.sigma = sigma;
            this.m = m;
        }

        public double generateNumber() {
            Random rand = new Random();
            double summ = 0.0D;

            for(int i = 0; i < 12; ++i) {
                summ += rand.nextDouble();
            }

            return (summ - 6.0D) * this.sigma + this.m;
        }

        public double get(double x) {
            double arg = -(x - this.m) * (x - this.m) / (2.0D * this.sigma * this.sigma);
            return 1.0D / (Math.sqrt(6.283185307179586D) * this.sigma) * Math.exp(arg);
        }

        public double getSigma() {
            return this.sigma;
        }

        public double getM() {
            return this.m;
        }

}
