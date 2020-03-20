package MOPE.Lab3;

import static java.lang.Math.pow;

public class MatrixCalculator {
    double[][] matrix;

    double calculateDeterminant(){
        return calculateDeterminant(matrix);
    }

    double calculateDeterminant(double[][] matrix){
        // matrix should be square
        if (matrix.length == 1){
            return matrix[0][0];
        }
        double result = 0;
        for (int i = 0; i <matrix[0].length ; i++) {
            result += pow(-1,i)*matrix[0][i]*calculateDeterminant(cutSomeColumnAndRow(matrix,0,i));
        }
        return result;
    }

    double[][] cutSomeColumnAndRow(double[][] matrix, int row, int column){
        // matrix should be square
        double[][] doubles = new double[matrix.length-1][matrix[0].length-1];
        int rowToWrite = 0;
        int columnToWrite = 0;
        for (int i = 0; i < matrix.length; i++) {
            if (i != row){
                for (int j = 0; j <matrix[0].length ; j++) {
                    if (j != column){
                        doubles[rowToWrite][columnToWrite] = matrix[i][j];
                        columnToWrite++;
                    }
                }
                columnToWrite = 0;
                rowToWrite++;
            }
        }return doubles;
    }

    double sumOfDivisionInSquare(double[] first, double[] second){
        double result = 0;
        for (int i = 0; i < first.length; i++) {
            result+=pow((first[i]-second[i]),2);
        }return result;
    }

    double[][] formMatrixOfTheFourthOrder(double a11, double a12, double a13, double a14,
                                          double a21, double a22, double a23, double a24,
                                          double a31, double a32, double a33, double a34,
                                          double a41, double a42, double a43, double a44){
        matrix = new double[][]{{a11,a12,a13,a14},
                                {a21,a22,a23,a24},
                                {a31,a32,a33,a34},
                                {a41,a42,a43,a44}};
        return matrix;
    }

    double[][] changeSomeColumn(double[][] anyMatrix, double[] columnArray, int column){
        // length of column should == num of rows in matrix
        double[][] resultMatrix = copyMatrix(anyMatrix);
        for (int i = 0; i <anyMatrix.length ; i++) {
            resultMatrix[i][column] = columnArray[i];
        }return resultMatrix;
    }

    double[][] copyMatrix(double[][] matrix){
        double[][] result = new double[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            System.arraycopy(matrix[i], 0, result[i], 0, matrix[0].length);
        }return result;
    }

    double[][] changeSomeColmn(double[] columnArray, int column){
        return changeSomeColumn(matrix, columnArray,column);
    }

    public double calculateSumTwoArrayForEach(int[] first, double[] second){
        // look through all elem in both arrays and multiply elements with the same index, returns sum of it
        double result = 0;
        if (first.length == second.length){
            for (int i = 0; i < first.length; i++) {
                result += first[i]*second[i];
            }
        }
        return result;
    }


    int[] getColumn(int column, int[][] array){
        int[] result = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i][column];
        }return result;
    }

}
