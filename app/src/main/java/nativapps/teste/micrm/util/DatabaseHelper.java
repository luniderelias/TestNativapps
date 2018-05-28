package nativapps.teste.micrm.util;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.util.ArrayList;
import java.util.List;

import nativapps.teste.micrm.R;
import nativapps.teste.micrm.model.Activity;
import nativapps.teste.micrm.model.Business;
import nativapps.teste.micrm.model.Institution;
import nativapps.teste.micrm.model.Person;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    private static final String DATABASE_NAME = "micrm.db";
    private static final int DATABASE_VERSION = 1;

    List<Activity> activityList = new ArrayList<>();
    List<Business> businessList = new ArrayList<>();
    List<Institution> institutionList = new ArrayList<>();
    List<Person> personList = new ArrayList<>();


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Activity.class);
            TableUtils.createTable(connectionSource, Business.class);
            TableUtils.createTable(connectionSource, Institution.class);
            TableUtils.createTable(connectionSource, Person.class);

            populateDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void populateDatabase() {
        populatePerson();
        populateInstitution();
        populateBusiness();
        populateActivity();
    }

    private void populatePerson() {
        RuntimeExceptionDao<Person, Integer> dao = getRuntimeExceptionDao(Person.class);
        personList.add(new Person("Person 1", "1111-1111", "mail1@mail.com"));
        personList.add(new Person("Person 2", "2222-2222","mail2@mail.com"));
        personList.add(new Person("Person 3", "3333-3333","mail3@mail.com"));
        dao.create(personList);
    }

    private void populateInstitution() {
        RuntimeExceptionDao<Institution, Integer> dao = getRuntimeExceptionDao(Institution.class);
        institutionList.add(new Institution("Institution 1","Address 1", "1111-1111"));
        institutionList.add(new Institution("Institution 2","Address 2", "2222-2222"));
        institutionList.add(new Institution("Institution 3","Address 3", "3333-3333"));
        dao.create(institutionList);
    }

    private void populateBusiness() {
        RuntimeExceptionDao<Business, Integer> dao = getRuntimeExceptionDao(Business.class);
        businessList.add(new Business("1", "1", "01/01/2018", "1", "1",
                institutionList.get(0),
                personList.get(0)));
        businessList.add(new Business("2", "2", "01/01/2018", "1", "1",
                institutionList.get(0),
                personList.get(0)));
        businessList.add(new Business("3", "3", "01/01/2018", "1", "1",
                institutionList.get(0),
                personList.get(0)));
        dao.create(businessList);
    }

    private void populateActivity() {
        RuntimeExceptionDao<Activity, Integer> dao = getRuntimeExceptionDao(Activity.class);
        activityList.add(new Activity("1", "1",
                institutionList.get(0),
                personList.get(0),
                businessList.get(0),
                "01/01/20180", "10:00"));
        activityList.add(new Activity("2", "2",
                institutionList.get(0),
                personList.get(0),
                businessList.get(0),
                "01/01/20180", "10:00"));
        activityList.add(new Activity("3", "3",
                institutionList.get(0),
                personList.get(0),
                businessList.get(0),
                "01/01/20180", "10:00"));
        dao.create(activityList);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, Activity.class, true);
            TableUtils.dropTable(connectionSource, Business.class, true);
            TableUtils.dropTable(connectionSource, Institution.class, true);
            TableUtils.dropTable(connectionSource, Person.class, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        super.close();
    }
}
