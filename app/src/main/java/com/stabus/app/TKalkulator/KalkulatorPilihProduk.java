package com.stabus.app.TKalkulator;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ScrollView;

import com.stabus.app.Database.DBMProduk;
import com.stabus.app.Interface.ISetListener;
import com.stabus.app.Interface.OnListener;
import com.stabus.app.Model.CollectBahanBaku;
import com.stabus.app.Model.CollectBahanCRUD;
import com.stabus.app.Model.CollectKebutuhan;
import com.stabus.app.Model.CollectKebutuhanCRUD;
import com.stabus.app.Model.CollectKemasan;
import com.stabus.app.Model.CollectKemasanCRUD;
import com.stabus.app.Model.CollectString;
import com.stabus.app.Model.CollectStringCRUD;
import com.stabus.app.Model.MProdukRelasi;
import com.stabus.app.Model.MString;
import com.stabus.app.R;
import com.stabus.app.RecyclerView.ProdukAdapter;

import java.util.ArrayList;
import java.util.List;

public class KalkulatorPilihProduk extends Fragment implements OnListener , SearchView.OnQueryTextListener {

    View view;
    //class
    private ISetListener mISetListener;
    private ProdukAdapter mAdapter;
    private DBMProduk dbmProduk;
    //list
    private List<MProdukRelasi> produkList;
    private List<MProdukRelasi> produkListFull;
    private CollectBahanCRUD crud;
    private CollectStringCRUD stringCRUD;
    private CollectKemasanCRUD kemasanCRUD;
    private CollectKebutuhanCRUD kebutuhanCRUD;
    //widget
    private RecyclerView mRV;
    private SearchView searchView;
    private ScrollView scrollView;
    private FrameLayout frameLayout;
    private ImageButton addProduk;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_kalkulator_pilih,container,false);
        initView();
        initObject();
        initListener();
        setFullList();
        setmRV();
        return view;
    }
    private void initView(){
        mRV = view.findViewById(R.id.rvPilihProduk);
        scrollView = view.findViewById(R.id.scrollProduk);
        frameLayout = view.findViewById(R.id.frameEmpty);
        searchView = view.findViewById(R.id.svProduk);
        addProduk = view.findViewById(R.id.pilihTambah);
    }
    private void initObject(){
        dbmProduk = new DBMProduk(view);
        crud = new CollectBahanCRUD(CollectBahanBaku.getRelasi());
        stringCRUD = new CollectStringCRUD(CollectString.getString());
        kemasanCRUD = new CollectKemasanCRUD(CollectKemasan.getKemasanList());
        kebutuhanCRUD = new CollectKebutuhanCRUD(CollectKebutuhan.getKebutuhanList());
    }
    private void initListener(){
        searchView.setOnQueryTextListener(this);
        addProduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crud.getRelasi().clear();
                stringCRUD.getString().clear();
                mISetListener.inflateFragment(getString(R.string.TambahProduk),null);
            }
        });
    }
    private void setmRV(){
        produkList = new ArrayList<>();
        mAdapter = new ProdukAdapter(produkList,this);
        mISetListener.setRecyclerView(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL),
                                      mRV, mAdapter);
        mAdapter.notifyDataSetChanged();
        refreshList();
    }
    private void setFullList(){
        produkListFull = new ArrayList<>();
        dbmProduk.getAllProduk(produkListFull);
    }
    private void refreshList(){
        dbmProduk.getAllProduk(produkList);
        mAdapter.notifyDataSetChanged();
        cekEmptyList();
    }
    private void cekEmptyList(){
        if (produkList.size()==0){
            scrollView.setVisibility(View.GONE);
            frameLayout.setVisibility(View.VISIBLE);
        }else {
            scrollView.setVisibility(View.VISIBLE);
            frameLayout.setVisibility(View.GONE);
        }
    }

    private void setListener(int position){
        crud.getRelasi().clear();
        stringCRUD.getString().clear();
        kemasanCRUD.getKemasanList().clear();
        kebutuhanCRUD.getKebutuhanList().clear();
        //inisiialize
        int id_produk = produkList.get(position).getFk_id_produk();
        String nama = produkList.get(position).getNama_produk();
        int jumlah_lama = produkList.get(position).getJumlah();
        String satuan = produkList.get(position).getSatuan();
        //get relasi from produk
        MString string = new MString();
        string.setNama(nama);
        string.setJumlah(jumlah_lama);
        string.setSatuan(satuan);
        string.setId(id_produk);
        string.setEdit(true);
        if (stringCRUD.getString().size()>0) stringCRUD.getString().clear();
        stringCRUD.addNew(string);
        dbmProduk.getAllRelasi(crud.getRelasi(),id_produk,jumlah_lama);
    }


    @Override
    public void OnClickListener(int position, View view) {
        setListener(position);
        mISetListener.inflateFragment(getString(R.string.KalkulatorSetHarga),null);
    }

    @Override
    public boolean OnLongListener(int position, View view) {
        return false;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mISetListener = (ISetListener) getActivity();
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        mAdapter.filter(produkListFull, s);
        cekEmptyList();
        return true;
    }
}
