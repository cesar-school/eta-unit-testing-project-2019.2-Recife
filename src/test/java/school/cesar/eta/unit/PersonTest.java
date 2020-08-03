package school.cesar.eta.unit;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;

public class PersonTest {
    @Test
    public void getName_firstNameJonLastNameSnow_jonSnow() {
    }

    @Test
    public void getName_firstNameJonNoLastName_jon() {
    }

    @Test
    public void getName_noFirstNameLastNameSnow_snow() {
    }

    @Test
    public void getName_noFirstNameNorLastName_throwsException() {
    }

    @Test
    public void isBirthdayToday_differentMonthAndDay_false() {
    }

    @Test
    public void isBirthdayToday_sameMonthDifferentDay_false() {
    }

    @Test
    public void isBirthdayToday_sameMonthAndDay_true() {
    }

    @Test
    public void addToFamily_somePerson_familyHasNewMember() {
    }

    @Test
    public void addToFamily_somePerson_personAddedAlsoHasItsFamilyUpdated() {
    }

    @Test
    public void isFamily_nonRelativePerson_false() {
    }

    @Test
    public void isFamily_relativePerson_true() {
    }
}
