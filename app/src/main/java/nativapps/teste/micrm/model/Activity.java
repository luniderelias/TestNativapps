package nativapps.teste.micrm.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Activity {

    @DatabaseField(generatedId = true)
    Integer id;

    @DatabaseField
    String description;

    @DatabaseField
    String type;

    @DatabaseField(foreign = true)
    Institution institution;

    @DatabaseField(foreign = true)
    Person person;

    @DatabaseField(foreign = true)
    Business business;

    @DatabaseField
    String date;

    @DatabaseField
    String time;

    public Activity() {
    }

    public Activity(String description, String type, Institution institution, Person person, Business business, String date, String time) {
        this.description = description;
        this.type = type;
        this.institution = institution;
        this.person = person;
        this.business = business;
        this.date = date;
        this.time = time;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public Business getBusiness() {
        return business;
    }

    public void setBusiness(Business business) {
        this.business = business;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
