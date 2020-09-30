
import static org.mockito.Mockito.mock;

import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import school.cesar.eta.unit.Person;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class PersonTest
{

    private Person person;

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
        person.setName(null);
        person.setLastName(null);

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
        person.addToFamily(somePerson);
        Assertions.assertTrue(person.isFamily(somePerson));

    }

    @Test
    public void addToFamily_somePerson_personAddedAlsoHasItsFamilyUpdated()
    {

    }

    @Test
    public void isFamily_nonRelativePerson_false()
    {
        person = mock(FakePerson.class);
        Mockito.when(person.isFamily(person)).thenReturn(false);
    }

    @Test
    public void isFamily_relativePerson_true()
    {
        FakePerson relativePerson = new FakePerson();
        boolean result = relativePerson.isFamily(person);
        Assertions.assertTrue(result);
    }
}
