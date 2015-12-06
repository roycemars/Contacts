package c.mars.contacts;

import android.app.Application;

/**
 * Created by Constantine Mars on 12/6/15.
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ContactsHelper.init(this);
    }
}
