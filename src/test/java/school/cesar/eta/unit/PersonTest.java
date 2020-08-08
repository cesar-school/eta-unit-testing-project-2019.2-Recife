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
                return LocalDate.now();
            }
        };

    }

    @Test
    public void getName_firstNameJonLastNameSnow_jonSnow() {
        person.setName("Jon");
        person.setLastName("Snow");
        Assertions.assertEquals("Jon",person.getFirstName());
        Assertions.assertEquals("Snow",person.getLastName());
        Assertions.assertEquals("JonSnow",person.getName());
    }

    @Test
    public void getName_firstNameJonNoLastName_jon() {
        person.setName("Jon");
        Assertions.assertEquals("Jon",person.getFirstName());
        Assertions.assertEquals("Jon",person.getName());
    }

    @Test
    public void getName_noFirstNameLastNameSnow_snow() {
        person.setLastName("Snow");
        Assertions.assertEquals("Snow",person.getLastName());
        Assertions.assertEquals("Snow",person.getName());
    }

    @Test
    public void getName_noFirstNameNorLastName_throwsException() {
        Assertions.assertThrows(RuntimeException.class, () -> person.getName());
    }

    @Test
    public void isBirthdayToday_differentMonthAndDay_false() {
        person.setBirthday(LocalDate.of(2020,7 ,19));
        Assertions.assertNotEquals(person.getBirthday().getMonth(),LocalDate.now().getMonth());
        Assertions.assertNotEquals(person.getBirthday().getDayOfMonth(),LocalDate.now().getDayOfMonth());
        Assertions.assertFalse(person.isBirthdayToday());

    }

    @Test
    public void isBirthdayToday_sameMonthDifferentDay_false() {
        person.setBirthday(LocalDate.of(2020, 8, 7));
        Assertions.assertEquals(person.getBirthday().getMonth(),LocalDate.now().getMonth());
        Assertions.assertNotEquals(person.getBirthday().getDayOfMonth(),LocalDate.now().getDayOfMonth());
        Assertions.assertFalse(person.isBirthdayToday());
    }

    @Test
    public void isBirthdayToday_sameMonthAndDay_true() {
        person.setBirthday(LocalDate.now());
        Assertions.assertEquals(person.getBirthday().getMonth(),LocalDate.now().getMonth());
        Assertions.assertEquals(person.getBirthday().getDayOfMonth(),LocalDate.now().getDayOfMonth());
        Assertions.assertTrue(person.isBirthdayToday());
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
