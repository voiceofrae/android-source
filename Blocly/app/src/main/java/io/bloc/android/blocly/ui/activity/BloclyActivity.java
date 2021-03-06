package io.bloc.android.blocly.ui.activity;

import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import io.bloc.android.blocly.BloclyApplication;
import io.bloc.android.blocly.R;
import io.bloc.android.blocly.api.model.database.DatabaseOpenHelper;
import io.bloc.android.blocly.api.model.database.table.RssItemTable;
import io.bloc.android.blocly.ui.adapter.ItemAdapter;
import io.bloc.android.blocly.ui.adapter.NavigationDrawerAdapter;

/**
 * Created by ReneeCS on 3/17/15.
 */
public class BloclyActivity extends ActionBarActivity {

    private ItemAdapter itemAdapter;
    private ActionBarDrawerToggle drawerToggle;
    private DrawerLayout drawerLayout;
    private NavigationDrawerAdapter navigationDrawerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blocly);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tb_activity_blocly);
        setSupportActionBar(toolbar);

        itemAdapter = new ItemAdapter();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_activity_blocly);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(itemAdapter);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerLayout = (DrawerLayout) findViewById(R.id.dl_activity_blocly);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, 0, 0);
        drawerLayout.setDrawerListener(drawerToggle);

        navigationDrawerAdapter = new NavigationDrawerAdapter();
        RecyclerView navigationRecyclerView = (RecyclerView) findViewById(R.id.rv_nav_activity_blocly);
        navigationRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        navigationRecyclerView.setItemAnimator(new DefaultItemAnimator());
        navigationRecyclerView.setAdapter(navigationDrawerAdapter);

    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
//        drawerLayout.openDrawer(Gravity.LEFT); // makes drawer open by default
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Methods to query the database

    DatabaseOpenHelper bloclyOpenHelper = new DatabaseOpenHelper(BloclyApplication.getSharedInstance()); // with help from Tony
    SQLiteDatabase readableDatabase = bloclyOpenHelper.getReadableDatabase();

    RssItemTable rssItemTable = new RssItemTable(); // instance of RssItemTable for us to use here

    public Cursor cursor =  readableDatabase.query(false, rssItemTable.getName(), null, null, null, null, null, "pub_date", " 20");

}

