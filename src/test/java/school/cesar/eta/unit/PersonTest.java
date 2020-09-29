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
    private Person personOne;
    @InjectMocks
    private Person personTwo;
    @Spy
    private List<Person> family;

    @BeforeEach
    public void setupTest() {
        person = new Person() {
            @Override
            public LocalDate getNow() {
                return LocalDate.of(2020, 9, 2);
            }
        };
    }

    @Test
    public void getName_firstNameJonLastNameSnow_jonSnow()  {
        person.setName("Jon");
        person.setLastName("Snow");
        Assertions.assertEquals("JonSnow", person.getName());
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
        Assertions.assertThrows(RuntimeException.class, () -> person.getName());
    }

    @Test
    public void isBirthdayToday_differentMonthAndDay_false() {
        LocalDate birthday = LocalDate.of(1988,8, 5);
        person.setBirthday(birthday);
        Assertions.assertFalse(person.isBirthdayToday());
    }

    @Test
    public void isBirthdayToday_sameMonthDifferentDay_false() {
        LocalDate birthday = LocalDate.of(1988,7, 2);
        person.setBirthday(birthday);
        Assertions.assertFalse(person.isBirthdayToday());
    }

    @Test
    public void isBirthdayToday_sameMonthAndDay_true() {
        LocalDate birthday = LocalDate.of(1988,9, 2);
        person.setBirthday(birthday);
        Assertions.assertTrue(person.isBirthdayToday());
    }

    @Test
    public void addToFamily_somePerson_familyHasNewMember() {
        personOne.addToFamily(personTwo);
        verify(family, times(1)).add(personTwo);
    }

    @Test
    public void addToFamily_somePerson_personAddedAlsoHasItsFamilyUpdated() {
        personOne.addToFamily(personTwo);
        verify(family, times(1)).add(personOne);
    }

    @Test
    public void isFamily_nonRelativePerson_false() {

        Assertions.assertFalse(person.isFamily(person));
    }

    @Test
    public void isFamily_relativePerson_true() {
        when(family.contains(null)).thenReturn(true);
        Assertions.assertTrue(personOne.isFamily(null));
    }
}
