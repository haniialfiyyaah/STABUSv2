package com.stabus.app;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.stabus.app.Interface.ISetListener;
import com.stabus.app.TBahanBaku.BahanBaku;
import com.stabus.app.TBahanBaku.HargaBahanBaku;
import com.stabus.app.TKalkulator.KalkulatorGetHarga;
import com.stabus.app.TKalkulator.KalkulatorSetHarga;
import com.stabus.app.TKalkulator.KalkulatorSetKebutuhan;
import com.stabus.app.TKalkulator.KalkulatorSetKemasan;
import com.stabus.app.TKalkulator.KalkulatorSetMargin;
import com.stabus.app.TProduk.Produk;
import com.stabus.app.TProduk.ProdukTambah;
import com.stabus.app.TKalkulator.Kalkulator;
import com.stabus.app.TKalkulator.KalkulatorPilihProduk;

public class MainActivity extends AppCompatActivity implements ISetListener {

    private Toolbar mToolbar;
    private TextView mTitle;

    private BottomNavigationView navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //define id
        mToolbar = findViewById(R.id.toolbarBK);
        mToolbar.setVisibility(View.GONE);
        mTitle = findViewById(R.id.tvTitle);
        //set action bar
        setSupportActionBar(mToolbar);
        setTitle("");

        navigation = findViewById(R.id.bottomNav);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navKalkulator);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()){
                case R.id.navBahan:
                    //setmToolbar("Bahan Baku", R.drawable.ic_home_white);
                    inflateFragment(getString(R.string.BahanBaku),null);
                    break;
                case R.id.navProduk:
                    //setmToolbar("Produk", R.drawable.ic_home_white);
                    inflateFragment(getString(R.string.Produk),null);
                    break;
                case R.id.navKalkulator:
                    //setmToolbar("Kalkulator", R.drawable.ic_home_white);
                    inflateFragment(getString(R.string.Kalkulator), null);
                    break;
                case R.id.navRiwayat:
                    //setmToolbar("Riwayat", R.drawable.ic_home_white);

                    break;
            }
            //setFragment(fragment,getString(R.string.BahanBaku),false,null);

            return true;
        }
    };

    /*    SETTING FRAGMENT     */
    private void setFragment(Fragment fragment, String tag, boolean addToBackStack, Bundle bundle){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //AMBIL DATA DARI FRAGMENT BAHAN BAKU
        if (bundle != null){
            fragment.setArguments(bundle);
        }
        fragmentTransaction.replace(R.id.frameContainer, fragment, tag);
        if (addToBackStack){
            fragmentTransaction.addToBackStack(tag);
        }
        fragmentTransaction.commit();
    }

    @Override
    public void inflateFragment(String fragmentTag, Bundle bundle) {
        //main
        if (fragmentTag.equals(getString(R.string.BahanBaku))){
            BahanBaku fragment = new BahanBaku();
            setFragment(fragment, fragmentTag, false, null);
        }
        //main
        if (fragmentTag.equals(getString(R.string.Produk))){
            Produk fragment = new Produk();
            setFragment(fragment, fragmentTag, false, null);
        }
        if (fragmentTag.equals(getString(R.string.HargaBahanBaku))){
            HargaBahanBaku fragment = new HargaBahanBaku();
            setFragment(fragment, fragmentTag, true, bundle);
        }
        if (fragmentTag.equals(getString(R.string.TambahProduk))){
            ProdukTambah fragment = new ProdukTambah();
            setFragment(fragment, fragmentTag, true, bundle);
        }
        //main
        if (fragmentTag.equals(getString(R.string.Kalkulator))){
            Kalkulator fragment = new Kalkulator();
            setFragment(fragment, fragmentTag, false, null);
        }
        if (fragmentTag.equals(getString(R.string.KalkulatorPilihProduk))){
            KalkulatorPilihProduk fragment = new KalkulatorPilihProduk();
            setFragment(fragment, fragmentTag, true, null);
        }
        if (fragmentTag.equals(getString(R.string.KalkulatorSetHarga))){
            KalkulatorSetHarga fragment = new KalkulatorSetHarga();
            setFragment(fragment, fragmentTag, true, null);
        }
        if (fragmentTag.equals(getString(R.string.KalkulatorSetKemasan))){
            KalkulatorSetKemasan fragment = new KalkulatorSetKemasan();
            setFragment(fragment, fragmentTag, true, null);
        }
        if (fragmentTag.equals(getString(R.string.KalkulatorSetKebutuhan))){
            KalkulatorSetKebutuhan fragment = new KalkulatorSetKebutuhan();
            setFragment(fragment, fragmentTag, true, null);
        }
        if (fragmentTag.equals(getString(R.string.KalkulatorSetMargin))){
            KalkulatorSetMargin fragment = new KalkulatorSetMargin();
            setFragment(fragment, fragmentTag, true, null);
        }
        if (fragmentTag.equals(getString(R.string.KalkulatorGetHarga))){
            KalkulatorGetHarga fragment = new KalkulatorGetHarga();
            setFragment(fragment, fragmentTag, true, null);
        }
    }

    @Override
    public void setRecyclerView(RecyclerView.LayoutManager layoutManager, RecyclerView recyclerView, RecyclerView.Adapter mAdapter) {
        recyclerView.setHasFixedSize(false);
        //RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onBackPressed() {
        Fragment f = getSupportFragmentManager().findFragmentById(R.id.frameContainer);
        if (f instanceof BahanBaku || f instanceof Produk){
            navigation.setSelectedItemId(R.id.navKalkulator);
        }else if (f instanceof Kalkulator){
            ((Kalkulator)f).onBackPressed();
        }
        else super.onBackPressed();
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mTitle.setCompoundDrawables(null,null,null,null);
    }

}
