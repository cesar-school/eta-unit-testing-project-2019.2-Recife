

import java.time.LocalDate;

import school.cesar.eta.unit.Person;

public interface PersonServiceInterface {
	void addToFamily(Person person);
	
	 boolean isFamily(Person person);
	 
	 public LocalDate getNow();
}
