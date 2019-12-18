package Teor_ver.sixthLab;

public class Main {
    public static void main(String[] args) {
        float[][] tableOfStates = new float[][]{
                {-1,1,2,3,4},
                {2,-1,3,5,6},
                {3,6,-1,4,1},
                {4,2,6,-1,3},
                {1,3,4,35,-1}
        };


        StateParce stateParce = new StateParce();

        State[] states = new State[tableOfStates.length];
        for (int i=0; i < tableOfStates.length; i++ ){
            states[i] = stateParce.parseTableToState(tableOfStates[i]);
        }

        ProbabilityCalculator probabilityCalculator =
                new ProbabilityCalculator(states, 5000);

        float[] experementalResults = probabilityCalculator.experementalCalculate();
        float[] teorResults = probabilityCalculator.handleCalculate();
        float sum1=0;
        float sum2 = 0;

        for (int i = 0; i < experementalResults.length; i ++){
            System.out.printf("%d. Experemental results is %f teoretical results is %f \n",
                    i+1, experementalResults[i], teorResults[i]);
            sum1+=experementalResults[i];
            sum2 += teorResults[i];
        }

        System.out.printf("sum of practical is %f , teoretical - %f",sum1,sum2);



    }
}
