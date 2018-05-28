package nativapps.teste.micrm.view;


import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.EditText;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import nativapps.teste.micrm.R;

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
}
