import java.util.Scanner;

public class log {

    public static void main(String[] args){
        while (true){
            Scanner in = new Scanner(System.in);
            Double pi = in.nextDouble();
            System.out.println(count(pi));
        }
    }

    public static Double count(Double p){
        return p*Math.log(p)/Math.log(2);
    }
}
