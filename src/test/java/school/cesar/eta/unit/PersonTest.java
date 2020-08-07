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
    private List<Person> family;

    @BeforeEach
    public void setupTest() {
        person = new Person() {
            @Override
            public LocalDate getNow() {
                return LocalDate.of(2021, 4, 19);
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
        Exception exception = Assertions.assertThrows(RuntimeException.class, () -> {
            person.getName();
        });

        String expectedMessage = "Name must be filled";
        String actualMessage = exception.getMessage();

        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void isBirthdayToday_differentMonthAndDay_false() {
        LocalDate birthday = LocalDate.of(1983,7, 10);

        person.setBirthday(birthday);

        Assertions.assertFalse(person.isBirthdayToday());
    }

    @Test
    public void isBirthdayToday_sameMonthDifferentDay_false() {
        LocalDate birthday = LocalDate.of(1983,4, 15);

        person.setBirthday(birthday);

        Assertions.assertFalse(person.isBirthdayToday());
    }

    @Test
    public void isBirthdayToday_sameMonthAndDay_true() {
        LocalDate birthday = LocalDate.of(1983,4, 19);

        person.setBirthday(birthday);

        Assertions.assertTrue(person.isBirthdayToday());
    }

    @Test
    public void addToFamily_somePerson_familyHasNewMember() {
        person1.addToFamily(person2);

        verify(family, times(1)).add(person2);
    }

    @Test
    public void addToFamily_somePerson_personAddedAlsoHasItsFamilyUpdated() {
        person1.addToFamily(person2);

        verify(family, times(1)).add(person1);
    }

    @Test
    public void isFamily_nonRelativePerson_false() {
        when(family.contains(null)).thenReturn(false);

        Assertions.assertFalse(person1.isFamily(null));
    }

    @Test
    public void isFamily_relativePerson_true() {
        when(family.contains(null)).thenReturn(true);

        Assertions.assertTrue(person1.isFamily(null));
    }
}