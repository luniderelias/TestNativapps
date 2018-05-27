package nativapps.teste.micrm.util;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;

import java.util.Locale;

public class LocaleManager {

    public static Context setLocale(Context c) {
        return setNewLocale(c, getLanguage(c));
    }

    public static Context setNewLocale(Context c, String language) {
        persistLanguage(c, language);
        c = updateResources(c, language);
        return c;
    }

    public static String getLanguage(Context c) {
        return c.getSharedPreferences("LANGUAGE", 0).getString("LANGUAGE", "en");
    }

    private static void persistLanguage(Context c, String language) {
        c.getSharedPreferences("LANGUAGE", 0).edit().putString("LANGUAGE", language).apply();
    }

    private static Context updateResources(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Resources res = context.getResources();
        Configuration config = new Configuration(res.getConfiguration());
        if (Build.VERSION.SDK_INT >= 17) {
            config.setLocale(locale);
            context = context.createConfigurationContext(config);
        } else {
            config.locale = locale;
            res.updateConfiguration(config, res.getDisplayMetrics());
        }
        return context;
    }
}
