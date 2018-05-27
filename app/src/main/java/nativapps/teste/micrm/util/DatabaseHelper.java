package nativapps.teste.micrm.util;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import nativapps.teste.micrm.R;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    private static final String DATABASE_NAME = "micrm.db";
    private static final int DATABASE_VERSION = 1;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            //TableUtils.createTable(connectionSource, CustomObject.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

        @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            //TableUtils.dropTable(connectionSource, CustomObject.class, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        super.close();
    }
}
