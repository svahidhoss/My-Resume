package com.vahid.myresume;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.design.BuildConfig;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SimpleAdapter;

import com.example.vahid.myresume.R;
import com.vahid.myresume.util.ActivityUtil;


/**
 * Created by Vahid on 2015-11-07.
 * <p/>
 * The ResumeFragment does the job displaying my resume.
 */
public class ResumeFragment extends Fragment implements View.OnClickListener {

    // TAG for logging
    public static final String TAG = "ResumeFragment";


    // The SimpleAdapter adapts the data about transfers to rows in the UI
    private SimpleAdapter mSimpleAdapter;

    // UI elements:
    private Button mBtnPauseResumeUpload, mBtnCancelUpload;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the data from any transfers that have already happened,
        initData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.content_main, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initUI();
    }


    /**
     * Gets all relevant transfers from the Transfer Service for populating the UI.
     */
    private void initData() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
    }


    /**
     * Called once a video is selected. We check if the activity that was triggered was
     * Video Gallery (It is common to trigger different intents from
     * the same activity and expects result from each).
     *
     * @param requestCode Used to differentiate the purpose of the Activity call.
     * @param resultCode  Used to make sure the activity call was successful.
     * @param intent      The data coming from the external activity call.
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (resultCode == Activity.RESULT_OK && intent != null) {
            // The selected video file path in the phone and its title
            String selectedVideoPath, selectedVideoTitle;
            Uri selectedVideoUri;
            switch (requestCode) {
                case ActivityUtil.REQUEST_VIDEO_LOAD:
                    try {
                        selectedVideoUri = intent.getData();

                    } catch (Exception ex) {
                        if (BuildConfig.DEBUG) {
                            Log.e(TAG, ex.getMessage());
                        }
                        // Video selection failed, advise user
//                        ActivityUtil.toastShortMessage(getActivity(), getString(R.string.prompt_failed_selection));
                    }
                    break;
            }
        }
    }


    /**
     * Manages all onClickUploadFragment events on buttons of this activity.
     *
     * @param view The view that its onClick event is handled.
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            default:
                break;
        }

    }

    /**
     * Initializes the UI for this activity accordingly.
     */
    private void initUI() {

    }


    /**
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case ActivityUtil.REQUEST_VIDEO_LOAD: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(Intent.ACTION_PICK,
                            MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, ActivityUtil.REQUEST_VIDEO_LOAD);
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
//                    ActivityUtil.toastShortMessage(getActivity(), getString(R.string.write_ssd_permission_denied));
                }
            }
            case ActivityUtil.REQUEST_VIDEO_CAPTURE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                    startActivityForResult(intent, ActivityUtil.REQUEST_VIDEO_CAPTURE);
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
//                    ActivityUtil.toastShortMessage(getActivity(), getString(R.string.camera_permission_denied));
                }
            }
            // Other 'case' lines to check for other permissions this app might request
        }
    }
}