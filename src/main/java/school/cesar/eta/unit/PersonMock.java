package school.cesar.eta.unit;

import java.util.ArrayList;
import java.util.List;

public class PersonMock extends Person {

	public List<Person> family = new ArrayList<Person>();

	@Override
	public void addToFamily(Person person) {
		this.family.add(person);
		super.addToFamily(person);
	}

}
