import java.util.Scanner;
import java.util.Random;

class PasswordGenerator {
    public static void main(String[] args) {
        int length = getLength();
        int complex = getComplexity();
        String password = createPassword(complex,length);
        System.out.println("A viable password is: " + password);

        
    }
    public static int getLength() throws NumberFormatException{
        Scanner scanner = new Scanner(System.in);
        System.out.println("How long do you want the password to be?");
        int length = scanner.nextInt();
        return length;
    }
    public static int getComplexity(){
        int complex;
        do { 
            System.out.println("How complex do you want the password to be?\n1,2 or 3 in Ascending order");
            Scanner scanner = new Scanner(System.in);
            complex = scanner.nextInt();
        } while (complex != 1 && complex != 2 && complex != 3);
        return complex;
    }

    public static String createPassword(int complex, int length){
        Random random = new Random();
        String password = "";
        int offset = 0;
        switch (complex) {
            case 1:
                offset = 97;
                break;
            case 2:
                offset = 65;
                break;
            case 3:
                offset = 33;
                break;
            default:
                System.out.println("You somehow broke the program");
                break;
        }

        for(int i = 0; i < length; i++) {
            char x = (char) (random.nextInt(127-offset) + offset);
            password += x;

        }
        return password; 

    }
}
