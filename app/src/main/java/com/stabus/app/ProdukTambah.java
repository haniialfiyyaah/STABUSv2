package com.stabus.app;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
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
import com.stabus.app.RecyclerView.BahanBakuAdapter;

public class ProdukTambah extends Fragment implements View.OnClickListener, OnListener,TextWatcher {

    //widget
    Toolbar toolbar;
    ScrollView scrollView;
    FrameLayout frameLayout;
    FloatingActionButton fabAddBahan;
    RecyclerView mRecyclerView;

    //inputan
    TextInputLayout txNama;
    TextInputLayout txJumlah;
    Spinner Spsatuan;

    //gettext string
    String nama;
    String satuan;
    int jumlah;
    int jumlah_lama;
    int id_produk;
    //class
    DBMProduk dbmProduk;
    DBMBahan dbmBahan;
    ISetListener mISetListener;

    //tempList
    CollectBahanCRUD crud;
    ProdukDialogTambah dialogTambah;

    //list
    //List<MHargaBahan> bahanBakuList;
    BahanBakuAdapter mAdapter;
    Bundle bundle;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bundle = this.getArguments();
        if (bundle!=null){
            this.jumlah_lama = bundle.getInt("JUMLAH_PRODUK");
            this.nama = bundle.getString("NAMA_PRODUK");
            this.satuan = bundle.getString("SATUAN_PRODUK");
            this.id_produk = bundle.getInt("ID_PRODUK");
        }

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
        crud = new CollectBahanCRUD(CollectBahanBaku.getBahanBakuList());
        if (bundle!=null){
            dbmProduk.getAllRelasi(crud.getBahanBakuList(),id_produk,jumlah_lama);
            txNama.getEditText().setText(nama);
            txJumlah.getEditText().setText(String.valueOf(jumlah_lama));
            if (satuan.equals("porsi")) {
                Spsatuan.setSelection(0);
            } else if (satuan.equals("pcs")) {
                Spsatuan.setSelection(1);
            } else if (satuan.equals("buah")) {
                Spsatuan.setSelection(2);
            }
            else {
                Spsatuan.setSelection(3);
            }
        }
        txNama.getEditText().addTextChangedListener(this);
        txJumlah.getEditText().addTextChangedListener(this);
        setRV(view);

        dialogTambah = new ProdukDialogTambah(crud,mAdapter);
    }
    private void initListener(){
        fabAddBahan.setOnClickListener(this);
        toolbar.setNavigationOnClickListener(this);
        txNama.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (dbmProduk.cekNama(String.valueOf(s))){
                    txNama.setError("Tersedia");
                }else {
                    txNama.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    private void setRV(View view){
        mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new BahanBakuAdapter(crud.getBahanBakuList(),this,false);
        mISetListener.setRecyclerView(new LinearLayoutManager(view.getContext()), mRecyclerView, mAdapter);
        //mAdapter.notifyDataSetChanged();
        refreshList();
    }
    private void refreshList(){
        mAdapter.notifyDataSetChanged();
        cekEmptyList();
    }
    private void cekEmptyList(){
        if (crud.getBahanBakuList().size()==0){
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
    private void tambahData(View v){
        if (txNama.getEditText().getText().toString().trim().length() <=0){
            setErrorMessage(txNama,"Tidak Boleh Kosong");
            return;
        }
        if (txJumlah.getEditText().getText().toString().length() <=0){
            setErrorMessage(txJumlah,"Tidak Boleh Kosong");
            return;
        }
        setErrorMessage(txNama,"");
        setErrorMessage(txJumlah,"");
        //cek produk udah ada belum
        boolean cekProduk = crud.getBahanBakuList().size() >0;
        if (!cekProduk){
            Toast toast = Toast.makeText(getContext(), "Pilih Bahan Baku!", Toast.LENGTH_SHORT);
            toast.show();
            InputMethodManager imm = ((InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE));
            imm.hideSoftInputFromWindow(v.getWindowToken(),0);
            v.clearFocus();
            return;
        }

        getTextInput();
        //jika sudah ada hanya input relasi bahan terpilih


        if (bundle!=null){
            dbmProduk.ubahProduk(id_produk,nama);
            dbmProduk.deleteRelasi(id_produk,jumlah_lama);

            simpanRelasi();
        }else {

            if (dbmProduk.cekNama(nama)) simpanRelasi();
            else {
                dbmProduk.saveProduk(nama);
                simpanRelasi();
            }
        }


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

    private void simpanRelasi(){

        int fk_id_poduk;
        if (bundle==null) {
             fk_id_poduk= dbmProduk.produk(nama, 0).getId_produk();
        }else {
            fk_id_poduk=id_produk;
        }
        int jumlah = Integer.parseInt(txJumlah.getEditText().getText().toString());
        String satuan = Spsatuan.getSelectedItem().toString();
        int fk_id_bahan;
        int jumlah_digunakan;
        String satuan_digunakan;
        for (int pos = 0;pos<crud.getBahanBakuList().size();pos++){
            fk_id_bahan = crud.getBahanBakuList().get(pos).getId();
            jumlah_digunakan = crud.getBahanBakuList().get(pos).getJumlah_dg();
            satuan_digunakan = crud.getBahanBakuList().get(pos).getSatuan_dg();
            dbmProduk.saveRelasi(fk_id_poduk,jumlah,satuan,fk_id_bahan,jumlah_digunakan,satuan_digunakan);
        }
        Log.d("fk_id_Produk","FK_ID_PRODUK"+String.valueOf(fk_id_poduk));
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
            tambahData(getView());
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onClick(View v) {
        if (v==fabAddBahan){
            //bahanBakuList.add(new MHargaBahan(0,"Bahan Baku","Merk",0));
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            //AMBIL DATA DARI FRAGMENT BAHAN BAKU
            ProdukPilihBahan fragment = new ProdukPilihBahan();
            fragmentTransaction.replace(R.id.frameContainer, fragment,"");
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }else {
            getActivity().onBackPressed();
        }
    }



    @Override
    public void OnClickListener(int position, String str, View view) {
        dialogTambah.showDialog(view,position);

    }

    @Override
    public boolean OnLongListener(int position, View view) {
        return false;
    }



    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
    }
}
