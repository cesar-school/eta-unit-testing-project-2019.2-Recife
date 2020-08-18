package school.cesar.eta.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;

import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
public class PersonTest {

    private Person person;

    @InjectMocks
    Person personOne = new Person();

    @InjectMocks
    Person personTwo = new Person();

    @Mock
    List<Person> family = new ArrayList<Person>();

    @BeforeEach
    public void  setupPerson(){
        person = new Person();
    }

    @Test
    public void getName_firstNameJonLastNameSnow_jonSnow(){
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
        Assertions.assertThrows(RuntimeException.class, () -> person.getName());
    }

    @Test
    public void isBirthdayToday_differentMonthAndDay_false() {
        person = new Person(){
            @Override
            public LocalDate getNow() {
                LocalDate dateNow = LocalDate.now();
                return LocalDate.of(dateNow.getYear(),dateNow.getMonthValue()+4,dateNow.getDayOfMonth()+3);
            }
        };
        person.setBirthday(LocalDate.now());
        Assertions.assertFalse(person.isBirthdayToday());
    }

    @Test
    public void isBirthdayToday_sameMonthDifferentDay_false() {
        person = new Person(){
            @Override
            public LocalDate getNow() {
                LocalDate dateNow = LocalDate.now();
                return LocalDate.of(dateNow.getYear(),dateNow.getMonthValue(),dateNow.getDayOfMonth()-6);
            }
        };
        person.setBirthday(LocalDate.now());
        Assertions.assertFalse(person.isBirthdayToday());
    }

    @Test
    public void isBirthdayToday_sameMonthAndDay_true() {
        person = new Person(){
            @Override
            public LocalDate getNow() {
                LocalDate dateNow = LocalDate.now();
                return LocalDate.of(dateNow.getYear(),dateNow.getMonthValue(),dateNow.getDayOfMonth());
            }
        };
        person.setBirthday(LocalDate.now());
        Assertions.assertTrue(person.isBirthdayToday());
    }

    @Test
    public void addToFamily_somePerson_familyHasNewMember() {
        Person somePerson = new Person();
        personOne.addToFamily(somePerson);
        verify(family,times(1)).add(somePerson);
    }

    @Test
    public void addToFamily_somePerson_personAddedAlsoHasItsFamilyUpdated() {
        personOne.addToFamily(personTwo);
        verify(family,times(1)).add(personOne);
    }

    @Test
    public void isFamily_nonRelativePerson_false() {
       when(family.contains(personTwo)).thenReturn(false);
       Assertions.assertFalse(personOne.isFamily(personTwo));
    }

    @Test
    public void isFamily_relativePerson_true() {
        when(family.contains(personTwo)).thenReturn(true);
        Assertions.assertTrue(personOne.isFamily(personTwo));
    }
}
