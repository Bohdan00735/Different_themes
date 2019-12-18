package Teor_ver.sixthLab;

public class StateParce {
    public State parseTableToState (float [] table){
        int number = 0;
        float[][] ways = new float[table.length-1][2];
        int counter = 0;
        for (int i = 0; i < table.length; i++) {
            if (table[i] == -1){
                number = i+1;
            }else {
                ways[counter] = new float[]{i+1, table[i]};
                counter++;
            }
        }
        return new State(number, ways);
    }
}
