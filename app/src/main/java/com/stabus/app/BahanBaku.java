package com.stabus.app;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.stabus.app.Database.DBMBahan;
import com.stabus.app.Interface.ISetListener;
import com.stabus.app.Interface.OnListener;
import com.stabus.app.Model.MBahanBaku;
import com.stabus.app.RecyclerView.BahanBakuAdapter;

import java.util.ArrayList;
import java.util.List;

public class BahanBaku extends Fragment implements View.OnClickListener , OnListener{

    private ISetListener mISetListener;
    private BahanBakuAdapter mAdapter;
    private List<MBahanBaku> bahanBakuList;
    private List<MBahanBaku> bahanBakuListFull;
    private List<MBahanBaku> selectedList;

    private RecyclerView mRecyclerView;
    private FrameLayout frameRV;
    private ScrollView scrollView;
    private FloatingActionButton fabTambah;

    private int selected=0;

    private DBMBahan dbmBahan;

    private DialogTambah dialogTambah;
    private DialogHapus dialogHapus;

    private SearchView searchView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mISetListener.setToolbarTitle(getTag());
        setHasOptionsMenu(true);
        //mISetListener.setNavigationIcon(R.drawable.ic_home_white, false);
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

    /*    DEFINE ID LAYPUT BAHAN BAKU     */
    private void initView(View view){
        mRecyclerView = view.findViewById(R.id.rvBahanBK);
        frameRV =view.findViewById(R.id.frameRVBahan);
        scrollView=view.findViewById(R.id.scrollBahan);
        fabTambah = view.findViewById(R.id.fabBahanBK);
    }
    private void initObject(View view){
        dbmBahan = new DBMBahan(view);
        setRV(view);
        dialogTambah = new DialogTambah(getString(R.string.BahanBaku),getActivity(),view,dbmBahan,bahanBakuList,mAdapter,frameRV,scrollView);
        dialogHapus = new DialogHapus(view,bahanBakuList,selectedList,mAdapter,selected,dbmBahan,getActivity(),mISetListener);
    }
    private void setRV(View view){
        bahanBakuList = new ArrayList<>();
        mAdapter = new BahanBakuAdapter(bahanBakuList,this);
        mISetListener.setRecyclerView(view, mRecyclerView, mAdapter);
        mAdapter.notifyDataSetChanged();
        refreshList();
    }
    private void initListener(){
        fabTambah.setOnClickListener(this);
    }
    private void refreshList(){
        dbmBahan.getAllBahan(bahanBakuList);
        mAdapter.notifyDataSetChanged();
        cekEmptyList();
    }

    private void cekEmptyList(){
        if (bahanBakuList.size()==0){
            scrollView.setVisibility(View.GONE);
            frameRV.setVisibility(View.VISIBLE);
        }else {
            scrollView.setVisibility(View.VISIBLE);
            frameRV.setVisibility(View.GONE);
        }
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

        searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Cari Bahan Baku..");
        searchView.setPadding(-22, 0, 0, 0);

        if (selected<=0){
            menu.findItem(R.id.actsearch).setVisible(true);
            menu.findItem(R.id.actdelete).setVisible(false);
            menu.findItem(R.id.actSelectAll).setVisible(false);
        }
        else{
            menu.findItem(R.id.actsearch).setVisible(false);
            menu.findItem(R.id.actdelete).setVisible(true);
            menu.findItem(R.id.actSelectAll).setVisible(true);
        }
        bahanBakuListFull = new ArrayList<>();
        dbmBahan.getAllBahan(bahanBakuListFull);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                mAdapter.filter(bahanBakuListFull, s);
                return true;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onClick(View v) {
        if (v==fabTambah){
            callMenu();
            dialogTambah.tambahDialog(getString(R.string.TambahBahan));
        }
    }

    @Override
    public void OnClickListener(int position, View view) {
        int sId = bahanBakuList.get(position).getId();
        String sNama = bahanBakuList.get(position).getNama_bahan();
        String sKategori = bahanBakuList.get(position).getKategori();
        if (bahanBakuList.get(position).isOpen()){
            bahanBakuList.get(position).setSelected(!bahanBakuList.get(position).isSelected());
            selectList(position);
            mISetListener.setToolbarTitle(selected +" item terpilih");
            mAdapter.notifyDataSetChanged();
        }
        else {
            Bundle bundle = new Bundle();
            bundle.putInt(getString(R.string.selectedId), sId);
            bundle.putString(getString(R.string.selectedNama), sNama);
            bundle.putString(getString(R.string.selectedKategori), sKategori);
            mISetListener.inflateFragment(getString(R.string.HargaBahanBaku), bundle);
        }

    }

    @Override
    public boolean OnLongListener(int position, View view) {
        if (!bahanBakuList.get(position).isOpen()){
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

    private void selectList(int position){
        if (bahanBakuList.get(position).isSelected()){
            selectedList.add(bahanBakuList.get(position));
            selected++;
        }else {
            selectedList.remove(bahanBakuList.get(position));
            selected--;
        }
    }
    private void clickFirstList(int position){
        bahanBakuList.get(position).setSelected(!bahanBakuList.get(position).isSelected());
        selectedList = new ArrayList<>();
        selectedList.add(bahanBakuList.get(position));
    }
    private void setToolbarHapus(){
        mISetListener.setToolbarTitle(selected +" item selected");
        mISetListener.setNavigationListener(R.drawable.ic_arrow_back_white, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearMenu();
            }
        });
        callMenu();
    }
    private void openDelete(){
        for (int ind = 0; ind < bahanBakuList.size(); ind++){
            bahanBakuList.get(ind).setOpen(true);
        }
        mAdapter.notifyDataSetChanged();
    }
    private void closeDelete(){
        for (int ind = 0; ind < bahanBakuList.size(); ind++){
            bahanBakuList.get(ind).setOpen(false);
            bahanBakuList.get(ind).setSelected(false);
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
        callMenu();
        mISetListener.setToolbarTitle(getTag());
        mISetListener.setNavigationIcon(R.drawable.ic_home_white, false);
        closeDelete();
    }
    private void deleteDB(){
        for (MBahanBaku bahanBaku : selectedList){
            dbmBahan.delete(bahanBaku.getId());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mISetListener.setToolbarTitle(getTag());
        mISetListener.setNavigationIcon(R.drawable.ic_home_white, false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.actdelete) {
            deleteDB();
            clearMenu();
            mAdapter.delete(selectedList);
            cekEmptyList();
        }
        return super.onOptionsItemSelected(item);
    }



}
