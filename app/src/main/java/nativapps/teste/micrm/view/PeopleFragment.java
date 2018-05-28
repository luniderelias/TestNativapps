package nativapps.teste.micrm.view;


import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.EditText;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import nativapps.teste.micrm.R;

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

}
