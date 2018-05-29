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

import net.rimoto.intlphoneinput.IntlPhoneInput;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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

    public static Boolean isValidTime(EditText editText, String errorMessage) {
        try {
            if (Integer.valueOf(editText.getText().toString().split(":")[0]) > 24) {
                setError(editText, errorMessage);
                return false;
            } else {
                if (Integer.valueOf(editText.getText().toString().split(":")[1]) > 59) {
                    setError(editText, errorMessage);
                    return false;
                }
            }
        } catch (Exception e) {
            setError(editText, errorMessage);
            e.printStackTrace();
        }
        return true;
    }

    public static boolean isValidDate(EditText editText, String errorMessage) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(editText.getText().toString().trim());
        } catch (ParseException pe) {
            setError(editText, errorMessage);
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
                setError(editText, errorMessage);
                return false;
            }
        } catch (Exception e) {
            setError(editText, errorMessage);
            e.printStackTrace();
            return false;
        }
    }

    public static boolean checkPhone(IntlPhoneInput phoneInputView) {
        if (!phoneInputView.isValid()) {
            phoneInputView.requestFocus();
            return false;
        }
        return true;
    }

    private static void setError(EditText editText, String errorMessage) {
        editText.requestFocus();
        editText.setError(errorMessage);
    }
}