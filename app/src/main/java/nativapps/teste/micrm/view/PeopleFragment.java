package nativapps.teste.micrm.view;


import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.EditText;

import com.j256.ormlite.dao.Dao;

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


    @Click(R.id.addButton)
    void addClick() {
        addItem();
    }

    @Background
    void addItem() {
        try {
            personDao.createOrUpdate(new Person(
                    nameEditText.getText().toString(),
                    phoneEditText.getText().toString(),
                    mailEditText.getText().toString()
            ));
            showToast(getResources().getString(R.string.saved));
        } catch (SQLException e) {
            e.printStackTrace();
            showToast(getResources().getString(R.string.save_failed));
        }
    }

    @UiThread
    void showToast(String saved) {
        ActivityUtil.showToast(getActivity(), saved);
    }

}
