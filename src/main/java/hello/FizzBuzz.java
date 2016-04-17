package hello;

/**
 * Created by craigleclair on 2016-04-14.
 */
public class FizzBuzz {


    public void buzz(int n){
        for(int i = 1; i <= n; i++){
            if(i % 5 == 0 && i % 3 == 0){
                System.out.println("fizzbuzz");
                continue;
            }
            if(i % 3 == 0){
                System.out.println("fizz");
                continue;
            }
            if(i % 5 == 0){
                System.out.println("buzz");
                continue;
            }
            System.out.println(i);
        }
    }
}
