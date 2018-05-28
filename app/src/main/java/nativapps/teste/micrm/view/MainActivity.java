package nativapps.teste.micrm.view;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import nativapps.teste.micrm.R;
import nativapps.teste.micrm.util.ActivityUtil;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @ViewById(R.id.toolbar)
    Toolbar toolbar;

    @ViewById(R.id.drawer_layout)
    DrawerLayout drawer;

    @ViewById(R.id.nav_view)
    NavigationView navigationView;

    @AfterViews
    void afterViews() {
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        switchFragment("HomeFragment");
    }

    @Override
    public void onBackPressed() {
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

        if (id == R.id.nav_home) {
            switchFragment("HomeFragment");
        } else if (id == R.id.nav_people) {
            switchFragment("PeopleFragment");
        } else if (id == R.id.nav_institution) {
            switchFragment("InstitutionFragment");
        } else if (id == R.id.nav_business) {
            switchFragment("BusinessFragment");
        } else if (id == R.id.nav_activities) {
            switchFragment("ActivitiesFragment");
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void switchFragment(String fragment) {
        switch (fragment) {
            case "ActivitiesFragment":
                ActivityUtil.switchFragment(
                        new ActivitiesFragment_()
                        , R.id.home_container, this);
                break;
            case "BusinessFragment":
                ActivityUtil.switchFragment(
                        new BusinessFragment_()
                        , R.id.home_container, this);
                break;
            case "HomeFragment":
                ActivityUtil.switchFragment(
                        new HomeFragment_()
                        , R.id.home_container, this);
                break;
            case "InstitutionFragment":
                ActivityUtil.switchFragment(
                        new InstitutionFragment_()
                        , R.id.home_container, this);
                break;
            case "PeopleFragment":
                ActivityUtil.switchFragment(
                        new PeopleFragment_()
                        , R.id.home_container, this);
                break;
        }
    }
}
