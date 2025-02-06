// this is the program of making simple calculator using switch case

import java.util.Scanner;

public class program {
    public static void main(String[]args){
        Scanner sc =new Scanner(System.in);

        int a, b, c;
        int sum, difference, divide, multiply;

        System.out.println("enter the first number: ");
        a = sc.nextInt();
        System.out.println("enter the second number: ");
        b = sc.nextInt();
        System.out.println("which operation do you want to apply 1 -> sum, 2 -> subtraction, 3 -> multiply, 4 -> divide: ");
        c = sc.nextInt();
        switch(c){
            case(1):
                sum = a + b;
                System.out.println("sum of two number is: " + sum);
                break;
            case(2):
                difference = a - b;
                System.out.println("subtraction of two number is: "+ difference);
                break;
            case(3):
                multiply = a * b;
                System.out.println("multiply of two number is "+multiply);
                break;
            case(4):
                divide = a / b;
                System.out.println("divide of two number is "+ divide);
                break;
            default:
                System.out.println("you have given the wrong operation number");
        }

        sc.close();

    }
}
