package Teor_ver.sixthLab;

import java.util.HashMap;
import java.util.Random;

public class State {
    int num;
    float time = 0;
    private HashMap<Integer, Float> states = new HashMap<>();

    public State(int num, float [][] vars) {
        this.num = num;
        for (int i = 0; i < vars.length; i++){
            states.put((int)vars[i][0], vars[i][1]);
        }
    }

    public float getTime() {
        return time;
    }

    public int calculateTimeAndGo(){
        Random random = new Random();
        float previousTime = 5000000;
        int currentState = 0;
        for (Integer key : states.keySet()){
            float currentTime = (float)(-(1/states.get(key))*Math.log(random.nextFloat()));
            if (previousTime > currentTime){
                currentState = key;
                previousTime = currentTime;
            }
        }
        time += previousTime;
        return currentState;
    }

    public double calculateOutlines(){
        double sum = 0;
        for (double i : states.values()){
            sum+=i;
        }
        return sum;
    }

    public double getInner(Integer numState){
        return states.get(numState);
    }

}
