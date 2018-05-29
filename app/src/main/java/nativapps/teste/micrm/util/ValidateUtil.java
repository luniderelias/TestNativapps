package nativapps.teste.micrm.util;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import nativapps.teste.micrm.R;


public class ValidateUtil {

    public static Boolean isNotNullEditText(EditText editText, String errorMessage) {
        if (editText.getText().length() == 0) {
            editText.requestFocus();
            editText.setError(errorMessage);
            return false;
        }
        return true;
    }

    public static Boolean isSpinnerItemSelected(Spinner spinner) {
        return spinner.getSelectedItem() != null;
    }

    public static Boolean checkEmail(EditText editText, String errorMessage) {
        try {
            if (editText.getText().toString().split("@")[1].split("\\.").length > 1)
                return true;
            else {
                editText.requestFocus();
                editText.setError(errorMessage);
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            editText.requestFocus();
            editText.setError(errorMessage);
            return false;
        }
    }
}