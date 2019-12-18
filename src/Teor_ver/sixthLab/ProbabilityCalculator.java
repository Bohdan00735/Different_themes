package Teor_ver.sixthLab;

public class ProbabilityCalculator {
    State[] states;
    int cycles;

    public ProbabilityCalculator(State[] states, int cycles) {
        this.states = states;
        this.cycles = cycles;

    }

    public float[] handleCalculate(){
        double[][] valuesTable = new double[states.length][states.length];
        double[] answersTable = new double[states.length];
        answersTable[0] = 1;
        for (int i = 0; i < states.length; i++){
            valuesTable[0][i] = 1;
        }
        int carrentState = 1;

        for (int i = 1; i < states.length; i++){
            double[] properities = new double[states.length];
            for (int j = 0; j < states.length; j++){
                if (j+1 == carrentState){
                    properities[j] = (-1)*states[j].calculateOutlines();
                    continue;
                }
                properities[j] = states[j].getInner(carrentState);
            }
            valuesTable[i] = properities;
            carrentState++;
        }

        return kramerCalculate(valuesTable, answersTable);
    }

    public float[] experementalCalculate(){
        float [] results = new float[states.length];
        int nextStep = 1;
        for (int i = 0; i < cycles; i++){
            for (State curr: states){
                if (curr.num == nextStep){
                    nextStep = curr.calculateTimeAndGo();
                    break;
                }
            }
        }

        float sumTime = 0;
        for (int j = 0; j < results.length; j ++){
            results[j] = states[j].getTime();
            sumTime += states[j].getTime();
        }
        for (int j = 0; j < results.length; j ++){
            System.out.printf("for step %d time is %f \n", j+1, results[j]);
            results[j] = results[j]/sumTime;
        }
        System.out.println("Summary time is " + sumTime);
        return results;
    }

    public float[] kramerCalculate(double[][] values, double[] answers){
        float[] results = new float[answers.length];
        float maindeterminant = calculateDeterminant(values);

        for (int i = 0; i < values.length; i++){
            results[i] = calculateDeterminant(changeColumn(values , answers,i ))/maindeterminant;
        }
        return results;
    }

    public float calculateDeterminant(double[][] values){
        if (values.length == 2){
            return (float) (values[0][0]*values[1][1] - values[0][1]*values[1][0]);
        }else if (values.length == 1){ return (float) values[0][0];}
        double currentElement;
        double[][] newMatrix = new double[values.length -1][values.length - 1];
        float result = 0;
        for (int i = 0; i < values.length; i++){
            if (i%2 == 0){currentElement = values[i][0];
            }else {currentElement = (-1)*values[i][0];}

            if (currentElement == 0){continue;}

            for (int j = 0; j < newMatrix.length+1; j++){
                if (j == i){continue;}
                for (int k = 1; k < newMatrix.length+1; k++){
                    if (j > i){newMatrix[j-1][k-1] = values[j][k]; continue;}
                    newMatrix[j][k-1] = values[j][k];
                }
            }
            result += currentElement*calculateDeterminant(newMatrix);
        }
        return result;
    }

    public double[][] changeColumn(double[][] matrix, double[] column, int numOfColumn){
        double[][] newMatrix = new double[matrix.length][matrix.length];
        for(int i = 0; i < column.length; i++){
            for (int j = 0; j < column.length; j++){
                if (j == numOfColumn){newMatrix[i][j] = column[i];
                }else {newMatrix[i][j] = matrix[i][j];}
            }
        }
        return newMatrix;
    }
}
