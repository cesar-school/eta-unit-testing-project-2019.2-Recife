package school.cesar.eta.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PersonTest {

    private Person person;

    @BeforeEach
    public void setupTest(){
        person = new Person();
    }

    @InjectMocks
    private Person personFamily1;

    @InjectMocks
    private Person personFamily2;

    @Mock
    private List<Person> family;

    public LocalDate differentDayDate (){
        LocalDate diffDate;
        if (LocalDate.now().getDayOfMonth() > 27){
            diffDate = LocalDate.now().minusDays(1);
            return diffDate;
        }
        diffDate = LocalDate.now().plusDays(1);
        return diffDate;
    }

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
        Assertions.assertThrows(RuntimeException.class,() -> person.getName());
    }

    @Test
    public void isBirthdayToday_differentMonthAndDay_false() {
        person.setBirthday(LocalDate.now().plusMonths(1).plusDays(2));

        Assertions.assertFalse(person.isBirthdayToday());
    }

    @Test
    public void isBirthdayToday_sameMonthDifferentDay_false() {
        person.setBirthday(differentDayDate());

        Assertions.assertFalse(person.isBirthdayToday());
    }

    @Test
    public void isBirthdayToday_sameMonthAndDay_true() {
        person.setBirthday(LocalDate.now());

        Assertions.assertTrue(person.isBirthdayToday());
    }

    @Test
    public void addToFamily_somePerson_familyHasNewMember() {
        personFamily1.addToFamily(personFamily2);

        verify(family, times(1)).add(personFamily2);
    }

    @Test
    public void addToFamily_somePerson_personAddedAlsoHasItsFamilyUpdated() {
        personFamily1.addToFamily(personFamily2);

        verify(family, times(1)).add(personFamily1);
    }

    @Test
    public void isFamily_nonRelativePerson_false() {
        when(family.contains(personFamily2)).thenReturn(false);

        Assertions.assertFalse(personFamily1.isFamily(personFamily2));
    }

    @Test
    public void isFamily_relativePerson_true() {
        when(family.contains(personFamily2)).thenReturn(true);

        Assertions.assertTrue(personFamily1.isFamily(personFamily2));
    }
}
