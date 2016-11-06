package com.example.vahid.myresume;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
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

import com.example.vahid.myresume.util.ActivityUtil;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    // Used for exiting on pressing back double
    private boolean doubleBackToExitIsPressedOnce = false;
    private static final int BACK_PRESS_TIME = 2000; // 2s

    // View Elements
    private TextView tvTitle, tvContents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvContents = (TextView) findViewById(R.id.tv_contents);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
                tvTitle.setText(getString(R.string.nav_drawer_summary_item));
                tvContents.setText(getString(R.string.contents_summary));
                break;
            /*case R.id.nav_gallery: // Handle the camera action
                break;*/
            case R.id.nav_experience:
                tvTitle.setText(getString(R.string.nav_drawer_experience_item));
                tvContents.setText(getString(R.string.contents_experience));
                break;
            case R.id.nav_education:
                tvTitle.setText(getString(R.string.nav_drawer_education_item));
                tvContents.setText(getString(R.string.contents_education));
                break;
            case R.id.nav_certificates:
                tvTitle.setText(getString(R.string.nav_drawer_certificates_item));
                tvContents.setText(getString(R.string.contents_certificates));
                break;
            case R.id.nav_skills:
                tvTitle.setText(getString(R.string.nav_drawer_skills_item));
                tvContents.setText(getString(R.string.contents_skills));
                break;
            case R.id.nav_contact:
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
}
