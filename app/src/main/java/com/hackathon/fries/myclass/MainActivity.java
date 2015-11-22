package com.hackathon.fries.myclass;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hackathon.fries.myclass.fragment.LopFragment;
import com.hackathon.fries.myclass.fragment.ThoiKhoaBieuFragment;
import com.hackathon.fries.myclass.fragment.TimelineFragment;
import com.hackathon.fries.myclass.helper.SQLiteHandler;
import com.hackathon.fries.myclass.helper.SessionManager;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int REQUEST_CODE_EDIT = 1234;
    private static final String TAG = "MainActivity";
    private static final String URL_DOWNLOAD_APK = "https://www.dropbox.com/s/nscoya56e89bffk/LopToi%20-%20Fries.apk?dl=0";
    private LopFragment lopFragment = new LopFragment();
    private ThoiKhoaBieuFragment thoiKhoaBieuFragment = new ThoiKhoaBieuFragment();

    private SessionManager session;
    private SQLiteHandler sqlite;

    private TextView tvName, tvEmail;
    private CircleImageView ivAva;
    private View header;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        toolbar.setNavigationIcon(R.mipmap.ic_launcher);
//        toolbar.setNavigationContentDescription("BackPressed");

//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        //session
        session = new SessionManager(getApplicationContext());

        //lay co so du lieu
        sqlite = new SQLiteHandler(getApplicationContext());

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        header = navigationView.getHeaderView(0);

        if (!session.isLoggedIn()) {
            logout();
        }
        initViews();
        initAllFragments();
    }

    private void initViews() {
        tvName = (TextView) header.findViewById(R.id.tv_name);
        tvEmail = (TextView) header.findViewById(R.id.tv_email);
        ivAva = (CircleImageView) header.findViewById(R.id.iv_avatar);

        ivAva.setFillColor(Color.WHITE);

        HashMap<String, String> user = sqlite.getUserDetails();
        tvName.setText(user.get(SQLiteHandler.KEY_NAME) + " (" + user.get(SQLiteHandler.KEY_TYPE) + ")");
        tvEmail.setText(user.get(SQLiteHandler.KEY_EMAIL));
    }

    private void startEditActivity() {
        Intent mIntent = new Intent(MainActivity.this, EditProfileActivity.class);
        HashMap<String, String> user = sqlite.getUserDetails();
        mIntent.putExtra("name", user.get("name"));
        mIntent.putExtra("email", user.get("email"));
        mIntent.putExtra("lop", user.get("lop"));
        mIntent.putExtra("mssv", user.get("mssv"));
        mIntent.putExtra("type", user.get("type"));

        startActivityForResult(mIntent, REQUEST_CODE_EDIT);
    }

    private void initAllFragments() {
        getFragmentManager().beginTransaction().
                add(R.id.container, lopFragment).show(lopFragment).commit();
    }

    private void hideAllFragment() {
//        getFragmentManager().beginTransaction().hide(lopFragment).commit();
    }

    private void showLopFragment() {
//        hideAllFragment();
        getFragmentManager().beginTransaction().replace(R.id.container, lopFragment).commit();
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
            return;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
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
        switch (item.getItemId()) {
            case R.id.action_sendAll:
                Toast.makeText(this, "Send to all", Toast.LENGTH_LONG).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()) {
            case R.id.nav_lopMonHoc:
//                showLopFragment();
//                lopFragment.showLopMonHoc();
                if (getFragmentManager().getBackStackEntryCount() > 0) {
                    getFragmentManager().popBackStack();
                }
                if (!lopFragment.isVisible()) {
                    showLopFragment();
                    Log.i(TAG, "is hidden");
                }
                lopFragment.showLopMonHoc();

                toolbar.setTitle("Lớp môn học");
                break;
            case R.id.nav_lopKhoaHoc:
                if (getFragmentManager().getBackStackEntryCount() > 0) {
                    getFragmentManager().popBackStack();
                }
                if (!lopFragment.isVisible()) {
                    showLopFragment();
                    Log.i(TAG, "is hidden");
                }
                lopFragment.showLopKhoaHoc();

                toolbar.setTitle("Lớp khoá học");
                break;
            case R.id.nav_nhom:
//                hideAllFragment();
                if (getFragmentManager().getBackStackEntryCount() > 0) {
                    getFragmentManager().popBackStack();
                }
                if (!lopFragment.isVisible()) {
                    showLopFragment();
                    Log.i(TAG, "is hidden");
                }
                lopFragment.showNhomAdt();

                toolbar.setTitle("Nhóm");
                break;
            case R.id.nav_setting:
                //replace fragment moi
//                hideAllFragment();
                toolbar.setTitle("Cài đặt");
                break;
            case R.id.nav_share:
//                hideAllFragment();
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Ứng dụng \"Lớp tôi\" thật tuyệt vời!\n" + URL_DOWNLOAD_APK);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                toolbar.setTitle("Chia sẻ");
                break;
            case R.id.nav_thoikhoabieu:
                if (getFragmentManager().getBackStackEntryCount() > 0) {
                    getFragmentManager().popBackStack();
                }
                //replace fragment moi
                getFragmentManager().beginTransaction().replace(R.id.container, thoiKhoaBieuFragment).commit();
                toolbar.setTitle("Thời khoá biểu");
                break;
            case R.id.nav_updateAccount:
//                hideAllFragment();
                startEditActivity();
                toolbar.setTitle(R.string.app_name);
                break;
            case R.id.nav_logout:
//                hideAllFragment();
                logout();
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logout() {
        // xoa session
        session.setLogin(false);

        // xoa user
        sqlite.deleteUsers();

        // thoat ra man hinh dang nhap
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);

        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_EDIT:
                if (resultCode == RESULT_OK) {
//                    String name = data.getStringExtra("name");
//                    String lop = data.getStringExtra("lop");
//                    String mssv = data.getStringExtra("mssv");
//                    String type = data.getStringExtra("type");

                    //Update csdl trong may va tren server
//                    sqlite.updateUser(lop);
//                    HashMap<String, String> user = sqlite.getUserDetails();
//                    String email = user.get("email");
//                    String uid = user.get("uid");
//                    String createAt = user.get("created_at");
////                    String mssv = user.get("mssv");
//                    String type = user.get("type");

//                    tvName.setText(name + "(" + type + ")");

                    checkLogDb();
                }
                break;
        }
    }


    public void goToTimeLine(String idData, int lopType) {
        //Lay thong tin cac bai dang tren server ve
        //goi timelinefragment
        //set data cho listview
        TimelineFragment timelineFragment = new TimelineFragment();
        Bundle b = new Bundle();
        b.putString("idData", idData);
        b.putInt("lopType", lopType);
        timelineFragment.setArguments(b);
//        timelineFragment.setIdLop(idData, lopType);

        Toast.makeText(getApplicationContext(), "Id: " + idData, Toast.LENGTH_LONG).show();

        getFragmentManager().beginTransaction().
                replace(R.id.container, timelineFragment).addToBackStack(null).commit();
    }

    private void checkLogDb() {
        HashMap<String, String> user = sqlite.getUserDetails();
        String name = user.get("name");
        String email = user.get("email");
        String uid = user.get("uid");
        String createAt = user.get("created_at");
        String mssv = user.get("mssv");
        String lop = user.get("lop");
        String type = user.get("type");

        Log.i(TAG, "check db name: " + name);
        Log.i(TAG, "check db email: " + email);
        Log.i(TAG, "check db uid: " + uid);
        Log.i(TAG, "check db create: " + createAt);
        Log.i(TAG, "check db mssv: " + mssv);
        Log.i(TAG, "check db lop: " + lop);
        Log.i(TAG, "check db type: " + type);
    }


}
