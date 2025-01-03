import java.util.Scanner;

public class Project3 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Taking integer input
        System.out.print("Enter an integer: ");
        int userInputInt = scanner.nextInt();

        // Taking string input
        System.out.print("Enter a string: ");
        String userInputString = scanner.next();

        // Performing arithmetic operation
        int result = userInputInt * 2;

        // Printing the result of arithmetic operation
        System.out.println("Double of your input integer: " + result);

        // Printing the string input
        System.out.println("You entered: " + userInputString);
    }
}
