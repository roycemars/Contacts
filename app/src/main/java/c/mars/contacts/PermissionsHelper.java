package c.mars.contacts;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Constantine Mars on 12/6/15.
 */
public class PermissionsHelper {
    private AppCompatActivity activity;
    private static Integer requestCodeCounter = 0;

    public static Integer requestPermission(Activity activity, String permission) {

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return null;
        }

        Integer requestCode = requestCodeCounter++;
        // Should we show an explanation?
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {

            // Show an expanation to the user *asynchronously* -- don't block
            // this thread waiting for the user's response! After the user
            // sees the explanation, try again to request the permission.

        } else {

            // No explanation needed, we can request the permission.

            ActivityCompat.requestPermissions(activity, new String[]{permission}, requestCode);

            // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
            // app-defined int constant. The callback method gets the
            // result of the request.
        }

        return requestCode;
    }

    public static void onRequestPermissionsResult(OnPermissionGranted onPermissionGranted, final Integer expectedCode, int requestCode, String permissions[], int[] grantResults) {

        if(expectedCode != null && expectedCode == requestCode) {
            boolean granted = grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED;
            onPermissionGranted.call(granted);
        }
    }

    public interface OnPermissionGranted{
        void call(boolean granted);
    }

}
