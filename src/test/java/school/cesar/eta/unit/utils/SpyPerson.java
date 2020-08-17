package school.cesar.eta.unit.utils;

import school.cesar.eta.unit.Person;

import java.util.ArrayList;
import java.util.List;

public class SpyPerson extends Person {

    public List<Person> family = new ArrayList<Person>();

    @Override
    public void addToFamily(Person person) {
        this.family.add(person);
        super.addToFamily(person);
    }
}
