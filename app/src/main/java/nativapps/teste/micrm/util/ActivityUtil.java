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
import android.widget.Toast;

import nativapps.teste.micrm.R;


public class ActivityUtil {

    @SuppressLint("CommitTransaction")
    public static void switchFragment(Fragment fragment, int resIdContainer, AppCompatActivity context) {
        context.getSupportFragmentManager()
                .beginTransaction()
                .replace(resIdContainer, fragment)
                .addToBackStack("")
                .commit();
        context.getSupportFragmentManager().executePendingTransactions();
    }

    public static boolean isConnectedToInternet(Context context) {
        try {
            NetworkInfo netInfo = ((ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE))
                    .getActiveNetworkInfo();
            return netInfo != null && netInfo.isConnectedOrConnecting();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void showErrorDialog(Context context, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(
                context,
                R.style.Theme_AppCompat_Light_Dialog);
        builder.setTitle(title).setMessage(message).setPositiveButton(
                R.string.ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        builder.create();
    }

    public static void showToast(Context context, String message){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }

    public static ProgressDialog callProgressDialog(Context context, String title, String message) {
        ProgressDialog progress = null;
        if (context != null) {
            progress = new ProgressDialog(context, R.style.Theme_AppCompat_Light_Dialog);
            progress.setTitle(title);
            progress.setMessage(message);
            progress.setCancelable(true);
            progress.show();
        }
        return progress;
    }

    public static android.app.AlertDialog.Builder callDialog(Context context, int titleResId, int messageResId){
        android.app.AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new android.app.AlertDialog.Builder(context, android.R.style.Theme_Material_Light_Dialog_Alert);
        } else {
            builder = new android.app.AlertDialog.Builder(context);
        }
        builder.setTitle(context.getString(titleResId))
                .setMessage(context.getString(messageResId));
        return builder;
    }

    public static void closeProgressDialog(ProgressDialog progress) {
        progress.dismiss();
    }

}