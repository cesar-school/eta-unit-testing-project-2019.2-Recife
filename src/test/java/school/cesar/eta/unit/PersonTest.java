package school.cesar.eta.unit;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith({ MockitoExtension.class })
public class PersonTest {

	Person person;
	PersonMock personMock;
	int sameMonth = LocalDate.now().getMonthValue();
	int sameDay = LocalDate.now().getDayOfMonth();
	int differentMonth = LocalDate.now().getMonthValue() + 1;
	int differentDay = LocalDate.now().getDayOfMonth() + 1;

	@InjectMocks
	Person personFamily = new Person();

	@Mock
	List<Person> family = new ArrayList<Person>();

	@BeforeEach
	public void createPerson() {
		person = new Person();
	}

	@Test
	public void getName_firstNameJonLastNameSnow_jonSnow() {
		person.setName("Jon");
		person.setLastName("Snow");
		Assertions.assertEquals("JonSnow", person.getName());
	}

	@Test
	public void getName_firstNameJonNoLastName_jon() {
		person.setName("Jon");
		Assertions.assertEquals("Jon", person.getName());
	}

	@Test
	public void getName_noFirstNameLastNameSnow_snow() {
		person.setLastName("Snow");
		Assertions.assertEquals("Snow", person.getName());
	}

	@Test
	public void getName_noFirstNameNorLastName_throwsException() {
		Assertions.assertThrows(RuntimeException.class, () -> {
			person.getName();
		});
	}

	@Test
	public void isBirthdayToday_differentMonthAndDay_false() {
		person.setBirthday(LocalDate.of(2020, differentMonth, differentDay));
		Assertions.assertFalse(person.isBirthdayToday());
	}

	@Test
	public void isBirthdayToday_sameMonthDifferentDay_false() {
		person.setBirthday(LocalDate.of(2020, sameMonth, differentDay));
		Assertions.assertFalse(person.isBirthdayToday());
	}

	@Test
	public void isBirthdayToday_sameMonthAndDay_true() {
		person.setBirthday(LocalDate.of(2020, sameMonth, sameDay));
		Assertions.assertTrue(person.isBirthdayToday());
	}

	// teste extra para cobrir o metodo isBirthdayToday
	@Test
	public void isBirthdayToday_differentMonthSameDay_false() {
		person.setBirthday(LocalDate.of(2020, differentMonth, sameDay));
		Assertions.assertFalse(person.isBirthdayToday());
	}

	@Test
	public void addToFamily_somePerson_familyHasNewMember() {
		personMock = new PersonMock();
		personMock.addToFamily(personFamily);
		Assertions.assertTrue(personMock.family.contains(personFamily));
	}

	@Test
	public void addToFamily_somePerson_personAddedAlsoHasItsFamilyUpdated() {
		personMock = new PersonMock();
		personMock.addToFamily(personMock);
		Assertions.assertTrue(personMock.family.contains(personMock));
	}

	@Test
	public void isFamily_nonRelativePerson_false() {
		Person personNonRelative = new Person();
		Assertions.assertFalse(person.isFamily(personNonRelative));
	}

	@Test
	public void isFamily_relativePerson_true() {
		Person personRelative = new Person();
		person.addToFamily(personRelative);
		Assertions.assertTrue(person.isFamily(personRelative));
	}
}
