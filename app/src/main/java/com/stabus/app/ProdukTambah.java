package com.stabus.app;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.stabus.app.Database.DBMBahan;
import com.stabus.app.Database.DBMProduk;
import com.stabus.app.Interface.ISetListener;
import com.stabus.app.Interface.OnListener;
import com.stabus.app.Model.MHargaBahan;
import com.stabus.app.RecyclerView.HargaBahanAdapter;
import com.stabus.app.RecyclerView.ProdukAdapter;

import java.util.ArrayList;
import java.util.List;

public class ProdukTambah extends Fragment implements View.OnClickListener, OnListener {

    //widget
    Toolbar toolbar;
    ScrollView scrollView;
    FrameLayout frameLayout;
    FloatingActionButton fabAddBahan;
    RecyclerView mRecyclerView;

    //list
    List<MHargaBahan> bahanBakuList;
    List<MHargaBahan> selectedBahan;
    HargaBahanAdapter mAdapter;

    //inputan
    TextInputLayout txNama;
    TextInputLayout txJumlah;
    Spinner Spsatuan;

    //gettext string
    String nama;
    String satuan;
    int jumlah;

    //class
    DBMProduk dbmProduk;
    DBMBahan dbmBahan;
    ISetListener mISetListener;

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
        initObject(view);
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
        mRecyclerView = view.findViewById(R.id.rvTambahBahan);
        scrollView = view.findViewById(R.id.scrollBahan);
        frameLayout = view.findViewById(R.id.frameEmpty);
    }
    private void initObject(View view){
        txNama.requestFocus();
        dbmProduk = new DBMProduk(view);
        dbmBahan = new DBMBahan(view);
        setRV(view);

    }
    private void initListener(){
        fabAddBahan.setOnClickListener(this);
    }
    private void setRV(View view){
        if (bahanBakuList==null){
            bahanBakuList = new ArrayList<>();
            Toast toast = Toast.makeText(getContext(), "NEW ONE", Toast.LENGTH_SHORT);
            toast.show();
        }
        mAdapter = new HargaBahanAdapter(bahanBakuList,this);
        mISetListener.setRecyclerView(new LinearLayoutManager(view.getContext()), mRecyclerView, mAdapter);
        mAdapter.notifyDataSetChanged();
        refreshList();
    }
    private void refreshList(){
        //dbmBahan.getAllBahan(bahanBakuList);
        mAdapter.notifyDataSetChanged();
        cekEmptyList();
    }
    private void cekEmptyList(){
        if (bahanBakuList.size()==0){
            scrollView.setVisibility(View.GONE);
            frameLayout.setVisibility(View.VISIBLE);
        }else {
            scrollView.setVisibility(View.VISIBLE);
            frameLayout.setVisibility(View.GONE);
        }
    }

    /*
    HAPUS DATA METHOD
     */
    private void getTextInput(){
        if (txNama!=null&&txJumlah!=null) {
            nama = txNama.getEditText().getText().toString();
            jumlah = Integer.parseInt(txJumlah.getEditText().getText().toString());
            satuan = Spsatuan.getSelectedItem().toString();
        }
    }
    private void tambahData(){
        if (txNama.getEditText().getText().toString().trim().length() <=0){
            setErrorMessage(txNama,"Tidak Boleh Kosong");
            return;
        }
        if (txJumlah.getEditText().getText().toString().length() <=0){
            setErrorMessage(txJumlah,"Tidak Boleh Kosong");
            return;
        }

        getTextInput();
        dbmProduk.saveProduk(nama);
        //dbmProduk.saveRelasi();

        clearError();
        clearText();
        getActivity().onBackPressed();

    }
    private void setErrorMessage(TextInputLayout inputLayout, String message){
        clearError();
        inputLayout.setError(message);
        inputLayout.requestFocus();
    }
    private void clearError(){
        txNama.setError(null);
        txJumlah.setError(null);
    }
    private void clearText(){
        txNama.getEditText().setText(null);
        txJumlah.getEditText().setText(null);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mISetListener = (ISetListener)getActivity();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_tambah_produk, menu);
        //MenuItem menuItem = menu.findItem(R.id.searchProduk);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.actsaveProduk) {
            tambahData();
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onClick(View v) {
        if (v==fabAddBahan){
            //bahanBakuList.add(new MHargaBahan(0,"Bahan Baku","Merk",0));
        }else {
            getActivity().onBackPressed();
        }
    }



    @Override
    public void OnClickListener(int position, View view) {

    }

    @Override
    public boolean OnLongListener(int position, View view) {
        return false;
    }
}
