package bullscows;

import java.util.*;

public class Main {
    static Scanner scanner = new Scanner(System.in);

    static String numbers = "0123456789";
    static String alpha = "abcdefghijklmnopqrstuvwxyz";
    static List<String> list = new ArrayList<>();

    public static void main(String[] args) {
        initializeList();
        System.out.println("Please, enter the secret code's length:");
        String digit = scanner.nextLine();
        int digits = 0;
        try {

            try {
                digits = Integer.parseInt(digit);
            } catch (IllegalArgumentException exception) {
                throw new NotValidNumberFormatException(digit);
            }
            System.out.println("Input the number of possible symbols in the code:");
            int possibleSymbols = scanner.nextInt();
            if (digits > possibleSymbols) {
                throw new NotValidNumberFormatException(digits, possibleSymbols);
            }
            if (possibleSymbols > 36) {
                throw new IllegalArgumentException("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
            } else if (possibleSymbols <= 0) {
                throw new IllegalArgumentException("Error: minimum number of possible symbols in the code is 1 (0-0).");
            }

            if (digits <= 0) {
                throw new IllegalArgumentException("Error: minimum number of possible digit is 1.");
            }
            String secretNumber = generateSecretNumber(digits, possibleSymbols);
            showSecretMessage(possibleSymbols, digits);
            challengeSecret(secretNumber);
            //   System.out.printf("The random secret number is %d%n", secretNumber);
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void initializeList() {
        for (String elem : numbers.split("")) {
            list.add(elem);
        }

        for (String elem : alpha.split("")) {
            list.add(elem);
        }
    }

    private static void showSecretMessage(int possibleSymbols, int digits) {
        char firstNo = '0';
        char lastNo = '9';
        char firstChar = 'a';
        char lastChar = 'z';
        if (possibleSymbols <= numbers.length()) {
            lastNo = numbers.charAt(possibleSymbols - 1);
            firstChar = ' ';
            lastChar = ' ';
        } else if (possibleSymbols < numbers.length() + alpha.length()) {
            lastChar = alpha.charAt(possibleSymbols - numbers.length() - 1);
        }
        StringBuilder lengthOfSecret = new StringBuilder("");
        while (digits-- > 0) {
            lengthOfSecret.append("*");
        }


        System.out.printf("The secret is prepared: %s (%c-%c%s%s%s%s).%n", lengthOfSecret, firstNo, lastNo, firstChar != (char) 32 ? ", " : "", firstChar != (char) 32 ? firstChar : "", firstChar != (char) 32 ? "-" : "", lastChar != (char) 32 ? lastChar : "");
        System.out.println("Okay, let's start a game!");
    }

    private static void challengeSecret(String secretNumber) {
        StringBuilder secretNumberText = new StringBuilder(secretNumber);
        int counter = 1;
        boolean isGuessed = false;
        while (!isGuessed) {
            System.out.printf("Turn %d:%n", counter++);
            String guessNumber = scanner.next();

            int bulls = 0;
            int cows = 0;

            for (int i = 0; i < secretNumberText.length(); i++) {
                if (secretNumberText.charAt(i) == guessNumber.charAt(i)) {
                    bulls++;
                } else if (secretNumberText.indexOf(String.valueOf(guessNumber.charAt(i))) >= 0) {
                    cows++;
                }
            }
            if (cows == bulls && bulls == 0) {
                print("None", secretNumberText.toString());
            } else {
                String text = String.format("%s%s%s", bulls > 0 ? bulls + " bull(s)" : "", bulls > 0 && cows > 0 ? " and " : "", cows > 0 ? cows + " cow(s)" : "");
                print(text, secretNumberText.toString());
            }
            isGuessed = bulls == secretNumberText.length();
        }
        System.out.println("Congratulations! You guessed the secret code.");

    }

    private static void print(String txt, String secretNumberText) {
        System.out.printf("Grade: %s.%n", txt);//The secret code is %s", txt, secretNumberText
    }

    private static String generateSecretNumber(int digits, int possibleSymbols) {
        Random rnd = new Random();
        StringBuilder messages = new StringBuilder(numbers);
        messages.append(alpha);
        if (digits < 0 || digits > 10) {
            throw new IllegalArgumentException("Error: can't generate a secret number with a length of " + digits + " because there aren't enough unique digits.\n");
        }
        String secret = "";
        while (secret.length() < digits) {
            int randomNumber = rnd.nextInt(possibleSymbols);//(int) (Math.random() * Math.pow(digits, 10));
            String character = list.get(randomNumber);
            if (secret.equals("") && character.equals("0")) {
                continue;
            }
            if (secret.indexOf(character) < 0) {
                secret += character;
            }
        }
        return secret;
    }
}
