package com.stabus.app;

import android.support.annotation.MenuRes;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.ActionMode;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.stabus.app.Interface.ISetListener;

public class MainActivity extends AppCompatActivity implements ISetListener {

    private Toolbar mToolbar;
    private TextView mTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //define id
        mToolbar = findViewById(R.id.toolbarBK);
        mTitle = findViewById(R.id.tvTitle);
        //set action bar
        setSupportActionBar(mToolbar);

        BottomNavigationView navigation = findViewById(R.id.bottomNav);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if (savedInstanceState==null){
            setFragment(new BahanBaku(),getString(R.string.BahanBaku),false,null);
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment fragment=null ;
            switch (menuItem.getItemId()){
                case R.id.navBahan:
                    //setmToolbar("Bahan Baku", R.drawable.ic_home_white);
                    fragment = new BahanBaku();
                    break;
                case R.id.navProduk:
                    //setmToolbar("Produk", R.drawable.ic_home_white);
                    fragment = new BahanBaku();
                    break;
                case R.id.navKalkulator:
                    //setmToolbar("Kalkulator", R.drawable.ic_home_white);
                    fragment = new BahanBaku();
                    break;
                case R.id.navRiwayat:
                    //setmToolbar("Riwayat", R.drawable.ic_home_white);
                    fragment = new BahanBaku();
                    break;
            }
            setFragment(fragment,getString(R.string.BahanBaku),false,null);
            return true;
        }
    };

    /*    SETTING FRAGMENT     */
    private void setFragment(Fragment fragment, String tag, boolean addToBackStack, Bundle bundle){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        //AMBIL DATA DARI FRAGMENT BAHAN BAKU
        if (bundle != null){
            fragment.setArguments(bundle);
        }
        //CEK APAKAH INI FRAGMENT AWAL ATAU BUKAN
        if (findViewById(R.id.frameContainer)==null){
            fragmentTransaction.add(R.id.frameContainer, fragment,tag);
        }
        fragmentTransaction.replace(R.id.frameContainer, fragment, tag);
        if (addToBackStack){
            fragmentTransaction.addToBackStack(tag);
        }
        fragmentTransaction.commit();
    }

    @Override
    public void setToolbarTitle(String toolbarTitle) {
        mTitle.setText(toolbarTitle);
    }

    @Override
    public void setNavigationIcon(int resId, boolean backStack) {
        mToolbar.setNavigationIcon(resId);
        if (backStack) mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        if (!backStack)mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void setNavigationListener(int resId, View.OnClickListener listener) {
        mToolbar.setNavigationIcon(resId);
        mToolbar.setNavigationOnClickListener(listener);
    }

    @Override
    public void inflateFragment(String fragmentTag, Bundle bundle) {
        if (fragmentTag.equals(getString(R.string.HargaBahanBaku))){
            HargaBahanBaku fragment = new HargaBahanBaku();
            setFragment(fragment, fragmentTag, true, bundle);
        }
    }

    @Override
    public void setRecyclerView(View view, RecyclerView recyclerView, RecyclerView.Adapter mAdapter) {
        recyclerView.setHasFixedSize(false);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }



}
