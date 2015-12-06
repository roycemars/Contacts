package c.mars.contacts;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by Constantine Mars on 12/6/15.
 */
public class ContactsHelper {
    private static Context context;

    @Data @AllArgsConstructor
    public static class Contact{
        private String name;
        private String phone;

        public String toString() {
            return name + " " + phone;
        }
    }

    public static void init(Context context){
        ContactsHelper.context=context;
    }

    public static rx.Observable<Contact> getContacts(){
        Cursor cursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);

        rx.Observable<Contact> contacts = Observable.create(subscriber -> {
            if(cursor!=null && cursor.getCount()>0) {
                while (cursor.moveToNext()) {
                    String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    subscriber.onNext(new Contact(name, phoneNumber));
                }
                cursor.close();
                subscriber.onCompleted();
                return;
            }

            subscriber.onError(new Exception("No contacts found!"));
        });
        return contacts;
    }
}
