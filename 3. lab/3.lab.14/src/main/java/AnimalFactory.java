import model.Animal;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author Antonio Bukovac
 */
public class AnimalFactory {

    public static Animal newInstance(String animalKind, String name)
        throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException,
        InvocationTargetException {

        Class<Animal> clazz;
        clazz = (Class<Animal>)Class.forName("plugins." + animalKind);
        Constructor<?> ctr = clazz.getConstructor(String.class);
        return (Animal)ctr.newInstance(name);
    }

}
