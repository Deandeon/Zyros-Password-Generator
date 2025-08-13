/*
 * Code Written by
 * @David Kweku Amissah Orhin
 *
 *
 *  This is a program that generates random passwords
 */
// Importing the Necessary libraries
import java.util.Random;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;


// Main class for generating passwords
public class passwordGenerator{
    static Scanner input = new Scanner(System.in);
    // Main method to execute the program
    public static void main(String[] args){

        Menu();
    }

    // Method to generate a random password
    public static String generatePassword(
            int minPassLength,
            int minUppercase,
            int minNumOfDigits,
            int minNumOfSpecials,
            String allowedSpecials) {

        Random random = new Random();
        String password;

        do {
            char[] randomPass = new char[minPassLength];

            // Uppercase letters
            for (int i = 0; i < minUppercase; i++) {
                randomPass[i] = randomGenUppercaseChar();
            }

            // Digits
            for (int i = minUppercase; i < minUppercase + minNumOfDigits; i++) {
                randomPass[i] = randomNumGen();
            }

            // Special characters
            int specialLength = allowedSpecials.length();
            for (int i = minUppercase + minNumOfDigits;
                 i < minUppercase + minNumOfDigits + minNumOfSpecials;
                 i++) {
                int randIndex = random.nextInt(specialLength);
                randomPass[i] = allowedSpecials.charAt(randIndex);
            }

            // Remaining lowercase letters
            for (int i = minUppercase + minNumOfDigits + minNumOfSpecials;
                 i < minPassLength; i++) {
                randomPass[i] = randomGenLetterChar('a', 'z');
            }

            // Shuffle the array
            for (int i = minPassLength - 1; i > 0; i--) {
                int index = random.nextInt(i + 1);
                char temp = randomPass[index];
                randomPass[index] = randomPass[i];
                randomPass[i] = temp;
            }

            password = new String(randomPass);

        } while (!checkPassword(password, minPassLength, minUppercase, minNumOfDigits, minNumOfSpecials, allowedSpecials));

        return password;
    }


    // Method to check if password meets requirements
    public static Boolean checkPassword(String password,
                                        int minPassLength,
                                        int minUppercase,
                                        int minNumOfDigits,
                                        int minNumOfSpecials,
                                        String allowedSpecials) {

        // 1. Check overall length
        if (password.length() < minPassLength) {
            return false;
        }

        // Counters
        int uppercaseCount = 0;
        int digitCount = 0;
        int specialCount = 0;

        // 2. Count each type of character
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                uppercaseCount++;
            } else if (Character.isDigit(c)) {
                digitCount++;
            } else if (allowedSpecials.indexOf(c) != -1) {
                specialCount++;
            }
        }

        // 3. Validate counts against requirements
        boolean hasEnoughUppercase = uppercaseCount >= minUppercase;
        boolean hasEnoughDigits = digitCount >= minNumOfDigits;
        boolean hasEnoughSpecials = specialCount >= minNumOfSpecials;

        // 4. Return result
        return hasEnoughUppercase && hasEnoughDigits && hasEnoughSpecials;
    }

    // Method to generate a random lowercase character
    public static char randomGenLetterChar( int i, int j){
        return  (char)(i + Math.random() * (j - i + 1));
    }

    // Method to generate a random uppercase character
    public static char randomGenUppercaseChar(){
        return randomGenLetterChar('A','Z');

    }

    // Method to generate a random digit character
    public static char randomNumGen(){
        return (char) ('0' + (int) (

                Math.random() * 10));
    }

    // Method to return allowed special characters
    public static String randomSpecialchar(String allowedSpecials){
        return allowedSpecials;
    }

    public static String Menu(){
        String M1= "Please Indicate what you would like to do :";
        String M2 = "1.Generate Custom Password";
        String M3 = "2.View Saved passwords";
        String M4 = "3. Close Program";

        System.out.println(M1);
        System.out.println(M2);
        System.out.println(M3);
        System.out.println(M4);

        int menChoice = input.nextInt();

        if (menChoice == 1){
            // Special Characters Check
            String allowedSpecials;
            String validSpecials = "!@#$%^&*+=";
            while (true) {
                System.out.println("Enter allowed Special characters (only from " + validSpecials + "): ");
                allowedSpecials = input.next();
                boolean allValid = true;
                for (char c : allowedSpecials.toCharArray()) {
                    if (validSpecials.indexOf(c) == -1) {
                        allValid = false;
                        break;
                    }
                }
                if (allValid && !allowedSpecials.isEmpty()) {
                    break;
                }
                System.out.println("Error: Allowed specials can only contain characters from the lsit. Pleas try again" );
            }

            System.out.println("Enter minimum password length: ");
            int minPasslength = input.nextInt();

// Ensure the sum of components does not exceed the total length
            int minUppercase;
            while (true) {
                System.out.println("Enter minimum number of Uppercase Characters: ");
                minUppercase = input.nextInt();
                if (minUppercase <= minPasslength) break;
                System.out.println("Error: Uppercase count cannot be greater than password length!");
            }

            int minNumOfDigits;
            while (true) {
                System.out.println("Enter minimum number of digit characters: ");
                minNumOfDigits = input.nextInt();
                if (minUppercase + minNumOfDigits <= minPasslength) break;
                System.out.println("Error: Uppercase + Digits count cannot exceed password length!");
            }

            int minNumOfSpecials;
            while (true) {
                System.out.println("Enter minimum number of specials: ");
                minNumOfSpecials = input.nextInt();
                if (minUppercase + minNumOfDigits + minNumOfSpecials <= minPasslength) break;
                System.out.println("Error: Uppercase + Digits + Specials count cannot exceed password length!");
            }

            // Generating random password
            String randomPassword = generatePassword(
                    minPasslength,
                    minUppercase,
                    minNumOfDigits,
                    minNumOfSpecials,
                    allowedSpecials
            );

            // Checking if generated password meets requirements
            Boolean passTest = checkPassword(randomPassword,
                    minPasslength,
                    minUppercase,
                    minNumOfDigits,
                    minNumOfSpecials,
                    allowedSpecials);

            // Displaying generated password
            if (passTest) {

                System.out.println("Here is your randomly generated password");
                System.out.println(randomPassword);
            }

            else {
                System.out.println("The produced password does not meet the required conditions please try again");
            }




        }

        return null;
    }
}


