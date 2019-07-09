package com.stabus.app;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.stabus.app.Database.DBMProduk;
import com.stabus.app.RecyclerView.ProdukAdapter;

import java.util.ArrayList;

public class ProdukTambah extends Fragment implements View.OnClickListener {

    Toolbar toolbar;
    TextInputLayout txNama;
    TextInputLayout txJumlah;
    Spinner Spsatuan;
    FloatingActionButton fabAddBahan;
    RecyclerView rvBahan;

    String nama;
    String satuan;
    int jumlah;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_produk_tambah, container, false);
        initView(view);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        initListener();

        toolbar.setNavigationOnClickListener(this);
        return view;
    }
    private void initView(View view){
        toolbar = view.findViewById(R.id.toolbarPBK);
        txNama = view.findViewById(R.id.textNama);
        txJumlah = view.findViewById(R.id.textJumlah);
        Spsatuan = view.findViewById(R.id.spSatuanProduk);
        fabAddBahan = view.findViewById(R.id.fabTambahProduk);
    }

    private void initObject(View view){
        //dbmProduk = new DBMProduk(view);
        //setRV();
    }
    private void initListener(){
        fabAddBahan.setOnClickListener(this);
    }

    private void getTextInput(){
        if (txNama!=null&&txJumlah!=null) {
            nama = txNama.getEditText().getText().toString();
            jumlah = Integer.parseInt(txJumlah.getEditText().getText().toString());
            satuan = Spsatuan.getSelectedItem().toString();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_tambah_produk, menu);
        //MenuItem menuItem = menu.findItem(R.id.searchProduk);
    }

    @Override
    public void onClick(View v) {
        getActivity().onBackPressed();
    }
}
