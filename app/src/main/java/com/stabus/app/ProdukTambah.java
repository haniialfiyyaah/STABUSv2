package com.stabus.app;

import android.annotation.SuppressLint;
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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.stabus.app.Class.MyTextWatcher;
import com.stabus.app.Database.DBMBahan;
import com.stabus.app.Database.DBMProduk;
import com.stabus.app.Interface.ISetListener;
import com.stabus.app.Interface.OnListener;
import com.stabus.app.Model.CollectBahanBaku;
import com.stabus.app.Model.CollectBahanCRUD;
import com.stabus.app.Model.CollectString;
import com.stabus.app.Model.CollectStringCRUD;
import com.stabus.app.Model.MString;
import com.stabus.app.RecyclerView.RelasiAdapter;

public class ProdukTambah extends Fragment implements View.OnClickListener, OnListener {

    //widget
    Toolbar toolbar;
    ScrollView scrollView;
    FrameLayout frameLayout;
    FloatingActionButton fabAddBahan;
    RecyclerView mRecyclerView;
    TextView tvTitle;
    //inputan
    TextInputLayout txNama;
    TextInputLayout txJumlah;
    Spinner Spsatuan;

    //gettext string
    String nama;
    int jumlah;
    String satuan;
    int id_produk;
    int jumlah_lama;

