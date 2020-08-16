package school.cesar.eta.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class PersonTest {

    //Variables
    private Person person;

    @BeforeEach
    public void setupPersonTest(){
        person = new Person();
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
        //We don't want to getNow() to have an undesirable effect, so we will use a Stub
        person = new Person() {
            @Override
            public LocalDate getNow(){
                return LocalDate.of(2020,8,7);
            }
        };

        person.setBirthday(LocalDate.of(2020,1,1));
        Assertions.assertFalse(person.isBirthdayToday());
    }

    @Test
    public void isBirthdayToday_sameMonthDifferentDay_false() {
        //We don't want getNow() to have an undesirable effect, so we will use a Stub
        person = new Person() {
            @Override
            public LocalDate getNow(){
                return LocalDate.of(2020,8,7);
            }
        };

        person.setBirthday(LocalDate.of(2020,8,1));
        Assertions.assertFalse(person.isBirthdayToday());
    }

    @Test
    public void isBirthdayToday_sameMonthAndDay_true() {
        //We don't want getNow() to have an undesirable effect, so we will use a Stub
        person = new Person() {
            @Override
            public LocalDate getNow(){
                return LocalDate.of(2020,8,7);
            }
        };

        person.setBirthday(LocalDate.of(2020,8,7));
        Assertions.assertTrue(person.isBirthdayToday());
    }

    @Test
    public void addToFamily_somePerson_familyHasNewMember() {
        //Used Fake since we don't have a Database (or don't want to use a real one)
        List<Person> fakeFamily = new ArrayList<Person>();
        fakeFamily.add(person);

        //using reflection so private attribute "family" can be accessed
        Object somePerson;
        Field privateFamily;
        Method publicAddToFamily;
        try {
            somePerson = Class.forName("school.cesar.eta.unit.Person").newInstance();
            privateFamily = somePerson.getClass().getDeclaredField("family");
            privateFamily.setAccessible(true);
            publicAddToFamily = somePerson.getClass().getMethod("addToFamily", Person.class);
            publicAddToFamily.invoke(somePerson, person);
            Assertions.assertEquals(fakeFamily, privateFamily.get(somePerson));
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchFieldException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void addToFamily_somePerson_personAddedAlsoHasItsFamilyUpdated() {
        //Used Fake since we don't have a Database (or don't want to use a real one)
        List<Person> fakeFamily = new ArrayList<Person>();
        fakeFamily.add(person);

        //using reflection so private attribute "family" can be accessed
        Object somePerson;
        Field privateFamily;
        try {
            somePerson = Class.forName("school.cesar.eta.unit.Person").newInstance();
            privateFamily = somePerson.getClass().getDeclaredField("family");
            privateFamily.setAccessible(true);
            person.addToFamily((Person) somePerson);
            Assertions.assertEquals(fakeFamily, privateFamily.get(somePerson));
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void isFamily_nonRelativePerson_false() {
        //Used Dummy to create a nonRelativePerson
        class DummyPerson extends Person{}
        DummyPerson nonRelativePerson = new DummyPerson();
        Assertions.assertFalse(person.isFamily(nonRelativePerson));
    }

    @Test
    public void isFamily_relativePerson_true() {
        //Used Fake since we don't have a Database (or don't want to use a real one)
        List<Person> fakeFamily = new ArrayList<Person>();
        fakeFamily.add(person);

        //using reflection so private attribute "family" can be accessed
        Object relativePerson;
        Field privateFamily;
        Method publicIsFamily;
        try {
            relativePerson = Class.forName("school.cesar.eta.unit.Person").newInstance();
            privateFamily = relativePerson.getClass().getDeclaredField("family");
            privateFamily.setAccessible(true);
            privateFamily.set(relativePerson, fakeFamily);
            publicIsFamily = relativePerson.getClass().getMethod("isFamily", Person.class);
            Assertions.assertTrue((Boolean) publicIsFamily.invoke(relativePerson, person));

        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchFieldException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }

    }
}
