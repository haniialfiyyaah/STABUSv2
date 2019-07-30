package com.stabus.app;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
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

import com.stabus.app.Database.DBMBahan;
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
    private List<MHargaBahan> selectedList;

    private String sNama;
    private int sId;
    private String sKategori;

    private RecyclerView mRecyclerView;
    private FrameLayout frameRV;
    private ScrollView scrollView;
    private FloatingActionButton fabTambah;
    Toolbar toolbar;
    TextView title;

    private DBMHarga dbmHarga;
    private DBMBahan dbmBahan;

    private ClassDialogTambah dialogTambah;
    private ClassDialogEdit dialogEdit;

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
        if (getView()!=null) {
            dbmBahan = new DBMBahan(getView());
            this.sNama = dbmBahan.bahanBaku("",sId).getNama_bahan();
        }

        //mISetListener.setToolbarTitle(sNama);
        //mISetListener.setToolbarTitleListener(true,this);
        //mISetListener.setNavigationIcon(R.drawable.ic_arrow_back_white, true);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bahan, container, false);

        initView(view);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        settoolbars(view);
        initObject(view);
        initListener();
        return view;
    }

    private void settoolbars(View v){
        //set toolbar title
        title.setText(sNama);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white);

        Drawable drawable = ContextCompat.getDrawable(v.getContext(), R.drawable.ic_edit);
        drawable.setBounds(0,0,30,30);
        title.setCompoundDrawables(null,null,drawable,null);

        title.setClickable(true);
        title.setOnClickListener(this);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
    }
    private void initView(View view){
        mRecyclerView = view.findViewById(R.id.rvBahanBK);
        frameRV =view.findViewById(R.id.frameRVBahan);
        scrollView=view.findViewById(R.id.scrollBahan);
        fabTambah = view.findViewById(R.id.fabBahanBK);
        toolbar = view.findViewById(R.id.toolbarBK);
        title = view.findViewById(R.id.tvTitle);
    }
    private void initObject(View view){
        dbmHarga = new DBMHarga(view);
        dbmBahan= new DBMBahan(view);
        setRV(view);
        dialogTambah = new ClassDialogTambah(getString(R.string.HargaBahanBaku), getActivity(), view, hargaBahanList, mAdapter, frameRV, scrollView);
        dialogEdit = new ClassDialogEdit(title, view, getActivity(), hargaBahanList, mAdapter, scrollView, frameRV);
    }
    private void setRV(View view){
        hargaBahanList = new ArrayList<>();
        mAdapter = new HargaBahanAdapter(hargaBahanList, this);
        mISetListener.setRecyclerView(new LinearLayoutManager(view.getContext()), mRecyclerView, mAdapter);
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
        dbmHarga.getAllHarga(hargaBahanList,sId);
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
        }else{
            menu.findItem(R.id.actsearch).setVisible(false);
            menu.findItem(R.id.actdelete).setVisible(true);
            if (hargaBahanList.size()==selected){
                menu.findItem(R.id.actdelete).setEnabled(false);
                menu.findItem(R.id.actdelete).setIcon(R.drawable.ic_delete_gray);
            }else {
                menu.findItem(R.id.actdelete).setEnabled(true);
                menu.findItem(R.id.actdelete).setIcon(R.drawable.ic_delete);
            }
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
                cekEmptyList();
                return true;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onClick(View v) {
        clearMenu(v);
        if (v==fabTambah){
            dialogTambah.tambahDialog(getString(R.string.TambahHarga)+" "+sNama,sId);
            //dbmHarga.deleteAll();
        }else{
            dialogEdit.editDialog(getString(R.string.UbahBahan),sId,sNama);

            Toast toast = Toast.makeText(getContext(), "EDIT OPEN", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    public void OnClickListener(int position, String str, View view) {
        int setId = hargaBahanList.get(position).getId();

        if (hargaBahanList.get(position).isOpen()){
            hargaBahanList.get(position).setSelected(!hargaBahanList.get(position).isSelected());
            selectList(position);
            title.setText(selected +" item terpilih");
            //mISetListener.setToolbarTitle(selected +" item terpilih");
            mAdapter.notifyDataSetChanged();
        }else {
            dialogEdit.editDialog(getString(R.string.UbahHarga), setId,"");
        }
    }

    @Override
    public boolean OnLongListener(int position, View view) {
        if (!hargaBahanListFull.get(position).isOpen()){
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
        callMenu();
        if (hargaBahanList.get(position).isSelected()){
            selectedList.add(hargaBahanList.get(position));
            selected++;
        }else {
            selectedList.remove(hargaBahanList.get(position));
            selected--;
        }
    }
    private void clickFirstList(int position){
        callMenu();
        hargaBahanList.get(position).setSelected(!hargaBahanList.get(position).isSelected());
        selectedList = new ArrayList<>();
        selectedList.add(hargaBahanList.get(position));
    }
    private void setToolbarHapus(){
        title.setText(selected +" item terpilih");
        //mISetListener.setToolbarTitle(selected +" item selected");
        //mISetListener.setToolbarTitleListener(false,null);
        title.setOnClickListener(null);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearMenu(v);
            }
        });/*
        mISetListener.setNavigationListener(R.drawable.ic_arrow_back_white, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearMenu();
            }
        });*/
        callMenu();
    }
    private void openDelete(){
        for (int ind = 0; ind < hargaBahanList.size(); ind++){
            hargaBahanList.get(ind).setOpen(true);
        }
        mAdapter.notifyDataSetChanged();
    }
    private void closeDelete(){
        for (int ind = 0; ind < hargaBahanList.size(); ind++){
            hargaBahanList.get(ind).setOpen(false);
            hargaBahanList.get(ind).setSelected(false);
        }
        mAdapter.notifyDataSetChanged();
    }
    private void callMenu(){
        if (getActivity()!=null){
            getActivity().invalidateOptionsMenu();
        }
    }
    private void clearMenu(View view){
        selected=0;
        sNama = dbmBahan.bahanBaku("",sId).getNama_bahan();
        callMenu();
        settoolbars(view);
        //mISetListener.setToolbarTitle(sNama);
        //mISetListener.setToolbarTitleListener(true,this);
       // mISetListener.setNavigationIcon(R.drawable.ic_arrow_back_white, true);
        closeDelete();
    }
    private void deleteDB(){
        for (MHargaBahan bahanBaku : selectedList){
            dbmHarga.deleteHarga(bahanBaku.getId());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.actdelete) {
            deleteDB();
            clearMenu(getView());
            mAdapter.delete(selectedList);
            cekEmptyList();
        }
        return super.onOptionsItemSelected(item);
    }
}
