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

@ExtendWith(MockitoExtension.class)
public class PersonTest {
    private Person person;

    @InjectMocks
    Person personInject = new Person();

    @InjectMocks
    Person relativeInject = new Person();

    @Mock
    List<Person> family = new ArrayList<Person>();

    @BeforeEach
    public void setup() {
        person = new Person();
    }

    @Test
    public void getName_firstNameJonLastNameSnow_jonSnow() {
        person.setName("Jon");
        person.setLastName("Snow");
        String actualName = person.getName();
        Assertions.assertEquals("JonSnow", actualName);
    }

    @Test
    public void getName_firstNameJonNoLastName_jon() {
        person.setName("Jon");
        String actualName = person.getName();
        Assertions.assertEquals("Jon", actualName);
    }

    @Test
    public void getName_noFirstNameLastNameSnow_snow() {
        person.setLastName("Snow");
        String actualLastName = person.getName();
        Assertions.assertEquals("Snow", actualLastName);
    }

    @Test
    public void getName_noFirstNameNorLastName_throwsException() {
        person = new Person();
        Assertions.assertThrows(RuntimeException.class, () -> person.getName());
    }

    @Test
    public void isBirthdayToday_differentMonthAndDay_false() {
        Person person = new Person() {
            @Override
            public LocalDate getNow(){
                LocalDate date = LocalDate.now();
                return LocalDate.of(date.getYear(),
                        date.getMonthValue()-2,
                        date.getDayOfMonth()+2);
            }
        };

        person.setBirthday(LocalDate.now());
        Assertions.assertFalse(person.isBirthdayToday());

    }

    @Test
    public void isBirthdayToday_sameMonthDifferentDay_false() {
        Person person = new Person() {
            @Override
            public LocalDate getNow(){
                LocalDate date = LocalDate.now();
                return LocalDate.of(date.getYear(),
                        date.getMonthValue(),
                        date.getDayOfMonth()+2);
            }
        };
        person.setBirthday(LocalDate.now());
        Assertions.assertFalse(person.isBirthdayToday());

    }

    @Test
    public void isBirthdayToday_sameMonthAndDay_true() {
        Person person = new Person() {
            @Override
            public LocalDate getNow(){
                LocalDate date = LocalDate.now();
                return LocalDate.of(date.getYear(),
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
        personInject.addToFamily(relative);
        verify(family,times(1)).add(relative);
    }

    @Test
    public void addToFamily_somePerson_personAddedAlsoHasItsFamilyUpdated() {
        personInject.addToFamily(relativeInject);
        verify(family,times(1)).add(personInject);
    }

    @Test
    public void isFamily_nonRelativePerson_false() {
        Person relative = new Person();
        when(family.contains(relative)).thenReturn(false);
        Assertions.assertFalse(personInject.isFamily(relative));
    }

    @Test
    public void isFamily_relativePerson_true() {
        Person relative = new Person();
        when(family.contains(relative)).thenReturn(true);
        Assertions.assertTrue(personInject.isFamily(relative));

    }
}
