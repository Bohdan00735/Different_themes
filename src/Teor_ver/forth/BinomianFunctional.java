package Teor_ver.forth;

import java.util.Random;

public class BinomianFunctional extends Binomian{

    public BinomianFunctional(int n, double p, int c) {
        super(n, p, c);
    }

    public double[] getFunctionalRaw(){
        double[] functionalValues = new double[super.c];
        for (int i = 0;i < super.c;i++){
            functionalValues[i] = functionMaker(super.RM[i]);
        }
        return functionalValues;
    }

    public double functionMaker(double value){
        return 1/(2*Math.PI)*Math.exp((-1)*Math.pow(value-super.m , 2)/super.s);
    }
}
