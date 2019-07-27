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
import android.widget.FrameLayout;
import android.widget.ScrollView;

import com.stabus.app.Database.DBMProduk;
import com.stabus.app.Interface.ISetListener;
import com.stabus.app.Interface.OnListener;
import com.stabus.app.Model.CollectBahanBaku;
import com.stabus.app.Model.CollectBahanCRUD;
import com.stabus.app.Model.MProdukRelasi;
import com.stabus.app.RecyclerView.BahanBakuAdapter;
import com.stabus.app.RecyclerView.KalkulatorProdukAdapter;
import com.stabus.app.RecyclerView.ProdukAdapter;

import java.util.ArrayList;
import java.util.List;


public class KalkulatorPilihProduk extends Fragment implements OnListener {

    private ISetListener mISetListener;
    private DBMProduk dbmProduk;
    private ProdukAdapter produkAdapter;
    private KalkulatorProdukAdapter mAdapter;
    BahanBakuAdapter bhAdapter;
    //list
    private List<MProdukRelasi> produkList;
    private List<MProdukRelasi> produkListFull;
    private List<MProdukRelasi> selectedList;

    private Toolbar toolbar;
    private ScrollView scrollViewPro, scrollViewBah;
    private RecyclerView recyclerViewPro, recyclerViewBah;
    private FrameLayout frameLayout;
    private FloatingActionButton fab;

    int id_produk;
    int jumlah_lama;
    String nama;

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
        scrollViewPro = view.findViewById(R.id.scrollRVPro);
        scrollViewBah = view.findViewById(R.id.scrollRVBahan);
        recyclerViewPro = view.findViewById(R.id.rvPilihProduk);
        recyclerViewBah = view.findViewById(R.id.rvPilihBah);
        frameLayout = view.findViewById(R.id.frameEmptyPro);
        fab = view.findViewById(R.id.fabPilihKalkPro);
    }

    private void disableNested(){
        recyclerViewPro.setNestedScrollingEnabled(false);
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
        mAdapter = new KalkulatorProdukAdapter(produkList, this);
        mISetListener.setRecyclerView(new LinearLayoutManager(view.getContext()), recyclerViewPro, mAdapter);
        mAdapter.notifyDataSetChanged();

        refreshList();
    }
    private void refreshList(){
        dbmProduk.getAllProduk(produkList);
        mAdapter.notifyDataSetChanged();
        cekEmptyList();
    }
    private void cekEmptyList(){
        if (produkList.size()==0){
            scrollViewPro.setVisibility(View.GONE);
            scrollViewBah.setVisibility(View.GONE);
            frameLayout.setVisibility(View.VISIBLE);
        }else {
            scrollViewPro.setVisibility(View.VISIBLE);
            scrollViewBah.setVisibility(View.VISIBLE);
            frameLayout.setVisibility(View.GONE);
        }
    }


    @Override
    public void OnClickListener(int position, View view) {
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
    public boolean OnLongListener(int position, View view) {
        return false;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mISetListener = (ISetListener)getActivity();
    }

}
