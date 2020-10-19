package school.cesar.eta.unit;

import static org.mockito.Mockito.mock;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import school.cesar.eta.unit.Person;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class PersonTest
{

    private Person person;
    @Spy private List<Person> family = new ArrayList<Person>();

    @InjectMocks
    Person somePerson;

    @BeforeEach
    public void setup()
    {
        person = new Person()
        {
            @Override
            public LocalDate getNow()
            {
                return LocalDate.of(2000, 02, 20);
            }
        };
    }

    @Test
    public void getName_firstNameJonLastNameSnow_jonSnow()
    {
        person.setName("Jhon");
        person.setLastName("Snow");
        Assertions.assertEquals("JhonSnow", person.getName());

    }

    @Test
    public void getName_firstNameJonNoLastName_jon()
    {
        person.setName("Jhon");
        Assertions.assertEquals("Jhon", person.getName());
    }

    @Test
    public void getName_noFirstNameLastNameSnow_snow()
    {
        person.setLastName("Snow");
        Assertions.assertEquals("Snow", person.getName());
    }

    @Test
    public void getName_noFirstNameNorLastName_throwsException()
    {
        Assertions.assertThrows(RuntimeException.class, () -> {
            person.getName();
        });
    }

    @Test
    public void isBirthdayToday_differentMonthAndDay_false()
    {
        person.setBirthday(LocalDate.of(2000, 01, 21));
        boolean result = person.isBirthdayToday();
        Assertions.assertFalse(result);

    }

    @Test
    public void isBirthdayToday_sameMonthDifferentDay_false()
    {
        person.setBirthday(LocalDate.of(2000, 02, 21));
        boolean result = person.isBirthdayToday();
        Assertions.assertFalse(result);
    }

    @Test
    public void isBirthdayToday_sameMonthAndDay_true()
    {
        person.setBirthday(LocalDate.of(2000, 02, 20));
        boolean result = person.isBirthdayToday();
        Assertions.assertTrue(result);
    }

    @Test
    public void addToFamily_somePerson_familyHasNewMember()
    {
        somePerson.addToFamily(person);
        verify(family, times(1)).add(person);
    }

    @Test
    public void addToFamily_somePerson_personAddedAlsoHasItsFamilyUpdated()
    {
        person.addToFamily(somePerson);
        verify(family, times(1)).add(person);
    }

    @Test
    public void isFamily_nonRelativePerson_false()
    {
        boolean result = person.isFamily(new Person());
        Assertions.assertFalse(result);
    }

    @Test
    public void isFamily_relativePerson_true()
    {
        when(family.contains(person)).thenReturn(true);
        Assertions.assertTrue(somePerson.isFamily(person));
    }
}