    //class
    DBMProduk dbmProduk;
    DBMBahan dbmBahan;
    ISetListener mISetListener;
    //tempList
    CollectBahanCRUD crud;
    CollectStringCRUD stringCRUD;
    ProdukDialogTambah dialogTambah;
    //list
    RelasiAdapter mAdapter;

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
        if (getActivity() !=null)
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
        tvTitle = view.findViewById(R.id.tvTitle);
    }
    private void initObject(View view){
        txNama.requestFocus();
        dbmProduk = new DBMProduk(view);
        dbmBahan = new DBMBahan(view);
        //dll
        crud = new CollectBahanCRUD(CollectBahanBaku.getRelasi());
        stringCRUD = new CollectStringCRUD(CollectString.getString());
        setStringCRUD();
        setRV(view);
        dialogTambah = new ProdukDialogTambah(mAdapter,crud,scrollView,frameLayout);
    }

    @SuppressLint("SetTextI18n")
    private void setStringCRUD(){
        if (stringCRUD.getString().size()>0){
            if (stringCRUD.getString().get(0).isEdit()) tvTitle.setText("EDIT PRODUK");
            id_produk = stringCRUD.getString().get(0).getId();
            nama = stringCRUD.getString().get(0).getNama();
            jumlah = stringCRUD.getString().get(0).getJumlah();
            satuan = stringCRUD.getString().get(0).getSatuan();
            jumlah_lama = stringCRUD.getString().get(0).getJumlah();
            //setrelasiifedit

            if (txNama.getEditText()!=null)
            txNama.getEditText().setText(nama);
            if (jumlah>0)
                if (txJumlah.getEditText()!=null)
                    txJumlah.getEditText().setText(String.valueOf(jumlah));
            switch (satuan) {
                case "porsi":
                    Spsatuan.setSelection(0);
                    break;
                case "pcs":
                    Spsatuan.setSelection(1);
                    break;
                case "buah":
                    Spsatuan.setSelection(2);
                    break;
                default:
                    Spsatuan.setSelection(3);
                    break;
            }
        }
    }
    private void initListener(){
        fabAddBahan.setOnClickListener(this);
        toolbar.setNavigationOnClickListener(this);
        if (txNama.getEditText()!=null)
            txNama.getEditText().addTextChangedListener(new MyTextWatcher(txNama,dbmProduk,"Nama", stringCRUD));
        if (txJumlah.getEditText()!=null)
            txJumlah.getEditText().addTextChangedListener(new MyTextWatcher(txJumlah,dbmProduk,"Jumlah", stringCRUD));
        Spsatuan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                MString string = new MString();
                string.setSatuan(String.valueOf(Spsatuan.getSelectedItem()));
                if (!(stringCRUD.getString().size() >0)){
                    stringCRUD.getString().add(0,string);
                }
                else stringCRUD.getString().get(0).setSatuan(Spsatuan.getSelectedItem().toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }
    private void setRV(View view){
        mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new RelasiAdapter(crud.getRelasi(), this, false);
        mISetListener.setRecyclerView(new LinearLayoutManager(view.getContext()), mRecyclerView, mAdapter);
        //mAdapter.notifyDataSetChanged();
        refreshList();
    }
    private void refreshList(){
        mAdapter.notifyDataSetChanged();
        cekEmptyList();
    }
    private void cekEmptyList(){
        if (crud.getRelasi().size()==0){
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
        if (txNama.getEditText()!=null&&txJumlah.getEditText()!=null) {
            nama = txNama.getEditText().getText().toString();
            jumlah = Integer.parseInt(txJumlah.getEditText().getText().toString());
            satuan = Spsatuan.getSelectedItem().toString();
        }
    }
    private void tambahData(View v){
        if (txNama.getEditText()!=null)
        if (txNama.getEditText().getText().toString().trim().length() <=0){
            setErrorMessage(txNama,"Tidak Boleh Kosong");
            return;
        }
        if (txJumlah.getEditText()!=null)
        if (txJumlah.getEditText().getText().toString().length() <=0){
            setErrorMessage(txJumlah,"Tidak Boleh Kosong");
            return;
        }
        setErrorMessage(txNama,"");
        setErrorMessage(txJumlah,"");
        //cek bahanbaku added
        boolean cekBahanBaku = crud.getRelasi().size() >0;
        if (!cekBahanBaku){
            Toast toast = Toast.makeText(getContext(), "Pilih Bahan Baku!", Toast.LENGTH_SHORT);
            toast.show();
            if (getActivity()!=null){
                InputMethodManager imm = ((InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE));
                imm.hideSoftInputFromWindow(v.getWindowToken(),0);
                v.clearFocus();
                return;
            }
        }
        getTextInput();
        //jika sudah ada hanya input relasi bahan terpilih
        if (stringCRUD.getString().get(0).isEdit()){
            dbmProduk.ubahProduk(id_produk,nama);
            dbmProduk.deleteRelasi(id_produk,jumlah_lama);
            simpanRelasi();
        }else {
            if (dbmProduk.cekJumlah(nama,jumlah,satuan)){
                txJumlah.setError("Jumlah ini sudah ada!");
                txJumlah.requestFocus();
                return;
            }
            if (dbmProduk.cekNama(nama)) simpanRelasi();
            else {
                dbmProduk.saveProduk(nama);
                simpanRelasi();
            }
        }
        clearError();
        clearText();
        if (getActivity()!=null)
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
        if (txNama.getEditText()!=null&&txJumlah.getEditText()!=null) {
            txNama.getEditText().setText(null);
            txJumlah.getEditText().setText(null);
        }
    }

    private void simpanRelasi(){
        //produk
        int fk_id_poduk;
        if (!stringCRUD.getString().get(0).isEdit()) {
            fk_id_poduk= dbmProduk.produk(nama, 0).getFk_id_produk();
        }else {
            fk_id_poduk=id_produk;
        }
        int jumlah = 0;
        if (txJumlah.getEditText()!=null)
            jumlah = Integer.parseInt(txJumlah.getEditText().getText().toString());
        String satuan = Spsatuan.getSelectedItem().toString();
        //bahanbaku
        int fk_id_bahan;
        int jumlah_digunakan;
        String satuan_digunakan;
        for (int pos = 0;pos<crud.getRelasi().size();pos++){
            fk_id_bahan = crud.getRelasi().get(pos).getFk_id_bahan();
            jumlah_digunakan = crud.getRelasi().get(pos).getIsi_digunakan();
            satuan_digunakan = crud.getRelasi().get(pos).getSatuan_digunakan();
            dbmProduk.saveRelasi(fk_id_poduk,jumlah,satuan,fk_id_bahan,jumlah_digunakan,satuan_digunakan);
        }
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
        assert getActivity()!=null;
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
    public void OnClickListener(int position, View view) {
        dialogTambah.showDialog(view,position);
    }

    @Override
    public boolean OnLongListener(int position, View view) {
        return false;
    }

}
