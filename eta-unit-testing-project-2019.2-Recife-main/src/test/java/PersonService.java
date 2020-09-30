

import java.time.LocalDate;

import school.cesar.eta.unit.Person;

public class PersonService implements PersonServiceInterface{

	@Override
	public void addToFamily(Person person) {
		
		
	}

	@Override
	public boolean isFamily(Person person) {
		return true;
	}

    @Override
    public LocalDate getNow()
    {  
        return LocalDate.parse("2020-01-01");
    }
	

}
