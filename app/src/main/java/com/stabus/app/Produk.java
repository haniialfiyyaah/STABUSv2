package com.stabus.app;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
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

import com.stabus.app.Database.DBMProduk;
import com.stabus.app.Interface.ISetListener;
import com.stabus.app.Interface.OnListener;
import com.stabus.app.Model.MProdukBahan;
import com.stabus.app.RecyclerView.ProdukAdapter;

import java.util.ArrayList;
import java.util.List;

public class Produk extends Fragment implements OnListener, View.OnClickListener {

    //class
    private ISetListener mISetListener;
    private ProdukAdapter mAdapter;
    private DBMProduk dbmProduk;
    //list
    private List<MProdukBahan> produkList;
    private List<MProdukBahan> produkListFull;
    private List<MProdukBahan> selectedList;

    //widget
    private SearchView searchView;
    private RecyclerView recyclerView;
    private FloatingActionButton fabTambah;
    //tampilan
    private ScrollView scrollView;
    private FrameLayout frameLayout;

    private Toolbar toolbar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mISetListener.setNavigationIcon(0,false);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_produk, container, false);
        initView(view);
        initObject(view);
        initListener();
        return view;
    }

    private void initView(View view){
        searchView = view.findViewById(R.id.svProduk);
        recyclerView = view.findViewById(R.id.rvProduk);
        scrollView = view.findViewById(R.id.scrollProduk);
        frameLayout = view.findViewById(R.id.frameEmpty);
        fabTambah = view.findViewById(R.id.fabProduk);
    }
    private void initObject(View view){
        dbmProduk = new DBMProduk(view);
        setRV();
    }
    private void initListener(){
        fabTambah.setOnClickListener(this);
    }
    private void setRV(){
        produkList = new ArrayList<>();
        mAdapter = new ProdukAdapter(produkList,this);
        mISetListener.setRecyclerView(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL),
                recyclerView, mAdapter);
        mAdapter.notifyDataSetChanged();
        refreshList();
    }
    private void refreshList(){
        dbmProduk.getAllProduk(produkList);
        produkList.add(new MProdukBahan(0, "RESEP KUE BOLU HIGIENIS"));
        produkList.add(new MProdukBahan(0, "RESEP NIS"));
        produkList.add(new MProdukBahan(0, "RESEP KUE BOLU HIGIENIS"));
        produkList.add(new MProdukBahan(0, "RESEP KUE BOLU HIGIENIS"));
        produkList.add(new MProdukBahan(0, "RESEP KUE BOLGIENIS"));
        produkList.add(new MProdukBahan(0, "RESEP KUE BOLU HIGIENIS"));
        produkList.add(new MProdukBahan(0, "RESEP KUE BENIS"));
        produkList.add(new MProdukBahan(0, "RESEP KUE BOLU HIGIENIS"));


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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mISetListener = (ISetListener)getActivity();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_produk, menu);
        MenuItem menuItem = menu.findItem(R.id.searchProduk);

        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Cari Produk");
    }

    @Override
    public void OnClickListener(int position, View view) {

    }

    @Override
    public boolean OnLongListener(int position, View view) {
        return false;
    }

    @Override
    public void onClick(View v) {

    }
}
