package school.cesar.eta.unit;

public class SpyPerson extends Person{

    public int familyNumber = 0;

    @Override
    public void addToFamily(Person newMember){

        familyNumber++;
        super.addToFamily(newMember);

    }
}
