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

   private Person person = new Person();


    @InjectMocks
    private Person firstPerson;

    @InjectMocks
    private Person secondPerson;

    @Spy
    private List<Person> family;

    @BeforeEach
    public void setupTest() {
        person = new Person() {
            @Override
            public LocalDate getNow() {
                int year = 2020;
                int month = 8;
                int dayOfMonth = 7;

                return LocalDate.of(year, month, dayOfMonth);
            }
        };

    }

    @Test
    public void getName_firstNameJonLastNameSnow_jonSnow() {
        person.setName("Jon");
        person.setLastName("Snow");
        Assertions.assertEquals("Jon Snow",person.getName());
    }

    @Test
    public void getName_firstNameJonNoLastName_jon() {
        person.setName("Jon");
        person.getName();
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
        int year = 1991;
        int month = 7;
        int dayOfMonth = 19;

        LocalDate localDate = LocalDate.of(year, month, dayOfMonth);
        person.setBirthday(localDate);
        boolean actualResult = person.isBirthdayToday();
        Assertions.assertFalse(actualResult);

    }

    @Test
    public void isBirthdayToday_sameMonthDifferentDay_false() {
        int year = 1991;
        int month = 8;
        int dayOfMonth = 6;

        LocalDate localDate = LocalDate.of(year, month, dayOfMonth);
        person.setBirthday(localDate);
        boolean actualResult = person.isBirthdayToday();
        Assertions.assertFalse(actualResult);
    }

    @Test
    public void isBirthdayToday_sameMonthAndDay_true() {
        int year = 1991;
        int month = 8;
        int dayOfMonth = 7;

        LocalDate localDate = LocalDate.of(year, month, dayOfMonth);
        person.setBirthday(localDate);
        boolean actualResult = person.isBirthdayToday();
        Assertions.assertTrue(actualResult);
    }

    @Test
    public void addToFamily_somePerson_familyHasNewMember() {
        firstPerson.addToFamily(secondPerson);
        verify(family, times(1)).add(secondPerson);
    }

    @Test
    public void addToFamily_somePerson_personAddedAlsoHasItsFamilyUpdated() {
        firstPerson.addToFamily(secondPerson);
        verify(family, times(1)).add(firstPerson);
    }

    @Test
    public void isFamily_nonRelativePerson_false() {
        when(family.contains(null)).thenReturn(false);
        Assertions.assertFalse(firstPerson.isFamily(null));
    }

    @Test
    public void isFamily_relativePerson_true() {
        when(family.contains(null)).thenReturn(true);
        Assertions.assertTrue(firstPerson.isFamily(null));
    }
}
