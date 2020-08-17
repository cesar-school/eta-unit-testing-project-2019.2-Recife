package school.cesar.eta.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import school.cesar.eta.unit.utils.SpyPerson;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PersonTest {
    private Person p;

    @InjectMocks
    private Person p1;

    @InjectMocks
    private Person p2;

    @Spy
    public List<Person> family;

    @BeforeEach
    public void setUp(){
        p = new Person(){

            @Override
            public LocalDate getNow() {
                int year = 2020;
                int month = 8;
                int dayOfMonth = 11;

                return LocalDate.of(year, month, dayOfMonth);
            }
        };
    }

    @Test
    public void getName_firstNameJonLastNameSnow_jonSnow() {

        String firstName = "Jon";
        String lastName = "Snow";

        p.setName(firstName);
        p.setLastName(lastName);

        String expectedResult = String.format("%s%s",firstName,lastName);
        String actualResult = p.getName();

        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void getName_firstNameJonNoLastName_jon() {

        String firstName = "Jon";

        p.setName(firstName);

        String expectedResult = firstName;
        String actualResult = p.getName();

        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void getName_noFirstNameLastNameSnow_snow() {

        String lastName = "Snow";

        p.setLastName(lastName);

        String expectedResult = lastName;
        String actualResult = p.getName();

        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void getName_noFirstNameNorLastName_throwsException() {

        Exception e = assertThrows(RuntimeException.class, () -> {
            p.getName();
        });

        String expectedResult = "Name must be filled";
        String actualResult = e.getMessage();

        Assertions.assertEquals(expectedResult, actualResult);

    }

    @Test
    public void isBirthdayToday_differentMonthAndDay_false() {
        int year = 1989;
        int month = 7;
        int dayOfMonth = 10;

        LocalDate localDate = LocalDate.of(year, month, dayOfMonth);

        p.setBirthday(localDate);

        boolean actualResult = p.isBirthdayToday();

        Assertions.assertFalse(actualResult);
    }

    @Test
    public void isBirthdayToday_sameMonthDifferentDay_false() {
        int year = 1989;
        int month = 8;
        int dayOfMonth = 10;

        LocalDate localDate = LocalDate.of(year, month, dayOfMonth);

        p.setBirthday(localDate);

        boolean actualResult = p.isBirthdayToday();

        Assertions.assertFalse(actualResult);
    }

    @Test
    public void isBirthdayToday_sameMonthAndDay_true() {
        int year = 1989;
        int month = 8;
        int dayOfMonth = 11;

        LocalDate localDate = LocalDate.of(year, month, dayOfMonth);

        p.setBirthday(localDate);

        boolean actualResult = p.isBirthdayToday();

        Assertions.assertTrue(actualResult);
    }

    @Test
    public void addToFamily_somePerson_familyHasNewMember() {

        SpyPerson spyPerson = new SpyPerson();
        spyPerson.addToFamily(p1);

        Assertions.assertEquals(spyPerson.family.get(0), p1);

    }

    @Test
    public void addToFamily_somePerson_personAddedAlsoHasItsFamilyUpdated() {

        p1.addToFamily(p2);

        verify(family, times(1)).add(p1);
    }

    @Test
    public void isFamily_nonRelativePerson_false() {

        when(family.contains(p2)).thenReturn(false);
        Assertions.assertFalse(p1.isFamily(p2));

    }

    @Test
    public void isFamily_relativePerson_true() {

        when(family.contains(p2)).thenReturn(true);
        Assertions.assertTrue(p1.isFamily(p2));
    }
}
