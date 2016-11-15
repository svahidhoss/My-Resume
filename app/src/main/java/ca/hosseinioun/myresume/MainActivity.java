package ca.hosseinioun.myresume;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import ca.hosseinioun.myresume.util.ActivityUtil;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    // Used for exiting on pressing back double
    private boolean doubleBackToExitIsPressedOnce = false;
    private static final int BACK_PRESS_TIME = 2000; // 2s

    // View Elements
    private TextView mTvTitle, mTvContents;
    private FloatingActionButton mFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvContents = (TextView) findViewById(R.id.tv_contents);

        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mFab.setOnClickListener(this);

        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            /**
             * Called when a drawer has settled in a completely closed state.
             */
            @Override
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /**
             * Called when a drawer has settled in a completely open state.
             */
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Start fragment
//         startFragment(ResumeFragment.TAG);
    }

    /**
     * Using the following function of "clicking TWICE the back button to exit
     * app" has been implemented.
     */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            if (doubleBackToExitIsPressedOnce) {
                super.onBackPressed();
                return;
            }
            this.doubleBackToExitIsPressedOnce = true;
            ActivityUtil.toastShortMessage(this, getString(R.string.activity_main_exit_msg));
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitIsPressedOnce = false;
                }
            }, BACK_PRESS_TIME);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Handle navigation view item clicks here.
        switch (item.getItemId()) {
            case R.id.nav_summary:
                mTvTitle.setText(getString(R.string.nav_drawer_summary_item));
                mTvContents.setText(getString(R.string.contents_summary));
                break;
            /*case R.id.nav_gallery: // Handle the camera action
                break;*/
            case R.id.nav_experience:
                mTvTitle.setText(getString(R.string.nav_drawer_experience_item));
                mTvContents.setText(getString(R.string.contents_experience));
                break;
            case R.id.nav_education:
                mTvTitle.setText(getString(R.string.nav_drawer_education_item));
                mTvContents.setText(getString(R.string.contents_education));
                break;
            case R.id.nav_certificates:
                mTvTitle.setText(getString(R.string.nav_drawer_certificates_item));
                mTvContents.setText(getString(R.string.contents_certificates));
                break;
            case R.id.nav_skills:
                mTvTitle.setText(getString(R.string.nav_drawer_skills_item));
                mTvContents.setText(getString(R.string.contents_skills));
                break;
            case R.id.nav_contact:
                sendEmailToVahid();
                break;
            case R.id.nav_call:
                callVahid();
                break;
            case R.id.nav_linkedin:
                // TODO: Move the URI, provide webview inside the app
                Uri uri = Uri.parse(getString(R.string.my_linkedin_address));
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Calls Vahid's number.
     */
    private void callVahid() {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse(getString(R.string.phone_number)));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        startActivity(callIntent);
    }

    /**
     * Sends an email to Vahid by calling the mailto intent .
     */
    private void sendEmailToVahid() {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", getString(R.string.my_email_address), null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Networking Opportunity");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Dear Vahid,\n");
        startActivity(Intent.createChooser(emailIntent, "Send email..."));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                sendEmailToVahid();
                break;
        }
    }


    /**
     * Adds the video listing or upload video fragments to this activity.
     *
     * @param fragmentName Contains the fragment tag to identify the fragment.
     */
    /*private void startFragment(final String fragmentName) {
        Fragment startingFragment;
        switch (fragmentName) {
            case ResumeFragment.TAG:
                // redisplay the upload mFab
                mFab.setVisibility(View.VISIBLE);
                startingFragment = new ResumeFragment();
                // change title
                // setTitle(R.string.activity_main_drawer_title);
                break;
            default:
                // exit
                return;
        }


        // Asking the FragmentManager if the Fragment is already added:
        FragmentManager fragmentManager = getFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(fragmentName);
        if (fragment == null) {
            // fragment must be added
            fragmentManager.beginTransaction().replace(R.id.frame_layout_container, startingFragment,
                    fragmentName).commit();
        }
    }*/
}
