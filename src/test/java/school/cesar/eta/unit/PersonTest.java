package school.cesar.eta.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.fail;

public class PersonTest {

    private Person person;
    private Person member;
    private Person addMember;

    @BeforeEach
    public void setup() {

        person = new Person();

    }

    @Test
    public void getName_firstNameJonLastNameSnow_jonSnow() {
        //1- firstName and lastName
        person.setName("jon");
        person.setLastName("Snow");

        //Assertions
        Assertions.assertEquals("jonSnow", person.getName());

        //fail();
    }

    @Test
    public void getName_firstNameJonNoLastName_jon() {
        //2- firstName and No lastName
        person.setName("jon");

        //Assertions
        Assertions.assertEquals("jon", person.getName());

        ///fail();
    }

    @Test
    public void getName_noFirstNameLastNameSnow_snow() {
        //3- No firstName and lastName
        person.setLastName("Snow");

        //Assertions
        Assertions.assertEquals("Snow", person.getName());

        //fail();
    }

    @Test
    public void getName_noFirstNameNorLastName_throwsException() {
        //4- No firstName and No lastName

        //Assertions.assertThrows(RuntimeException.class,() -> method());
        Assertions.assertThrows(RuntimeException.class,() -> person.getName());

        //fail();
    }

    @Test
    public void isBirthdayToday_differentMonthAndDay_false() {
        //5- different Month And Day
        person.setBirthday((LocalDate.of(1984, 4, 15)));

        //assertFalse
        Assertions.assertFalse(person.isBirthdayToday());

        //fail();
    }

    @Test
    public void isBirthdayToday_sameMonthDifferentDay_false() {
        //6- same Month Different Day
        person.setBirthday((LocalDate.of(1984, 8, 11)));

        //assertFalse
        Assertions.assertFalse(person.isBirthdayToday());

        //fail();
    }

    @Test
    public void isBirthdayToday_sameMonthAndDay_true() {
        //7- same Month And Day
        person.setBirthday((LocalDate.now()));

        //assertTrue
        Assertions.assertTrue(person.isBirthdayToday());

        //fail();
    }

    @Test
    public void addToFamily_somePerson_familyHasNewMember() {
        //8- family Has New Member
        member = new Person();
        member.addToFamily(member);

        //assertTrue
        Assertions.assertTrue(member.isFamily(member));

        //fail();
    }

    @Test
    public void addToFamily_somePerson_personAddedAlsoHasItsFamilyUpdated() {
        //9-person Added Also Has Its Family Updated
        member = new Person();
        addMember = new Person();
        addMember.addToFamily(member);

        //assertTrue
        Assertions.assertTrue(addMember.isFamily(member));
        //fail();
    }

    @Test
    public void isFamily_nonRelativePerson_false() {
        //10-non Relative Person
        member = new Person();

        //assertFalse
        Assertions.assertFalse(person.isFamily(person));

        //fail();
    }

    @Test
    public void isFamily_relativePerson_true() {
        //11-relative Person
        addMember = new Person();
        person.addToFamily(addMember);

        //assertTrue
        Assertions.assertTrue(person.isFamily(addMember));

       //fail();
    }
}
