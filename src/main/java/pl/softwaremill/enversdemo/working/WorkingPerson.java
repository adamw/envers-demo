package pl.softwaremill.enversdemo.working;

import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * @author Adam Warski (adam at warski dot org)
 */
@Entity
@Audited
public class WorkingPerson {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String surname;

    private int age;

    @ManyToOne
    private WorkingAddress address;

    public WorkingPerson() {
    }

    public WorkingPerson(String name, String surname, int age) {
        this.name = name;
        this.surname = surname;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public WorkingAddress getAddress() {
        return address;
    }

    public void setAddress(WorkingAddress address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WorkingPerson)) return false;

        WorkingPerson workingPerson = (WorkingPerson) o;

        if (age != workingPerson.age) return false;
        if (id != null ? !id.equals(workingPerson.id) : workingPerson.id != null) return false;
        if (name != null ? !name.equals(workingPerson.name) : workingPerson.name != null) return false;
        if (surname != null ? !surname.equals(workingPerson.surname) : workingPerson.surname != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + age;
        return result;
    }
}
