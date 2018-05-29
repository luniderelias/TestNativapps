package nativapps.teste.micrm.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.j256.ormlite.dao.Dao;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.ItemSelect;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.ormlite.annotations.OrmLiteDao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import nativapps.teste.micrm.R;
import nativapps.teste.micrm.model.Activity;
import nativapps.teste.micrm.model.Business;
import nativapps.teste.micrm.model.Institution;
import nativapps.teste.micrm.model.Person;
import nativapps.teste.micrm.util.ActivityUtil;
import nativapps.teste.micrm.util.DatabaseHelper;
import nativapps.teste.micrm.util.MaskWatcher;
import nativapps.teste.micrm.util.ValidateUtil;

@EFragment(R.layout.fragment_activities)
public class ActivitiesFragment extends Fragment {

    @ViewById(R.id.descriptionEditText)
    EditText descriptionEditText;

    @ViewById(R.id.typeEditText)
    EditText typeEditText;

    @ViewById(R.id.organizationSpinner)
    Spinner organizationSpinner;

    @ViewById(R.id.personSpinner)
    Spinner personSpinner;

    @ViewById(R.id.businessSpinner)
    Spinner businessSpinner;

    @ViewById(R.id.dateEditText)
    EditText dateEditText;

    @ViewById(R.id.timeEditText)
    EditText timeEditText;

    @ViewById(R.id.addButton)
    Button addButton;

    @OrmLiteDao(helper = DatabaseHelper.class)
    Dao<Institution, Integer> institutionDao;

    @OrmLiteDao(helper = DatabaseHelper.class)
    Dao<Person, Integer> personDao;

    @OrmLiteDao(helper = DatabaseHelper.class)
    Dao<Business, Integer> businessDao;

    @OrmLiteDao(helper = DatabaseHelper.class)
    Dao<Activity, Integer> activityDao;

    List<String> institutes = new ArrayList<>();
    List<String> people = new ArrayList<>();
    List<String> businesses = new ArrayList<>();
    ArrayAdapter<String> organizationAdapter;
    ArrayAdapter<String> peopleAdapter;
    ArrayAdapter<String> businessAdapter;

    @AfterViews
    void afterViews() {
        ((MainActivity_) getActivity())
                .navigationView.setCheckedItem(R.id.nav_activities);
        ((MainActivity_) getActivity())
                .toolbar.setTitle(getResources().getString(R.string.add_activity));
        getSpinnersData();
        dateEditText.addTextChangedListener(new MaskWatcher("##/##/####"));
        timeEditText.addTextChangedListener(new MaskWatcher("##:##"));
    }

    @Background
    void getSpinnersData() {
        try {
            for (Institution institution : institutionDao.queryForAll())
                institutes.add(institution.getName());
            for (Person person : personDao.queryForAll())
                people.add(person.getName());
            for (Business business : businessDao.queryForAll())
                businesses.add(business.getTitle());
            initAdapters();
            setSpinnersData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initAdapters() {
        institutes.add(0, getResources().getString(R.string.inform));
        organizationAdapter = new ArrayAdapter<>(
                getActivity(), R.layout.spinner_item, institutes);
        organizationAdapter.setDropDownViewResource(R.layout.spinner_dropdown);

        people.add(0, getResources().getString(R.string.inform));
        peopleAdapter = new ArrayAdapter<>(
                getActivity(), R.layout.spinner_item, people);
        peopleAdapter.setDropDownViewResource(R.layout.spinner_dropdown);

        businesses.add(0, getResources().getString(R.string.inform));
        businessAdapter = new ArrayAdapter<>(
                getActivity(), R.layout.spinner_item, businesses);
        businessAdapter.setDropDownViewResource(R.layout.spinner_dropdown);
    }

    @UiThread
    void setSpinnersData() {
        organizationSpinner.setAdapter(organizationAdapter);
        organizationSpinner.setSelection(0);

        personSpinner.setAdapter(peopleAdapter);
        personSpinner.setSelection(0);

        businessSpinner.setAdapter(businessAdapter);
        businessSpinner.setSelection(0);
    }

    @Click(R.id.addButton)
    void addClick() {
        if (validateFields()) {
            if (checkSpinners())
                showSureDialog();
        } else
            showToast(getResources().getString(R.string.save_failed));
    }

    private Boolean validateFields() {
        return ValidateUtil.isNotNullEditText(descriptionEditText,
                getResources().getString(R.string.field_not_null))
                && ValidateUtil.isValidTime(timeEditText,
                getResources().getString(R.string.invalid_time)) &&
                ValidateUtil.isValidDate(dateEditText,
                        getResources().getString(R.string.invalid_date));
    }

    private Boolean checkSpinners() {
        if (organizationSpinner.getSelectedItemPosition() == 0) {
            showToast(getResources().getString(R.string.inform) +
                    " " + getResources().getString(R.string.organization));
            return false;
        } else {
            if (personSpinner.getSelectedItemPosition() == 0) {
                showToast(getResources().getString(R.string.inform) +
                        " " + getResources().getString(R.string.people));
                return false;
            } else {
                if (businessSpinner.getSelectedItemPosition() == 0) {
                    showToast(getResources().getString(R.string.inform) +
                            " " + getResources().getString(R.string.business));
                    return false;
                } else
                    return true;
            }
        }
    }

    private void showSureDialog() {
        AlertDialog.Builder builder = ActivityUtil.callDialog(
                getActivity(), R.string.add_activity, R.string.are_you_sure);

        builder.setPositiveButton(getResources().getString(R.string.ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        addItem();
                    }
                }).setNegativeButton(getResources().getString(R.string.cancel),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).show();
    }

    @Background
    void addItem() {
        try {
            activityDao.createOrUpdate(new Activity(
                    descriptionEditText.getText().toString(),
                    typeEditText.getText().toString(),
                    institutionDao.queryForAll().get(organizationSpinner.getSelectedItemPosition()-1),
                    personDao.queryForAll().get(personSpinner.getSelectedItemPosition()-1),
                    businessDao.queryForAll().get(businessSpinner.getSelectedItemPosition()-1),
                    dateEditText.getText().toString(),
                    timeEditText.getText().toString()));
            showToast(getResources().getString(R.string.activity_saved));

            switchFragment();
        } catch (SQLException e) {
            e.printStackTrace();
            showToast(getResources().getString(R.string.save_failed));
        }
    }

    @Click(R.id.addOrganizationImageView)
    void checkOrganizationSpinner() {
        ActivityUtil.callDialog(getActivity(), R.string.add_organization, R.string.want_to_create)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ((MainActivity_) getActivity()).switchFragment("InstitutionFragment");
                    }
                }).show();
    }

    @Click(R.id.addBusinessImageView)
    void checkBusinessSpinner() {
        ActivityUtil.callDialog(getActivity(), R.string.add_business, R.string.want_to_create)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ((MainActivity_) getActivity()).switchFragment("BusinessFragment");
                    }
                }).show();
    }

    @Click(R.id.addPeopleImageView)
    void checkPersonSpinner() {
        ActivityUtil.callDialog(getActivity(), R.string.add_person, R.string.want_to_create)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ((MainActivity_) getActivity()).switchFragment("PeopleFragment");
                    }
                }).show();
    }


    @UiThread
    void switchFragment() {
        ActivityUtil.switchFragment(new HomeFragment_(),
                R.id.home_container, ((MainActivity_) getActivity()));
    }

    @UiThread
    void showToast(String saved) {
        ActivityUtil.showToast(getActivity(), saved);
    }
}
