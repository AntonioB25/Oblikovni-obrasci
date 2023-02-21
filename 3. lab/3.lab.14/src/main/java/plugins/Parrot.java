package plugins;

import model.Animal;

/**
 * @author Antonio Bukovac
 */
public class Parrot extends Animal {

    private String name;

    public Parrot(String name) {
        this.name = name;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String greet() {
        return "Bok ja sam papiga";
    }

    @Override
    public String menu() {
        return "Volim sjemenke suncokreta";
    }

}
