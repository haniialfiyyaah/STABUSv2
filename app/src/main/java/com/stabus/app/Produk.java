package com.stabus.app;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
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
import android.widget.TextView;
import android.widget.Toast;

import com.stabus.app.Database.DBMHarga;
import com.stabus.app.Database.DBMProduk;
import com.stabus.app.Interface.ISetListener;
import com.stabus.app.Interface.OnListener;
import com.stabus.app.Model.CollectBahanBaku;
import com.stabus.app.Model.CollectBahanCRUD;
import com.stabus.app.Model.MProdukRelasi;
import com.stabus.app.RecyclerView.ProdukAdapter;

import java.util.ArrayList;
import java.util.List;

public class Produk extends Fragment implements OnListener, View.OnClickListener, SearchView.OnQueryTextListener {

    //class
    private ISetListener mISetListener;
    private ProdukAdapter mAdapter;
    private DBMProduk dbmProduk;
    //list
    private List<MProdukRelasi> produkList;
    private List<MProdukRelasi> produkListFull;
    private List<MProdukRelasi> selectedList;

    //widget
    private RecyclerView recyclerView;
    private FloatingActionButton fabTambah;
    //tampilan
    private ScrollView scrollView;
    private FrameLayout frameLayout;
    private Toolbar toolbar;
    private SearchView searchView;
    private TextView titleToolbar;
    int selected=0;
    //tempList
    CollectBahanCRUD crud;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_produk, container, false);
        initView(view);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        initObject(view);
        initListener();
        return view;
    }

    private void initView(View view){
        toolbar = view.findViewById(R.id.toolbarProduk);
        searchView = view.findViewById(R.id.svProduk);
        titleToolbar = view.findViewById(R.id.tvTitle);
        recyclerView = view.findViewById(R.id.rvProduk);
        scrollView = view.findViewById(R.id.scrollProduk);
        frameLayout = view.findViewById(R.id.frameEmpty);
        fabTambah = view.findViewById(R.id.fabProduk);
    }
    private void initObject(View view){
        dbmProduk = new DBMProduk(view);
        produkListFull = new ArrayList<>();
        dbmProduk.getAllProduk(produkListFull);
        setRV();
    }
    private void initListener(){
        fabTambah.setOnClickListener(this);
        searchView.setOnQueryTextListener(this);
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
        if (selected>0){
            inflater.inflate(R.menu.menu_produk, menu);
        }

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.actdelete) {
            //delete produk
            delete();
            clearMenu();
            mAdapter.delete(selectedList);
            cekEmptyList();
            //delete relasi
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void OnClickListener(int position, View view) {
        if (produkList.get(position).isHapus()){
            produkList.get(position).setSelected(!produkList.get(position).isSelected());
            selectList(position);
            titleToolbar.setText(selected +" item terpilih");
            mAdapter.notifyDataSetChanged();
        }else {
            MProdukRelasi produkRelasi = new MProdukRelasi();
            Bundle bundle = new Bundle();
            bundle.putInt("ID_PRODUK",produkList.get(position).getId_produk());
            bundle.putString("NAMA_PRODUK",produkList.get(position).getNama());
            bundle.putInt("JUMLAH_PRODUK",produkList.get(position).getJumlah());
            bundle.putString("SATUAN_PRODUK", produkList.get(position).getSatuan());
            mISetListener.inflateFragment(getString(R.string.TambahProduk),bundle);
        }
    }

    @Override
    public boolean OnLongListener(int position, View view) {
        if (!produkList.get(position).isHapus()){
            selected = selected+1;
            //set toolbar
            setToolbarHapus();
            //call All Open
            openDelete();
            //click list
            clickFirstList(position);
        }
        return false;
    }

    private void delete(){
        for (MProdukRelasi produkBahan : selectedList){
            //dbmProduk.deleteProduk(produkBahan.getId_produk());
            //delete relasi where id produk
            int fk_id_produk = produkBahan.getId_produk();
            int jumlah = produkBahan.getJumlah();
            dbmProduk.deleteRelasi(fk_id_produk,jumlah);
            if (!dbmProduk.cekFKProduk(fk_id_produk)){
                dbmProduk.deleteProduk(fk_id_produk);
                Toast toast = Toast.makeText(getContext(), "Delete produk!", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
        //search id produk  di relasi
        //jika tidak ada produk makna delete
    }

    private void selectList(int position){
        if (produkList.get(position).isSelected()){
            selectedList.add(produkList.get(position));
            selected++;
        }else {
            selectedList.remove(produkList.get(position));
            selected--;
        }
    }
    private void clickFirstList(int position){
        produkList.get(position).setSelected(!produkList.get(position).isSelected());
        selectedList = new ArrayList<>();
        selectedList.add(produkList.get(position));
    }
    private void setToolbarHapus(){
        searchView.setVisibility(View.GONE);
        titleToolbar.setVisibility(View.VISIBLE);
        toolbar.setBackground(getResources().getDrawable(R.drawable.shape_cornor_upp));
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white);

        titleToolbar.setText(selected +" item terpilih");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearMenu();
            }
        });
        callMenu();
    }
    private void openDelete(){
        for (int ind = 0; ind < produkList.size(); ind++){
            produkList.get(ind).setHapus(true);
        }
        mAdapter.notifyDataSetChanged();
    }
    private void closeDelete(){
        for (int ind = 0; ind < produkList.size(); ind++){
            produkList.get(ind).setHapus(false);
            produkList.get(ind).setSelected(false);
        }
        mAdapter.notifyDataSetChanged();
    }
    private void callMenu(){
        if (getActivity()!=null){
            getActivity().invalidateOptionsMenu();
        }
    }
    private void clearMenu(){
        selected=0;
        searchView.setVisibility(View.VISIBLE);
        titleToolbar.setVisibility(View.GONE);
        toolbar.setBackground(getResources().getDrawable(R.drawable.shape_search_view));
        toolbar.setNavigationIcon(null);
        callMenu();
        closeDelete();
    }

    @Override
    public void onClick(View v) {
        if (v==fabTambah){
            crud = new CollectBahanCRUD(CollectBahanBaku.getBahanBakuList());
            crud.getBahanBakuList().clear();
            mISetListener.inflateFragment(getString(R.string.TambahProduk),null);
        }
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
