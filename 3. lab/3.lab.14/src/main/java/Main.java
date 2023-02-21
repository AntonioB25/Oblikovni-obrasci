import model.Animal;

/**
 * @author Antonio Bukovac
 */
public class Main {

    public static void main(String[] args) {
        Animal parrot;
        Animal tiger;

        try {
            parrot = AnimalFactory.newInstance("Parrot", "ChiChi");
            System.out.println(parrot.name() + " kaže:" +parrot.greet());
            System.out.println(parrot.menu());
            System.out.println(parrot.name());

            tiger = AnimalFactory.newInstance("Tiger", "Tigger");
            System.out.println(tiger.name() + " kaže:" + tiger.greet());
            System.out.println(tiger.menu());
            System.out.println(tiger.name());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
