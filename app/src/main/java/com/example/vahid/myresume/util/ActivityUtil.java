package com.example.vahid.myresume.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.widget.Toast;

import java.util.Locale;

/**
 * Handles general activity view controller utility tools used throughout the app.
 * <p/>
 * Created by Vahid on 2016-06-13.
 */
public class ActivityUtil {

    public static final int REQUEST_VIDEO_LOAD = 1;
    public static final int REQUEST_VIDEO_CAPTURE = 2;


    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private static String[] PERMISSIONS_CAMERA = {
            Manifest.permission.CAMERA
    };


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }


    /**
     * Displays a short toast message on the screen.
     *
     * @param activity The activity that short message is displayed on it.
     * @param message  The message to be displayed.
     */
    public static void toastShortMessage(final Activity activity, final String message) {
        if (!TextUtils.isEmpty(message)) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    /**
     * Displays a long toast message on the screen.
     *
     * @param activity The activity that short message is displayed on it.
     * @param message  The message to be displayed.
     */
    public static void toastLongMessage(final Activity activity, final String message) {
        if (!TextUtils.isEmpty(message)) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
                }
            });
        }
    }


    /**
     * Checks if the app has permission to write to device storage
     * <p/>
     * If the app does not has permission then the user will be prompted to grant permissions
     */
    public static boolean verifyStoragePermission(Activity activity) {
        // Check if we have write permission
        int writePermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int readPermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);
        boolean result = true;

        if (writePermission != PackageManager.PERMISSION_GRANTED ||
                readPermission != PackageManager.PERMISSION_GRANTED) {
            result = false;
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_VIDEO_LOAD);
        }

        return result;
    }


    /**
     * Checks if the app has permission to use camera
     * <p/>
     * If the app does not has permission then the user will be prompted to grant permissions
     */
    public static boolean verifyCameraPermission(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.CAMERA);

        boolean result = true;
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(activity, PERMISSIONS_CAMERA, REQUEST_VIDEO_CAPTURE);
            result = false;
        }

        return result;
    }

    // ********* Settings


    /**
     * This method sets the locale data from pre saved sharedPreference,
     * If not used, the app might forget the user's language after it
     * clears the memory.
     *
     * @param resource
     * @param applicationContext
     */
    public static void setLanguage(Resources resource, Context applicationContext) {
        SharedPreferences sharedPreference = PreferenceManager.getDefaultSharedPreferences(applicationContext);
        Locale locale = null;
        if ("fa".equalsIgnoreCase(sharedPreference.getString("language", null))) {
            locale = new Locale("fa", "IR");
            ;
        } else if ("en".equalsIgnoreCase(sharedPreference.getString("language", null))) {
            locale = Locale.ENGLISH;
        } else {
            locale = Locale.getDefault();
        }
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        applicationContext.getResources().updateConfiguration(configuration, null);
    }


    /**
     * Determine if the device is a tablet (i.e. it has a large screen).
     *
     * @param context The calling context.
     */
    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
}
