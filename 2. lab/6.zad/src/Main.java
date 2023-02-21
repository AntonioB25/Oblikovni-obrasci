/**
 * @author Antonio Bukovac
 */
public class Main {

    public static void main(String[] args) {
        Sheet s = new Sheet(5, 5);

        s.set("A1", "2");
        s.set("A2", "5");
        s.set("A3", "A1+A2");
        s.printSheet();

        s.set("A1", "4");
        s.set("A4", "A1+a3");
        s.printSheet();
        s.set("d3", "a1+a2");
        s.printSheet();
        s.set("a1", "785");
        s.printSheet();
    }

}
