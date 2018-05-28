package nativapps.teste.micrm.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Business {
    @DatabaseField(generatedId = true)
    Integer id;

    @DatabaseField
    String title;

    @DatabaseField
    String description;

    @DatabaseField
    String value;

    @DatabaseField
    String dueDate;

    @DatabaseField
    String state;

    @DatabaseField(foreign = true)
    Institution institution;

    @DatabaseField(foreign = true)
    Person person;

    public Business() {
    }

    public Business(String title, String description, String value, String dueDate, String state, Institution institution, Person person) {
        this.title = title;
        this.description = description;
        this.value = value;
        this.dueDate = dueDate;
        this.state = state;
        this.institution = institution;
        this.person = person;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Institution getInstitution() {
        return institution;
    }

    public void setInstitution(Institution institution) {
        this.institution = institution;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
