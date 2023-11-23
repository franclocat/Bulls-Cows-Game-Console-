package bullsCows;

import java.util.*;

public class BullsAndCows {

    public static void main(String[] args) {
        boolean end = false;
        int turnCounter = 1;

        StringBuilder guess = new StringBuilder();
        String grade = "";
        int length = getLength();
        int possibleSymbols = getPossibleSymbols(length);
        StringBuilder secretCode = new StringBuilder(getCode(length, possibleSymbols));
        System.out.println("Okay, let's start a game!");

        while (!end) {
            System.out.println("Turn " + turnCounter + "!");
            turnCounter++;
            guess = getGuess(length);
            grade = getGrade(secretCode, guess, length);
            System.out.println("Grade: " + grade);
            if (grade.equals(length + " bulls")) {
                System.out.println("Congratulations! You guessed the secret code.");
                end = true;
            }
        }
    }

    //working... uses the given length and amount of symbols to create a code and return it
    public static StringBuilder getCode(int length, int possibleSymbols) {
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        Set<Integer> uniqueNumbers = new HashSet<Integer>();
        Set<Character> uniqueLetters = new HashSet<Character>();

        while (code.length() < length) {
            if (possibleSymbols <= 10) {
                int randomNumber = random.nextInt(possibleSymbols);
                if (!uniqueNumbers.contains(randomNumber)) {
                    uniqueNumbers.add(randomNumber);
                    code.append(String.valueOf(randomNumber));
                }
            } else if (possibleSymbols <= 36) {
                int randomNumber = random.nextInt(48, 97 + (possibleSymbols - 10));
                if (!uniqueLetters.contains((char) (randomNumber)) && (randomNumber > 96 || randomNumber < 58)) {
                    uniqueLetters.add((char) (randomNumber));
                    code.append((char) (randomNumber));
                }
            }
        }
        System.out.println(code);
        return code;
    }

    //working... Prompts the user for the wanted code length and checks for its validity and returns it if valid
    public static int getLength() {
        Scanner scanner = new Scanner(System.in);
        boolean valid = false;
        int codeGivenLength = 0;

        while (!valid) {
            System.out.println("Please, enter the secret code's length:");
            if (!scanner.hasNextInt()) {
                System.out.println("Error: The input has to be a number!\n");
                scanner.next();
            } else if (scanner.hasNextInt()) {
                codeGivenLength = scanner.nextInt();
                if (codeGivenLength > 36 || codeGivenLength < 1 ) {
                    System.out.println("Error: The number can't be bigger than 36 or less than 1!\n");
                } else {
                    valid = true;
                }
            }
        }
        return codeGivenLength;
    }

    //working... Ask for the amount of possible symbols, prints the range of possible symbols and returns the amount given
    public static int getPossibleSymbols(int length) {
        Scanner scanner = new Scanner(System.in);
        int possibleSymbols = 0;
        boolean valid = false;
        while (!valid) {
            System.out.println("Input the number of possible symbols in the code:");
            if (!scanner.hasNextInt()) {
                System.out.println("Error: The input has to be a number between 1-36!\n");
                scanner.next();
            } else{
                possibleSymbols = scanner.nextInt();
                if (possibleSymbols < length) {
                    System.out.println("Error: The amount of possible symbols has to be at least " + length +"!\n");
                } else if (possibleSymbols > 36) {
                    System.out.println("Error: The maximum amount of symbols is 36!\n");
                } else {
                    valid = true;
                }
            }
        }
        System.out.print("The secret code is prepared: ");
        for (int i = 0; i < length; i++) {
            System.out.print("*");
        }
        if (possibleSymbols <= 10 && possibleSymbols > 1) {
            System.out.println(" (0-" + String.valueOf(possibleSymbols - 1) + ").");
        } else if (possibleSymbols > 10) {
            String s = String.valueOf((char) (96 + (possibleSymbols - 10)));
            System.out.println(" (0-9, a-" + s + ").");
        }
        return possibleSymbols;
    }

    //working... prompts for a guess and analyzes the input for errors such as correct length and no 0 at the front
    public static StringBuilder getGuess(int codeGivenLength) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder guess = new StringBuilder();
        Set<Character> uniqueCharactersInGuess = new HashSet<Character>();
        boolean valid = false;

        while (!valid) {
            System.out.println("Make a guess:");
            guess.replace(0, guess.length(),String.valueOf(scanner.next()));
            for (int i = 0; i < guess.length(); i++) {
                uniqueCharactersInGuess.add(guess.charAt(i));;
            }
            if (uniqueCharactersInGuess.size() < codeGivenLength) {
                System.out.println("Error: The guess has to contain " + codeGivenLength +
                        " unique digits and can't start with 0!");
                continue;
            }
            if (guess.length() > codeGivenLength || guess.length() < codeGivenLength) {
                System.out.println("Error: The guess has to be " + codeGivenLength + " digits long!");
            } else {
                valid = true;
            }
        }
        return guess;
    }

    public static String getGrade(StringBuilder secretCode, StringBuilder guess, int length) {
        String result = "";
        int bulls = 0;
        int cows = 0;

        for (int i = 0; i < secretCode.length(); i++) {
            if (secretCode.charAt(i) == guess.charAt(i)) {
                bulls++;
            } else if (secretCode.toString().contains(String.valueOf(guess.charAt(i)))) {
                cows++;
            }
        }
        if (bulls == 0 && cows == 0) {
            result = "None";
        } else if (bulls == length) {
            result = bulls + " bulls";
        } else {
            result = bulls + " bull(s) & " + cows + " cow(s)";
        }
        return result;
    }
}