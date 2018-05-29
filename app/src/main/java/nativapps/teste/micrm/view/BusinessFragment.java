package nativapps.teste.micrm.view;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.j256.ormlite.dao.Dao;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.TextChange;
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

@EFragment(R.layout.fragment_business)
public class BusinessFragment extends Fragment {

    @ViewById(R.id.titleEditText)
    EditText titleEditText;

    @ViewById(R.id.descriptionEditText)
    EditText descriptionEditText;

    @ViewById(R.id.organizationSpinner)
    Spinner organizationSpinner;

    @ViewById(R.id.personSpinner)
    Spinner personSpinner;

    @ViewById(R.id.valueEditText)
    EditText valueEditText;

    @ViewById(R.id.dueDateEditText)
    EditText dueDateEditText;

    @ViewById(R.id.stateEditText)
    EditText stateEditText;

    @ViewById(R.id.addButton)
    Button addButton;

    @OrmLiteDao(helper = DatabaseHelper.class)
    Dao<Institution, Integer> institutionDao;

    @OrmLiteDao(helper = DatabaseHelper.class)
    Dao<Person, Integer> personDao;

    @OrmLiteDao(helper = DatabaseHelper.class)
    Dao<Business, Integer> businessDao;

    List<String> institutes = new ArrayList<>();
    List<String> people = new ArrayList<>();
    ArrayAdapter<String> organizationAdapter;
    ArrayAdapter<String> peopleAdapter;

    @AfterViews
    void afterViews() {
        ((MainActivity_) getActivity())
                .navigationView.setCheckedItem(R.id.nav_business);
        ((MainActivity_) getActivity())
                .toolbar.setTitle(getResources().getString(R.string.add_business));
        getSpinnersData();
        dueDateEditText.addTextChangedListener(new MaskWatcher("##/##/####"));
    }

    @Background
    void getSpinnersData() {
        try {
            for (Institution institution : institutionDao.queryForAll())
                institutes.add(institution.getName());
            for (Person person : personDao.queryForAll())
                people.add(person.getName());
            initAdapters();
            setSpinnersData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initAdapters() {
        organizationAdapter = new ArrayAdapter<>(
                getActivity(), R.layout.spinner_item, institutes);
        organizationAdapter.setDropDownViewResource(R.layout.spinner_dropdown);

        peopleAdapter = new ArrayAdapter<>(
                getActivity(), R.layout.spinner_item, people);
        peopleAdapter.setDropDownViewResource(R.layout.spinner_dropdown);
    }

    @UiThread
    void setSpinnersData() {
        organizationSpinner.setAdapter(organizationAdapter);
        organizationSpinner.setSelection(0);
        personSpinner.setAdapter(peopleAdapter);
        personSpinner.setSelection(0);
    }


    @Click(R.id.addButton)
    void addClick() {
        if (validateFields())
            showSureDialog();
        else
            showToast(getResources().getString(R.string.save_failed));
    }

    private Boolean validateFields() {
        return ValidateUtil.isNotNullEditText(titleEditText,
                getResources().getString(R.string.field_not_null))
                &&
                ValidateUtil.isNotNullEditText(dueDateEditText,
                        getResources().getString(R.string.field_not_null))
                &&
                ValidateUtil.isValidDate(dueDateEditText,
                        getResources().getString(R.string.invalid_date));
    }

    private void showSureDialog() {
        AlertDialog.Builder builder = ActivityUtil.callDialog(
                getActivity(), R.string.add_business, R.string.are_you_sure);

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
            businessDao.createOrUpdate(new Business(
                    titleEditText.getText().toString(),
                    descriptionEditText.getText().toString(),
                    valueEditText.getText().toString(),
                    dueDateEditText.getText().toString(),
                    stateEditText.getText().toString(),
                    institutionDao.queryForAll().get(organizationSpinner.getSelectedItemPosition()),
                    personDao.queryForAll().get(personSpinner.getSelectedItemPosition())));
            showToast(getResources().getString(R.string.business_saved));
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
