package nativapps.teste.micrm.view;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import com.j256.ormlite.dao.Dao;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.ormlite.annotations.OrmLiteDao;

import java.sql.SQLException;

import nativapps.teste.micrm.R;
import nativapps.teste.micrm.model.Institution;
import nativapps.teste.micrm.model.Person;
import nativapps.teste.micrm.util.ActivityUtil;
import nativapps.teste.micrm.util.DatabaseHelper;
import nativapps.teste.micrm.util.ValidateUtil;

@EFragment(R.layout.fragment_people)
public class PeopleFragment extends Fragment {

    @ViewById(R.id.nameEditText)
    EditText nameEditText;

    @ViewById(R.id.phoneEditText)
    EditText phoneEditText;

    @ViewById(R.id.mailEditText)
    EditText mailEditText;

    @ViewById(R.id.addButton)
    Button addButton;

    @OrmLiteDao(helper = DatabaseHelper.class)
    Dao<Person, Integer> personDao;

    @AfterViews
    void afterViews() {
        ((MainActivity_) getActivity())
                .navigationView.setCheckedItem(R.id.nav_people);
        ((MainActivity_) getActivity())
                .toolbar.setTitle(getResources().getString(R.string.add_person));

    }

    @Click(R.id.addButton)
    void addClick() {
        if (validateFields())
            showSureDialog();
    }

    private Boolean validateFields() {
        return ValidateUtil.isNotNullEditText(nameEditText,
                getResources().getString(R.string.field_not_null))
                &&
                ValidateUtil.checkEmail(mailEditText,
                        getResources().getString(R.string.invalid_email));
    }

    private void showSureDialog() {
        AlertDialog.Builder builder = ActivityUtil.callDialog(
                getActivity(), R.string.add_person, R.string.are_you_sure);

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
            personDao.createOrUpdate(new Person(
                    nameEditText.getText().toString(),
                    phoneEditText.getText().toString(),
                    mailEditText.getText().toString()
            ));
            showToast(getResources().getString(R.string.person_saved));

            switchFragment();
        } catch (SQLException e) {
            e.printStackTrace();
            showToast(getResources().getString(R.string.save_failed));
        }
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
