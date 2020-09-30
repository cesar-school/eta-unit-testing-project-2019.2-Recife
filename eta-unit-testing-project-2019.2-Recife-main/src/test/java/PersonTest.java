

import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
//import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import school.cesar.eta.unit.Person;

@ExtendWith(MockitoExtension.class)

public class PersonTest {
	
	private Person person;
	
	@InjectMocks
	Person somePerson;
	
	@Mock
	PersonServiceInterface service;
	
	@BeforeEach
    public void setup() {
		person = new Person();
	}
	
    @Test
    public void getName_firstNameJonLastNameSnow_jonSnow() {
    	person.setName("Jhon");
    	person.setLastName("Snow");
    	Assertions.assertEquals("JhonSnow",person.getName());
    	
    }

    @Test
    public void getName_firstNameJonNoLastName_jon() {
    	person.setName("Jhon");
    	Assertions.assertEquals("Jhon",person.getName());
    }

    @Test
    public void getName_noFirstNameLastNameSnow_snow() {
    	person.setLastName("Snow");
    	Assertions.assertEquals("Snow",person.getName());
    }

    @Test
    public void getName_noFirstNameNorLastName_throwsException() {
    	person.setName(null);
    	person.setLastName(null);
    	
    	Assertions.assertThrows(RuntimeException.class, () -> {
    		person.getName();
    	  });
    }

    @Test
    public void isBirthdayToday_differentMonthAndDay_false() {     
        PersonService service = new PersonService();
        person.setBirthday(service.getNow());
        boolean result = person.isBirthdayToday();
        Assertions.assertFalse(result);
       
    }

    @Test
    public void isBirthdayToday_sameMonthDifferentDay_false() {
        person = new Person(){
            
            @Override
            public LocalDate getNow()
            {  
                Date date = new Date();
                LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                int year = localDate.getYear();
                int month = localDate.getMonthValue();
                int diferentDay = localDate.getDayOfMonth();
                return LocalDate.of(year, month, diferentDay-5);
            }
            
            
        };

        person.getNow();
        boolean result = person.isBirthdayToday();
        Assertions.assertFalse(result);
    }

    @Test
    public void isBirthdayToday_sameMonthAndDay_true() {
    	person.setBirthday(LocalDate.now());
        boolean result = person.isBirthdayToday();
        Assertions.assertTrue(result);
    }

    @Test
    public void addToFamily_somePerson_familyHasNewMember() {
    	
    	PersonService service = new PersonService() {
    		@Override
    		public boolean isFamily(Person person) {
    			return true;
    		}
    	};
    	service.addToFamily(person);
    	Assertions.assertTrue(person.isFamily(person));
    	
    }

    @Test
    public void addToFamily_somePerson_personAddedAlsoHasItsFamilyUpdated() {
        fail();
    }

    @Test
    public void isFamily_nonRelativePerson_false() {
       person = new Person() {
    	   @Override
    	   public boolean isFamily(Person person) {
    	        return false;
    	    }
       };
       Assertions.assertFalse(person.isFamily(person));
    }

    @Test
    public void isFamily_relativePerson_true() {
    	FakePerson relativePerson = new FakePerson();
        boolean result = relativePerson.isFamily(person);
        Assertions.assertTrue(result);
    }
}
