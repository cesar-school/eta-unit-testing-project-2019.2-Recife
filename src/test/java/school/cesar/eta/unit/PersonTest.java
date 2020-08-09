package school.cesar.eta.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class PersonTest {

    private Person person;

    @InjectMocks
    private Person person1;

    @InjectMocks
    private Person person2;

    @Spy
    public List<Person> family;

    @BeforeEach
    public void setupTest() {
        person = new Person() {
            @Override
            public LocalDate getNow() {
                return LocalDate.of(2020, 8, 8);
            }
        };
    }

    @Test
    public void getName_firstNameJonLastNameSnow_jonSnow() {

        String expected = "JonSnow";
        person.setName("Jon");
        person.setLastName("Snow");
        Assertions.assertEquals(expected, person.getName());
    }

    @Test
    public void getName_firstNameJonNoLastName_jon() {

        String expected = "Jon";
        person.setName("Jon");
        Assertions.assertEquals(expected, person.getName());
    }

    @Test
    public void getName_noFirstNameLastNameSnow_snow() {

        String expected = "Snow";
        person.setLastName("Snow");
        Assertions.assertEquals(expected, person.getName());
    }

    @Test
    public void getName_noFirstNameNorLastName_throwsException() {

        Exception exception = Assertions.assertThrows(RuntimeException.class, () -> person.getName());
        String expected = "Name must be filled";
        Assertions.assertEquals(expected, exception.getMessage());
    }

    @Test
    public void isBirthdayToday_differentMonthAndDay_false() {

        LocalDate birthday = LocalDate.of(1993, 7, 7);
        person.setBirthday(birthday);
        Assertions.assertFalse(person.isBirthdayToday());
    }

    @Test
    public void isBirthdayToday_sameMonthDifferentDay_false() {

        LocalDate birthday = LocalDate.of(1993, 8, 7);
        person.setBirthday(birthday);
        Assertions.assertFalse(person.isBirthdayToday());
    }

    @Test
    public void isBirthdayToday_sameMonthAndDay_true() {

        LocalDate birthday = LocalDate.of(1993, 8, 8);
        person.setBirthday(birthday);
        Assertions.assertTrue(person.isBirthdayToday());
    }

    @Test
    public void addToFamily_somePerson_familyHasNewMember() {

        PersonSpy personSpy = new PersonSpy();
        Person somePerson = new Person();

        personSpy.addToFamily(somePerson);
        Assertions.assertEquals(1, personSpy.familyCount);
    }

    @Test
    public void addToFamily_somePerson_personAddedAlsoHasItsFamilyUpdated() {

        person1.addToFamily(person2);
        verify(family, times(1)).add(person1);
    }

    @Test
    public void isFamily_nonRelativePerson_false() {

        Person nonRelativePerson = new Person();
        person.isFamily(nonRelativePerson);
    }

    @Test
    public void isFamily_relativePerson_true() {

        Person relativePerson = new Person();
        person.addToFamily(relativePerson);
        person.isFamily(relativePerson);
    }
}
