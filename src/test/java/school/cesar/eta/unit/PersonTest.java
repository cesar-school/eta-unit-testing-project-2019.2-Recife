package school.cesar.eta.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

public class PersonTest {

    private final Person person = new Person();

    @InjectMocks
    private Person personMock1;
    @InjectMocks
    private Person personMock2;
    @Spy
    public List<Person> family;

    private LocalDate birthday;
    Exception exception;
    Person newPerson = new Person();
    SpyPerson spyPerson = new SpyPerson();


    @Test
    public void getName_firstNameJonLastNameSnow_jonSnow() {

        person.setName("Jon");
        person.setLastName("Snow");
        Assertions.assertEquals("JonSnow",person.getName());

    }

    @Test
    public void getName_firstNameJonNoLastName_jon() {

        person.setName("Jon");
        Assertions.assertEquals("Jon",person.getName());

    }

    @Test
    public void getName_noFirstNameLastNameSnow_snow() {

        person.setLastName("Snow");
        Assertions.assertEquals("Snow",person.getName());

    }

    @Test
    public void getName_noFirstNameNorLastName_throwsException() {

        exception = Assertions.assertThrows(RuntimeException.class, () -> person.getName());
        String expected = "Name must be filled";
        Assertions.assertEquals(expected, exception.getMessage());

    }

    @Test
    public void isBirthdayToday_differentMonthAndDay_false() {

        birthday = LocalDate.of(1991, 9, 3);
        person.setBirthday(birthday);
        Assertions.assertFalse(person.isBirthdayToday());

    }

    @Test
    public void isBirthdayToday_sameMonthDifferentDay_false() {

        birthday = LocalDate.of(1991, 8, 3);
        person.setBirthday(birthday);
        Assertions.assertFalse(person.isBirthdayToday());

    }

    @Test
    public void isBirthdayToday_sameMonthAndDay_true() {

        person.setBirthday(LocalDate.now());
        Assertions.assertTrue(person.isBirthdayToday());

    }

    @Test
    public void addToFamily_somePerson_familyHasNewMember() {

        spyPerson.addToFamily(newPerson);
        Assertions.assertEquals(1, spyPerson.familyNumber);

    }

    @Test
    public void addToFamily_somePerson_personAddedAlsoHasItsFamilyUpdated() {

        personMock1.addToFamily(personMock2);
        verify(family, times(1)).add(personMock1);

    }

    @Test
    public void isFamily_nonRelativePerson_false() {

        when(family.contains(personMock2)).thenReturn(false);
        Assertions.assertFalse(personMock1.isFamily(personMock2));

    }

    @Test
    public void isFamily_relativePerson_true() {

        Person relative = new SpyPerson();
        when(family.contains(relative)).thenReturn(true);
        Assertions.assertTrue(personMock1.isFamily(relative));

    }
}
