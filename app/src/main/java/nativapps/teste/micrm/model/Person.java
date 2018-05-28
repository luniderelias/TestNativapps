package nativapps.teste.micrm.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Person {
    @DatabaseField(generatedId = true)
    Integer id;

    @DatabaseField
    String name;

    @DatabaseField
    String phone;

    @DatabaseField
    String mail;

    public Person() {
    }

    public Person(String name, String phone, String mail) {
        this.name = name;
        this.phone = phone;
        this.mail = mail;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
