package com.stabus.app;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
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

    private ClassDialogTambah dialogTambah;

    Toolbar toolbar;
    TextView title;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //mISetListener.setToolbarTitle(getTag());
        //mISetListener.setNavigationIcon(R.drawable.ic_home_white, false);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bahan, container, false);
        initView(view);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
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
        toolbar = view.findViewById(R.id.toolbarBK);
        title = view.findViewById(R.id.tvTitle);
    }
    private void initObject(View view){
        settoolbaron();
        dbmBahan = new DBMBahan(view);
        setRV(view);
        dialogTambah = new ClassDialogTambah(getString(R.string.BahanBaku), getActivity(), view, bahanBakuList, mAdapter, frameRV, scrollView);
    }
    private void setRV(View view){
        bahanBakuList = new ArrayList<>();
        mAdapter = new BahanBakuAdapter(bahanBakuList,this,false);
        mISetListener.setRecyclerView(new LinearLayoutManager(view.getContext()), mRecyclerView, mAdapter);
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

        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Cari Bahan Baku..");
        searchView.setPadding(-22, 0, 0, 0);

        if (selected<=0){
            menu.findItem(R.id.actsearch).setVisible(true);
            menu.findItem(R.id.actdelete).setVisible(false);
        }
        else{
            menu.findItem(R.id.actsearch).setVisible(false);
            menu.findItem(R.id.actdelete).setVisible(true);
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
                cekEmptyList();
                return true;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onClick(View v) {
        if (v==fabTambah){
            callMenu();
            dialogTambah.tambahDialog(getString(R.string.TambahBahan),0);
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
            //mISetListener.setToolbarTitle(selected +" item terpilih");
            title.setText(selected +" item terpilih");
            mAdapter.notifyDataSetChanged();
        }
        else {
            Bundle bundle = new Bundle();
            bundle.putInt(getString(R.string.selectedId), sId);
            bundle.putString(getString(R.string.selectedNama), sNama);
            bundle.putString(getString(R.string.selectedKategori), sKategori);
            mISetListener.inflateFragment(getString(R.string.HargaBahanBaku), bundle);
            //title.setText(selected +" item terpilih");
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
        title.setText(selected +" item terpilih");
        //mISetListener.setToolbarTitle(selected +" item terpilih");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearMenu();
            }
        });
/*        mISetListener.setNavigationListener(R.drawable.ic_arrow_back_white, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearMenu();
            }
        });*/
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
        settoolbaron();
        //mISetListener.setToolbarTitle(getTag());
        //mISetListener.setNavigationIcon(R.drawable.ic_home_white, false);
        closeDelete();
    }
    private void deleteDB(){
        for (MBahanBaku bahanBaku : selectedList){
            dbmBahan.delete(bahanBaku.getId());
            dbmBahan.deleteHarga(bahanBaku.getId());
        }
    }

    @Override
    public void onResume() {
        settoolbaron();
        callMenu();
        super.onResume();

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

    private void settoolbaron(){
        title.setText(getTag());
        toolbar.setNavigationIcon(R.drawable.ic_home_white);
    }



}
