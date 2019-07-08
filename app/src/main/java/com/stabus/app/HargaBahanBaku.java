package com.stabus.app;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.stabus.app.Database.DBMHarga;
import com.stabus.app.Interface.ISetListener;
import com.stabus.app.Interface.OnListener;
import com.stabus.app.Model.MHargaBahan;
import com.stabus.app.RecyclerView.HargaBahanAdapter;

import java.util.ArrayList;
import java.util.List;

public class HargaBahanBaku extends Fragment implements View.OnClickListener, OnListener {

    private ISetListener mISetListener;
    private HargaBahanAdapter mAdapter;
    private List<MHargaBahan> hargaBahanList;
    private List<MHargaBahan> hargaBahanListFull;

    private String sNama;
    private int sId;
    private String sKategori;

    private RecyclerView mRecyclerView;
    private FrameLayout frameRV;
    private ScrollView scrollView;
    private FloatingActionButton fabTambah;

    private DBMHarga dbmHarga;

    private DialogTambah dialogTambah;

    int selected =0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Bundle bundle = this.getArguments();
        if (bundle!=null){
            this.sId = bundle.getInt(getString(R.string.selectedId));
            this.sNama = bundle.getString(getString(R.string.selectedNama));
            this.sKategori = bundle.getString(getString(R.string.selectedKategori));
        }
        //set toolbar title
        mISetListener.setToolbarTitle(sNama);
        mISetListener.setNavigationIcon(R.drawable.ic_arrow_back_white, true);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bahan_baku, container, false);

        initView(view);
        initObject(view);
        initListener();

        return view;
    }

    private void initView(View view){
        mRecyclerView = view.findViewById(R.id.rvBahanBK);
        frameRV =view.findViewById(R.id.frameRVBahan);
        scrollView=view.findViewById(R.id.scrollBahan);
        fabTambah = view.findViewById(R.id.fabBahanBK);
    }
    private void initObject(View view){
        dbmHarga = new DBMHarga(view);
        setRV(view);
        dialogTambah = new DialogTambah(getString(R.string.HargaBahanBaku),getActivity(),view, dbmHarga,hargaBahanList,mAdapter,frameRV,scrollView);
    }
    private void setRV(View view){
        hargaBahanList = new ArrayList<>();
        mAdapter = new HargaBahanAdapter(hargaBahanList, this);
        mISetListener.setRecyclerView(view, mRecyclerView, mAdapter);
        mAdapter.notifyDataSetChanged();
        refreshList();
    }
    private void initListener(){
        fabTambah.setOnClickListener(this);
    }
    private void cekEmptyList(){
        if (hargaBahanList.size()==0){
            scrollView.setVisibility(View.GONE);
            frameRV.setVisibility(View.VISIBLE);
        }else {
            scrollView.setVisibility(View.VISIBLE);
            frameRV.setVisibility(View.GONE);
        }
    }

    private void refreshList(){
        dbmHarga.getAllHarga(hargaBahanList);
        mAdapter.notifyDataSetChanged();
        cekEmptyList();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mISetListener = (ISetListener)getActivity();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_bahan_baku, menu);
        MenuItem menuItem = menu.findItem(R.id.actsearch);

        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Cari..");
        searchView.setPadding(-22,0,0,0);

        if (selected<=0){
            menu.findItem(R.id.actsearch).setVisible(true);
            menu.findItem(R.id.actdelete).setVisible(false);
            menu.findItem(R.id.actSelectAll).setVisible(false);
        }else{
            menu.findItem(R.id.actsearch).setVisible(false);
            menu.findItem(R.id.actdelete).setVisible(true);
            menu.findItem(R.id.actSelectAll).setVisible(true);
        }

        hargaBahanListFull = new ArrayList<>(hargaBahanList);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                mAdapter.filter(hargaBahanListFull, s);
                return true;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onClick(View v) {
        if (v==fabTambah){
            callMenu();
            dialogTambah.tambahDialog(getString(R.string.TambahHarga)+sNama);
        }
    }
    private void callMenu(){
        if (getActivity()!=null){
            getActivity().invalidateOptionsMenu();
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
