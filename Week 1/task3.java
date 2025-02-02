/*
 * Objective: Write a method that processes user input and handles exceptions appropriately.
    • Details: Implement a method processInput() that reads a numerical input from the user and calculates the reciprocal. Handle exceptions related to invalid input (e.g., non-numerical input, division by zero).
    • Functions to Implement:
        ◦ processInput(): Prompts user for a number, calculates the reciprocal, and handles any possible input errors or exceptions.
 */

import java.util.* ;

class reciprocal {
    private int val1, val2;

    public void processInput(){
        Scanner sc = new Scanner(System.in);

        try{
            System.out.println("Enter the numerator :");
            val1 = sc.nextInt();
            System.out.println("Enter the denominator :");
            val2 = sc.nextInt();

            if(val1 == 0 || val2 == 0) throw new ArithmeticException();
            float result = (float)val2/val1;


            System.out.println("The reciprocal of " + val1 +"/"+ val2 + " is " + val2 +"/"+ val1 + " which evaluates to "+ result);
        }

        catch(ArithmeticException e){
            System.out.println("Attempted division by zero, invalid input");
        }

        catch(InputMismatchException e){
            System.out.println("Non-int input detected, invalid input");
        }

        catch(Exception e){
            System.out.println("Arbitrary exception occured");
        }

        finally{
            sc.close();
        }
    }
}

public class task3 {
    public static void main(String[] args) {
        reciprocal obj = new reciprocal();
        obj.processInput();
    }
}
