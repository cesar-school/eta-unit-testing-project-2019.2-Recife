
package school.cesar.eta.unit;

public class PersonSpy extends Person{
    public int familyCount = 0;

    @Override
    public void addToFamily(Person person){
        familyCount++;
        super.addToFamily(person);
    }
}