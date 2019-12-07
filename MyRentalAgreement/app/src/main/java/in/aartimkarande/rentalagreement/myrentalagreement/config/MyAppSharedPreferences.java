package in.aartimkarande.rentalagreement.myrentalagreement.config;

import android.content.Context;
import android.content.SharedPreferences;

public class MyAppSharedPreferences {

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context context;
    private int PRIVATE_MODE = 0;

    public static final String SHARED_PREF_NAME = "MyApp";

    public static final String LOGGEDIN= "logged_in";

    public static final String USR_ID = "user_id";
    public static final String USERNAME = "username";

    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";

    public MyAppSharedPreferences(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(SHARED_PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

}
