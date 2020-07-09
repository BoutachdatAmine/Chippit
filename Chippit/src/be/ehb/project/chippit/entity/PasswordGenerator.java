package be.ehb.project.chippit.entity;
import java.util.Arrays;
import java.util.Collections;
import java.security.SecureRandom;
import java.security.SecureRandom;
import java.util.List;
import java.util.stream.Collectors;


public class PasswordGenerator {
    private static final String letters = "abcdefghijklmnopqrstuvwxyz";
    private static final String  capitalLetters= letters.toUpperCase();
    private static final String numbers = "0123456789";
    private static final String characters = "!@#$%&*()_+-=[]?";
    private static final int passwordSize = 13;

    private static final String password= capitalLetters+numbers+characters+letters;

    //Shuffle allows for more random
    private static final String password_shuffle=shuffleString(password);
    //safe RNG genarator
    private static SecureRandom random =new SecureRandom();


    public static String shuffleString(String password) {
        //Transform a password in an array
        List<String> letters=Arrays.asList(password.split(""));
        //Shuffle values in array
        Collections.shuffle(letters);

        //Join the array in a string
        return letters.stream().collect(Collectors.joining());
    }

    public static String generateRandomPassword(){

        //Construct a string builder with an initial capacity of 13
        StringBuilder sb = new StringBuilder(passwordSize);
        for (int i = 0; i < passwordSize; i++) {

            int randomCharAt = random.nextInt(password_shuffle.length());
            char randomChar = password_shuffle.charAt(randomCharAt);
            sb.append(randomChar);

        }

        return sb.toString();
    }
}

