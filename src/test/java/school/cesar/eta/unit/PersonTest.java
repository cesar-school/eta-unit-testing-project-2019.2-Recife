package school.cesar.eta.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

public class PersonTest {

    private Person person;

    @InjectMocks
    private Person member1;

    @InjectMocks
    private Person member2;

    @Spy
    private List<Person> family;

    @BeforeEach
    public void setup() {

        person = new Person() {
            @Override
            public LocalDate getNow() {
                return LocalDate.of(2020, 10, 05);
            }
        };

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
        //Como o método getNow() está chamando LocalDate.now(), teste falhará se for executado no dia 08/11.

        LocalDate birthday = LocalDate.of(1984, 4, 15);
        person.setBirthday(birthday);

        //assertFalse
        Assertions.assertFalse(person.isBirthdayToday());

        //fail();
    }

    @Test
    public void isBirthdayToday_sameMonthDifferentDay_false() {
        //6- same Month Different Day
        //Como o método getNow() está chamando LocalDate.now(), teste falhará se for executado no dia 08/11.

        LocalDate birthday = LocalDate.of(1984, 8, 11);
        person.setBirthday(birthday);

        //assertFalse
        Assertions.assertFalse(person.isBirthdayToday());

        //fail();
    }

    @Test
    public void isBirthdayToday_sameMonthAndDay_true() {
        //7- same Month And Day
        //A data retornada pelo LocalDate.now() é calculado no momento em que o método é chamado,
        // então por frações de segundo o dia calculado pode ser diferente do calculado pela chamada
        // do getNow() o que pode provocar intermitências no resultado do teste. Evite basear seus testes em variáveis do sistema!

        LocalDate birthday = LocalDate.of(1984,10, 05);
        person.setBirthday(birthday);

        //assertTrue
        Assertions.assertTrue(person.isBirthdayToday());

        //fail();
    }

    @Test
    public void addToFamily_somePerson_familyHasNewMember() {
        //8- family Has New Member
        //Testes unitários devem isolar o cenário que está sendo testado, sendo assim não
        //contar com a correta implementação do método isFamily().

        member1.addToFamily(member2);

        verify(family, times(1)).add(member2);

        //fail();
    }

    @Test
    public void addToFamily_somePerson_personAddedAlsoHasItsFamilyUpdated() {
        //9-person Added Also Has Its Family Updated
        //Testes unitários devem isolar o cenário que está sendo testado, sendo assim não
        //deveria contar com a correta implementação do método isFamily().

        member1.addToFamily(member2);

        verify(family, times(1)).add(member1);

        //fail();
    }

    @Test
    public void isFamily_nonRelativePerson_false() {
        //10-non Relative Person

        //assertFalse
        Assertions.assertFalse(person.isFamily(person));

        //fail();
    }

    @Test
    public void isFamily_relativePerson_true() {
        //11-relative Person
        //Esta chamada acopla o sucesso do teste a correta implementação do método addToFamily().
        // Testes unitários devem isolar o cenário que está sendo testado.

        when(family.contains(null)).thenReturn(true);

        //assertTrue
        Assertions.assertTrue(member1.isFamily(null));

       //fail();
    }
}
