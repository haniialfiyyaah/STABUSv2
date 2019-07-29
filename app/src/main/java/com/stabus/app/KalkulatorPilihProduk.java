package com.stabus.app;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.stabus.app.Database.DBMBahan;
import com.stabus.app.Database.DBMProduk;
import com.stabus.app.Interface.ISetListener;
import com.stabus.app.Interface.OnListener;
import com.stabus.app.Model.CollectBahanBaku;
import com.stabus.app.Model.CollectBahanCRUD;
import com.stabus.app.Model.MBahanBaku;
import com.stabus.app.Model.MProdukRelasi;
import com.stabus.app.RecyclerView.BahanBakuAdapter;
import com.stabus.app.RecyclerView.KalkulatorProdukAdapter;
import com.stabus.app.RecyclerView.ProdukAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class KalkulatorPilihProduk extends Fragment implements OnListener, AdapterView.OnItemSelectedListener {

    private ISetListener mISetListener;
    private DBMProduk dbmProduk;
    private BahanBakuAdapter bhAdapter;
    //list
    private List<MProdukRelasi> produkList;
    private List<MProdukRelasi> produkListFull;
    private List<MProdukRelasi> selectedList;

    private Toolbar toolbar;
    private ScrollView scrollViewBah;
    private RecyclerView recyclerViewBah;
    private FrameLayout frameLayout;
    private FloatingActionButton fab;
    private Spinner spinner;
    ArrayList arraycountries;

    int id_produk;
    int jumlah_lama;
    String nama;
    String[] options;

    CollectBahanCRUD crud;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kalkulator_produk,container,false);
        initView(view);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        initObject(view);
        disableNested();
        return view;
    }

    private void initView(View view){
        toolbar = view.findViewById(R.id.toolbarPilihProduk);
        scrollViewBah = view.findViewById(R.id.scrollRVBahan);
        recyclerViewBah = view.findViewById(R.id.rvPilihBah);
        frameLayout = view.findViewById(R.id.frameEmptyPro);
        fab = view.findViewById(R.id.fabPilihKalkPro);
        spinner = view.findViewById(R.id.spinnerPro);
    }

    private void disableNested(){
        recyclerViewBah.setNestedScrollingEnabled(false);
    }

    private void initObject(View view){
        dbmProduk = new DBMProduk(view);
        crud = new CollectBahanCRUD(CollectBahanBaku.getBahanBakuList());
        produkListFull = new ArrayList<>();
        dbmProduk.getAllProduk(produkListFull);

        setRV(view);
    }

    private void setRV(View view){
        produkList = new ArrayList<>();

        callSpinner(view);
        refreshList();
    }
    private void refreshList(){
        dbmProduk.getAllProduk(produkList);
        cekEmptyList();
    }
    private void cekEmptyList(){
        if (produkList.size()==0){
            scrollViewBah.setVisibility(View.GONE);
            frameLayout.setVisibility(View.VISIBLE);
        }else {
            scrollViewBah.setVisibility(View.VISIBLE);
            frameLayout.setVisibility(View.GONE);
        }
    }

    private void callSpinner(View view){
        arraycountries= dbmProduk.getAllNamaProduk();
        ArrayAdapter adapter = new ArrayAdapter(view.getContext(), android.R.layout.simple_list_item_1, arraycountries);
        // Specify layout to be used when list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Applying the adapter to our spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void OnClickListener(int position, View view) {
        Log.d("Posisi",""+position);
        Log.d("Nama",nama);
        Log.d("namaaaa",view.toString());


    }

    @Override
    public boolean OnLongListener(int position, View view) {
        return false;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mISetListener = (ISetListener)getActivity();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        id_produk = produkList.get(position).getId_produk();
        nama = produkList.get(position).getNama();
        jumlah_lama = produkList.get(position).getJumlah();

        dbmProduk.getAllRelasi(crud.getBahanBakuList(),id_produk,jumlah_lama);
        recyclerViewBah.setLayoutManager(new LinearLayoutManager(view.getContext()));
        bhAdapter = new BahanBakuAdapter(crud.getBahanBakuList(),this, false);
        mISetListener.setRecyclerView(new LinearLayoutManager(view.getContext()), recyclerViewBah, bhAdapter);
        bhAdapter.notifyDataSetChanged();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
