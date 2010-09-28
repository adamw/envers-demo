package pl.softwaremill.enversdemo.working;

import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Set;

/**
 * @author Adam Warski (adam at warski dot org)
 */
@Entity
@Audited
public class WorkingAddress {
    @Id
    @GeneratedValue
    private Long id;

    private String street;

    @OneToMany(mappedBy = "address")
    private Set<WorkingPerson> persons;

    public WorkingAddress() {
    }

    public WorkingAddress(String street) {
        this.street = street;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Set<WorkingPerson> getPersons() {
        return persons;
    }

    public void setPersons(Set<WorkingPerson> persons) {
        this.persons = persons;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WorkingAddress)) return false;

        WorkingAddress that = (WorkingAddress) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (persons != null ? !persons.equals(that.persons) : that.persons != null) return false;
        if (street != null ? !street.equals(that.street) : that.street != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (street != null ? street.hashCode() : 0);
        result = 31 * result + (persons != null ? persons.hashCode() : 0);
        return result;
    }
}
