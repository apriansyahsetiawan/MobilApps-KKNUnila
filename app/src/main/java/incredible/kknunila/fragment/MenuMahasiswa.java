package incredible.kknunila.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import incredible.kknunila.R;
import incredible.kknunila.SessionManager;
import incredible.kknunila.menu.MainActivity;

public class MenuMahasiswa extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView sessionNPM;
    Toolbar toolbar;
    DrawerLayout drawer;
    NavigationView navigationView;
    Fragment fragment = null;
    FragmentManager fragmentManager;
    SessionManager sesi;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_mahasiswa);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerOpened(View drawer) {
                super.onDrawerOpened(drawer);
                sessionNPM = (TextView) findViewById(R.id.sessionNPM);
                sesi = new SessionManager(MenuMahasiswa.this);
                sessionNPM.setText(sesi.getNPM());
            }

            @Override
            public void onDrawerClosed(View drawer) {
                super.onDrawerClosed(drawer);
            }
        };


        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            fragment = new Home();
            callFragment(fragment);

        }

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.mhs_home) {
            fragment = new Home();
            callFragment(fragment);

        } else if (id == R.id.mhs_infokelompok) {
            fragment = new InfoKelompok();
            callFragment(fragment);

        } else if (id == R.id.mhs_pengumuman) {
            fragment = new InfoDPL();
            callFragment(fragment);

        } else if (id == R.id.mhs_nilai) {
            fragment = new LihatNilai();
            callFragment(fragment);

        } else if (id == R.id.mhs_laporan) {
            fragment = new LihatLaporan();
            callFragment(fragment);

        } else if (id == R.id.mhs_logout) {
            AlertDialog.Builder logout = new AlertDialog.Builder(MenuMahasiswa.this);
            logout.setTitle("Loggin Out...");
            logout.setMessage("Anda yakin ingin keluar dari akun ini?");
            logout.setIcon(R.drawable.ic_exit_to_app_black_24dp);
            logout.setPositiveButton("Iya", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    sesi.logout();
                    Intent i = new Intent(MenuMahasiswa.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
            });
            logout.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            logout.show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void callFragment(Fragment fragment) {
        fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

}
