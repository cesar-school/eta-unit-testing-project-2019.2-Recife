package school.cesar.eta.unit;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Person {
    private String firstName = null;
    private String lastName = null;
    private LocalDate birthday;
    private List<Person> family = new ArrayList<Person>();

    public LocalDate getNow() {
        return LocalDate.now();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getName() {
        if (this.firstName == null && this.lastName == null) {
            throw new RuntimeException("Name must be filled");
        }
        if (this.firstName != null && this.lastName != null) {
            return this.firstName + this.lastName;
        } else if (this.firstName != null) {
            return this.firstName;
        }
        return lastName;
    }

    public boolean isBirthdayToday() {
        LocalDate now = getNow();
        return now.getDayOfMonth() == this.birthday.getDayOfMonth()
                && now.getMonth() == this.birthday.getMonth();
    }

    public void addToFamily(Person person) {
        this.family.add(person);
        person.family.add(this);
    }

    public boolean isFamily(Person person) {
        return this.family.contains(person);
    }
}
