package nativapps.teste.micrm.view;


import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import nativapps.teste.micrm.R;

@EFragment(R.layout.fragment_business)
public class BusinessFragment extends Fragment {

    @ViewById(R.id.titleEditText)
    EditText titleEditText;

    @ViewById(R.id.addressEditText)
    EditText addressEditText;

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
}
