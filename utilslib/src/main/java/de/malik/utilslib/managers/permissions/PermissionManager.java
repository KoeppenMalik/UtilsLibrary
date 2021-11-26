package de.malik.utilslib.managers.permissions;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PermissionManager {

    /**
     * the tag used to output text with Log class
     */
    public static final String TAG = PermissionManager.class.getName();

    /**
     * the request code to request storage permissions on API R
     */
    public static final int STORAGE_PERMISSIONS_REQUEST_CODE_API_R = 1;

    /**
     * the request code to request storage permissions on APi versions below R
     */
    public static final int STORAGE_PERMISSIONS_REQUEST_CODE_API_BELOW_R = 2;

    /**
     * requests all the storage permissions for API R and below
     * @param origin the activity from where this method is called
     */
    public static void askForStoragePermissions(@NonNull Activity origin) {
        if (checkStoragePermissions(origin.getApplicationContext())) {
            Log.i(TAG, "Permissions Granted");
        }
        else requestStoragePermissions(origin);
    }

    /**
     * checks if the app has all storage permissions
     * @param context context of the activity
     * @return true if the app has all storage permissions, false otherwise
     */
    public static boolean checkStoragePermissions(@NonNull Context context) {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.R) {
            return Environment.isExternalStorageManager();
        }
        else {
            return ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        }
    }

    /**
     * override the onActivityResult method in an activity and call this method. This method manages
     * the permission request result
     * @param origin the activity from where this method is called
     * @param requestCode the request code which was used to request the permissions
     * @param resultCode the code which contains the information if the permissions were granted or not
     */
    public static void manageActivityResult(@NonNull Activity origin, int requestCode, int resultCode) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == STORAGE_PERMISSIONS_REQUEST_CODE_API_R) {
                if (Build.VERSION.SDK_INT == Build.VERSION_CODES.R) {
                    if (Environment.isExternalStorageManager()) {
                        Log.i(TAG, "Permissions Granted: Android 11");
                    }
                    else {
                        requestStoragePermissions(origin);
                    }
                }
            }
        }
    }

    /**
     * override the onRequestPermissionsResult method in an activity and call this method. This method manages
     * the permission request result
     * @param origin the activity from where this method is called
     * @param requestCode the request code which was used to request the permissions
     * @param grantResults and array of integer which contains the information if the permissions were granted or not
     */
    public static void manageRequestPermissionsResult(@NonNull Activity origin, int requestCode, int[] grantResults) {
        if (grantResults.length > 0) {
            if (requestCode == STORAGE_PERMISSIONS_REQUEST_CODE_API_BELOW_R) {
                boolean readExternalStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                if (readExternalStorage) {
                    Log.i(TAG, "Permissions Granted: Android 10 and below");
                }
                else requestStoragePermissions(origin);
            }
        }
    }

    /**
     * requests all the storage permissions. This works for android API R and below. In case of API R,
     * this method will start a activity for result on the origin activity.
     * @param origin the origin activity
     */
    private static void requestStoragePermissions(@NonNull Activity origin) {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.R) {
            try {
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setData(Uri.parse(String.format("package:%s", origin.getApplicationContext().getPackageName())));
                origin.startActivityForResult(intent, STORAGE_PERMISSIONS_REQUEST_CODE_API_R);
            } catch (Exception ex) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                origin.startActivityForResult(intent, STORAGE_PERMISSIONS_REQUEST_CODE_API_R);
            }
        }
        else {
            ActivityCompat.requestPermissions(origin, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSIONS_REQUEST_CODE_API_BELOW_R);
        }
    }
}
