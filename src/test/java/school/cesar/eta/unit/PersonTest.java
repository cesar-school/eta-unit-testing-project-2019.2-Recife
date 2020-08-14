package school.cesar.eta.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})


public class PersonTest {

    private Person person;

    @InjectMocks
    Person relativeMock = new Person();
    @InjectMocks
    Person personMock = new Person();

    @Mock
    List<Person> family = new ArrayList<Person>();


    @Test
    public void getName_firstNameJonLastNameSnow_jonSnow() {
        person = new Person();
        person.setName("Jon");
        person.setLastName("Snow");
        String expected = "JonSnow";

        Assertions.assertEquals(expected,person.getName());
    }

    @Test
    public void getName_firstNameJonNoLastName_jon() {
        person = new Person();
        person.setName("Jon");
        String expected = "Jon";

        Assertions.assertEquals(expected,person.getName());
    }

    @Test
    public void getName_noFirstNameLastNameSnow_snow() {
        person = new Person();
        person.setLastName("Snow");
        String expected = "Snow";

        Assertions.assertEquals(expected,person.getName());
    }

    @Test
    public void getName_noFirstNameNorLastName_throwsException() {
        person = new Person();
        Assertions.assertThrows(RuntimeException.class,()->person.getName());
    }

    @Test
    public void isBirthdayToday_differentMonthAndDay_false() {
        person = new Person (){
            @Override
            public LocalDate getNow(){
                LocalDate date = LocalDate.now();
                return LocalDate.of(date.getYear(),
                        date.getMonthValue()+1,
                        date.getDayOfMonth()-1);
            }
        };

        person.setBirthday(LocalDate.now());
        Assertions.assertFalse(person.isBirthdayToday());
    }

    @Test
    public void isBirthdayToday_sameMonthDifferentDay_false() {
        person = new Person (){
            @Override
            public LocalDate getNow(){
                LocalDate date = LocalDate.now();
                return LocalDate.of(date.getYear()-1,
                        date.getMonthValue(),
                        date.getDayOfMonth()+1);
            }
        };

        person.setBirthday(LocalDate.now());
        Assertions.assertFalse(person.isBirthdayToday());
    }

    @Test
    public void isBirthdayToday_sameMonthAndDay_true() {
        person = new Person (){
            @Override
            public LocalDate getNow(){
                LocalDate date = LocalDate.now();
                return LocalDate.of(date.getYear()+1,
                        date.getMonthValue(),
                        date.getDayOfMonth());
            }
        };
        person.setBirthday(LocalDate.now());
        Assertions.assertTrue(person.isBirthdayToday());
    }


    @Test
    public void addToFamily_somePerson_familyHasNewMember() {
        Person relative = new Person();
        personMock.addToFamily(relative);
        verify(family,times(1)).add(relative);

    }

    @Test
    public void addToFamily_somePerson_personAddedAlsoHasItsFamilyUpdated() {
        personMock.addToFamily(relativeMock);
        verify(family,times(1)).add(personMock);

    }

    @Test
    public void isFamily_nonRelativePerson_false() {
        Person relative = new Person();
        when(family.contains(relative)).thenReturn(false);
        Assertions.assertFalse(personMock.isFamily(relative));

    }

    @Test
    public void isFamily_relativePerson_true() {
        Person relative = new Person();
        when(family.contains(relative)).thenReturn(true);
        Assertions.assertTrue(personMock.isFamily(relative));


    }
}
