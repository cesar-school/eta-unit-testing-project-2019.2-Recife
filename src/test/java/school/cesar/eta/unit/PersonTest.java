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

import static org.mockito.Mockito.*;

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
    public void setup() {
        person = new Person() {
            @Override
            public LocalDate getNow() {
                return LocalDate.of(2020, 8, 9);
            }
        };
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
        Assertions.assertThrows(RuntimeException.class, () -> person.getName());
    }

    @Test
    public void isBirthdayToday_differentMonthAndDay_false() {
        LocalDate myBirthday = LocalDate.of(2020, 12, 4);
        person.setBirthday(myBirthday);
        Assertions.assertFalse(person.isBirthdayToday());
    }

    @Test
    public void isBirthdayToday_sameMonthDifferentDay_false() {
        LocalDate myBirthday = LocalDate.of(2020, 8, 4);
        person.setBirthday(myBirthday);
        Assertions.assertFalse(person.isBirthdayToday());
    }

    @Test
    public void isBirthdayToday_sameMonthAndDay_true() {
        LocalDate myBirthday = LocalDate.of(2020, 8, 9);
        person.setBirthday(myBirthday);
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

        when(family.contains(person2)).thenReturn(false);
        Assertions.assertFalse(person1.isFamily(person2));
    }

    @Test
    public void isFamily_relativePerson_true() {
        when(family.contains(person2)).thenReturn(true);
        Assertions.assertTrue(person1.isFamily(person2));
    }
}
