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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;


@ExtendWith(MockitoExtension.class)
public class PersonTest {


    private Person person;

    @Spy
    private List<Person> family;

    @InjectMocks
    private Person OnePerson;

    @InjectMocks
    private Person OtherDifferentPerson;


    @BeforeEach
    public void setup(){
        person = new Person(){
            @Override
            public LocalDate getNow(){
                return LocalDate.now();
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
        person.setBirthday(LocalDate.of(2021, 10, 28));
        Assertions.assertFalse(person.isBirthdayToday());

    }

    @Test
    public void isBirthdayToday_sameMonthDifferentDay_false() {
        person.setBirthday(LocalDate.of(2032, 8, 11));
        Assertions.assertFalse(person.isBirthdayToday());
    }

    @Test
    public void isBirthdayToday_sameMonthAndDay_true() {
        person.setBirthday(LocalDate.now());
        Assertions.assertTrue(person.isBirthdayToday());

    }

    @Test
    public void addToFamily_somePerson_familyHasNewMember() {
        OnePerson.addToFamily(OtherDifferentPerson);
        verify(family, times(1)).add(OtherDifferentPerson);
    }

    @Test
    public void addToFamily_somePerson_personAddedAlsoHasItsFamilyUpdated() {
        addToFamily_somePerson_familyHasNewMember();
        verify(family, times(1)).add(OtherDifferentPerson);
    }

    @Test
    public void isFamily_nonRelativePerson_false() {
        when(family.contains(null)).thenReturn(false);
        Assertions.assertFalse(OnePerson.isFamily(null));
    }

    @Test
    public void isFamily_relativePerson_true() {
        when(family.contains(null)).thenReturn(true);
        Assertions.assertTrue(OtherDifferentPerson.isFamily(null));
    }
}
