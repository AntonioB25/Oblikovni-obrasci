package Sources;

import java.util.Scanner;

/**
 * @author Antonio Bukovac
 */
public class KeyboardSource implements Source {

    Scanner sc = new Scanner(System.in);

    @Override
    public int getNumber() {
        try {
            System.out.print("Unesite broj: ");
            return sc.nextInt();

        } catch (Exception e) {
            return -1;
        }
    }



}
