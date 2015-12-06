package c.mars.contacts;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.text)
    TextView textView;
    private Integer expectedCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Timber.plant(new Timber.DebugTree());
        ButterKnife.bind(this);

        expectedCode = PermissionsHelper.requestPermission(this, Manifest.permission.READ_CONTACTS);
        if(expectedCode==null) {
            showContacts();
        }
    }

    private void showContacts(){
        ContactsHelper.getContacts()
                .limit(10)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(contact -> {
                    String s = contact.toString() + "\n";
                    textView.append(s);
                    Timber.d(s);
                }, throwable -> {
                    String e = throwable.getLocalizedMessage();
                    textView.append(e);
                    Timber.e(e);
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        PermissionsHelper.onRequestPermissionsResult(granted -> showContacts()
                , expectedCode, requestCode, permissions, grantResults);

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
