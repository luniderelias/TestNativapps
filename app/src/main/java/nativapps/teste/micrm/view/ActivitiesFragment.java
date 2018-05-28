package nativapps.teste.micrm.view;

import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import nativapps.teste.micrm.R;

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
}
