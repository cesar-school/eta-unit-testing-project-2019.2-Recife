package school.cesar.eta.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Spy;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.*;

public class PersonTest {

   @BeforeEach
   public void setupTests(){
       person = new Person();
   }
    @InjectMocks
    private Person pMock1;
    @InjectMocks
    private Person pMock2;

    @Spy
    private List<Person> family;

   private Person person;



    @Test
    public void getName_firstNameJonLastNameSnow_jonSnow() {
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
        Assertions.assertEquals("Snow", person.getLastName());
    }

    @Test
    public void getName_noFirstNameNorLastName_throwsException() {
        Assertions.assertThrows(RuntimeException.class, () -> person.getName());
    }

    @Test
    public void isBirthdayToday_differentMonthAndDay_false() {

        LocalDate birthday = LocalDate.of(1994,10,2);
        person.setBirthday(birthday);
        Assertions.assertFalse(person.isBirthdayToday());

    }

    @Test
    public void isBirthdayToday_sameMonthDifferentDay_false() {
        LocalDate birthday = LocalDate.of(1994,9,7);
        person.setBirthday(birthday);
        Assertions.assertFalse(person.isBirthdayToday());
    }

    @Test
    public void isBirthdayToday_sameMonthAndDay_true() {
        LocalDate birthday = LocalDate.of(1994,9,2);
        person.setBirthday(birthday);
        Assertions.assertTrue(person.isBirthdayToday());
    }


    @Test
    public void addToFamily_somePerson_familyHasNewMember() {
        pMock1.addToFamily(pMock2);
        verify(family, times(1)).add(pMock2);
    }

    /*@Test
    public void addToFamily_somePerson_personAddedAlsoHasItsFamilyUpdated() {
        fail();
    }*/

    @Test
    public void isFamily_nonRelativePerson_false() {
       Assertions.assertFalse(person.isFamily(person));
    }

    @Test
    public void isFamily_relativePerson_true() {
        when(family.contains(null)).thenReturn(true);
       Assertions.assertTrue(pMock1.isFamily(null));
    }
}
