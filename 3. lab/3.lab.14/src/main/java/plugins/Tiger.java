package plugins;

import model.Animal;

/**
 * @author Antonio Bukovac
 */
public class Tiger extends Animal {

    private String name;

    public Tiger(String name) {
        this.name = name;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String greet() {
        return "Bok ja sam tigar";
    }

    @Override
    public String menu() {
        return "Volim takine";
    }

}
