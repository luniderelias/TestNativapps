package nativapps.teste.micrm.view;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
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
import nativapps.teste.micrm.model.Activity;
import nativapps.teste.micrm.model.Institution;
import nativapps.teste.micrm.model.Person;
import nativapps.teste.micrm.util.ActivityUtil;
import nativapps.teste.micrm.util.DatabaseHelper;
import nativapps.teste.micrm.util.ValidateUtil;

@EFragment(R.layout.fragment_institution)
public class InstitutionFragment extends Fragment {

    @ViewById(R.id.nameEditText)
    EditText nameEditText;

    @ViewById(R.id.addressEditText)
    EditText addressEditText;

    @ViewById(R.id.phoneEditText)
    EditText phoneEditText;

    @ViewById(R.id.addButton)
    Button addButton;

    @OrmLiteDao(helper = DatabaseHelper.class)
    Dao<Institution, Integer> institutionDao;

    @AfterViews
    void AfterViews() {
        ((MainActivity_) getActivity())
                .navigationView.setCheckedItem(R.id.nav_institution);
        ((MainActivity_) getActivity())
                .toolbar.setTitle(getResources().getString(R.string.add_organization));
    }

    @Click(R.id.addButton)
    void addClick() {
        if(validateFields())
            showSureDialog();
    }

    private Boolean validateFields(){
        return ValidateUtil.isNotNullEditText(nameEditText,
                getResources().getString(R.string.field_not_null));
    }

    private void showSureDialog() {
        AlertDialog.Builder builder = ActivityUtil.callDialog(
                getActivity(), R.string.add_organization, R.string.are_you_sure);

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
            institutionDao.createOrUpdate(new Institution(
                    nameEditText.getText().toString(),
                    addressEditText.getText().toString(),
                    phoneEditText.getText().toString()
            ));
            showToast(getResources().getString(R.string.organization_saved));

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
