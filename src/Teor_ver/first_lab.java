package Teor_ver;

public class first_lab {
    public static void main(String[] args) {
        int calc = 0;
        for (int i = 0; i < 10000; i++){
            int first = i/1000;
            int second =  i/100 - first*10;
            int third = i/10 - (second*10 + first*100);
            int forth = i - (first*1000+ second*100 + third*10);

            if (first+second == third+forth ){
                System.out.printf("1 - %d, 2 - %d, 3 - %d, 4 - %d \n" ,first,second,third,forth);
                calc++;
            }

        }

        System.out.println("result = " + (float) calc/10000);
    }
}
