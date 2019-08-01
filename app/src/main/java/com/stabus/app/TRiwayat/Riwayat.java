package com.stabus.app.TRiwayat;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import android.widget.Toast;

import com.stabus.app.Database.DBMRiwayat;
import com.stabus.app.Interface.ISetListener;
import com.stabus.app.Interface.OnListener;
import com.stabus.app.Model.CollectString;
import com.stabus.app.Model.CollectStringCRUD;
import com.stabus.app.Model.MRiwayat;
import com.stabus.app.Model.MString;
import com.stabus.app.R;
import com.stabus.app.RecyclerView.RiwayatAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Riwayat extends Fragment implements OnListener, SearchView.OnQueryTextListener {

    View view;

    //tampilan
    private ScrollView scrollView;
    private FrameLayout frameLayout;
    private Toolbar toolbar;
    private SearchView searchView;
    private TextView titleToolbar;
    //widget
    private RecyclerView mRecyclerView;
    int selected;
    private List<MRiwayat> riwayatList;
    private List<MRiwayat> riwayatListFull;
    private List<MRiwayat> selectedList;
    private DBMRiwayat dbmRiwayat;
    private RiwayatAdapter mAdapter;

    private ISetListener mISetListener;
    private CollectStringCRUD stringCRUD;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_riwayat, container, false);
        init();
        assert getActivity() !=null;
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        return view;

    }

    private void init() {
        initView();
        initObject();
        setRV();
        initListener();
    }

    private void initView() {
        toolbar = view.findViewById(R.id.toolbarProduk);
        searchView = view.findViewById(R.id.svProduk);
        titleToolbar = view.findViewById(R.id.tvTitle);
        mRecyclerView = view.findViewById(R.id.rvProduk);
        scrollView = view.findViewById(R.id.scrollProduk);
        frameLayout = view.findViewById(R.id.frameEmpty);
    }

    private void initObject() {
        dbmRiwayat = new DBMRiwayat(view);
        riwayatListFull = new ArrayList<>();
        dbmRiwayat.getAllRiwayat(riwayatListFull);
        stringCRUD = new CollectStringCRUD(CollectString.getString());
    }

    private void initListener() {
        searchView.setOnQueryTextListener(this);
    }

    private void setRV(){
        riwayatList = new ArrayList<>();
        mAdapter = new RiwayatAdapter(riwayatList,this);
        mISetListener.setRecyclerView(new LinearLayoutManager(view.getContext()), mRecyclerView, mAdapter);
        mAdapter.notifyDataSetChanged();
        refreshList();
    }
    private void refreshList(){
        dbmRiwayat.getAllRiwayat(riwayatList);
        mAdapter.notifyDataSetChanged();
        cekEmptyList();
    }
    private void cekEmptyList(){
        if (riwayatList.size()==0){
            scrollView.setVisibility(View.GONE);
            frameLayout.setVisibility(View.VISIBLE);
        }else {
            scrollView.setVisibility(View.VISIBLE);
            frameLayout.setVisibility(View.GONE);
        }
    }

    private void delete(){
        for (MRiwayat riwayat: selectedList){
            //dbmProduk.deleteProduk(produkBahan.getId_produk());
            //delete relasi where id produk
            int fk_id = riwayat.getId_riwayat();
            dbmRiwayat.deleteRiwayat(fk_id);
            dbmRiwayat.deleteRiwayat(fk_id);
            Toast toast = Toast.makeText(getContext(), "Data Dihapus!", Toast.LENGTH_SHORT);
            toast.show();
        }
        //search id produk  di relasi
        //jika tidak ada produk makna delete
    }
    private void selectList(int position){
        if (riwayatList.get(position).isSelected()){
            selectedList.add(riwayatList.get(position));
            selected++;
        }else {
            selectedList.remove(riwayatList.get(position));
            selected--;
        }
    }
    private void clickFirstList(int position){
        riwayatList.get(position).setSelected(!riwayatList.get(position).isSelected());
        selectedList = new ArrayList<>();
        selectedList.add(riwayatList.get(position));
    }
    private void setToolbarHapus(){
        searchView.setVisibility(View.GONE);
        titleToolbar.setVisibility(View.VISIBLE);
        toolbar.setBackground(getResources().getDrawable(R.drawable.shape_cornor_upp));
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white);

        titleToolbar.setText(String.format(Locale.US,"%d item terpilih", selected));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearMenu();
            }
        });
        callMenu();
    }
    private void openDelete(){
        for (int ind = 0; ind < riwayatList.size(); ind++){
            riwayatList.get(ind).setHapus(true);
        }
        mAdapter.notifyDataSetChanged();
    }
    private void closeDelete(){
        for (int ind = 0; ind < riwayatList.size(); ind++){
            riwayatList.get(ind).setHapus(false);
            riwayatList.get(ind).setSelected(false);
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
        if (riwayatList.get(position).isHapus()){
            riwayatList.get(position).setSelected(!riwayatList.get(position).isSelected());
            selectList(position);
            titleToolbar.setText(String.format(Locale.US,"%d item terpilih", selected));
            mAdapter.notifyDataSetChanged();
            if (selected==0){
                clearMenu();
            }
        }else {
            MString string = new MString();
            string.setNama(riwayatList.get(position).getNama());
            string.setJumlah(riwayatList.get(position).getJumlah());
            string.setSatuan(riwayatList.get(position).getSatuan());
            string.setHarga_total(riwayatList.get(position).getHarga_pokok());
            string.setMargin_harga(riwayatList.get(position).getMargin_harga());
            string.setHarga_jual(riwayatList.get(position).getHarga_jual());
            if (stringCRUD.getString().size() > 0) stringCRUD.getString().clear();
            stringCRUD.addNew(string);
            mISetListener.inflateFragment(getString(R.string.KalkulatorGetHarga), null);
        }
    }

    @Override
    public boolean OnLongListener(int position, View view) {
        selected = selected+1;
        //set toolbar
        setToolbarHapus();
        //call All Open
        openDelete();
        //click list
        clickFirstList(position);
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
        mAdapter.filter(riwayatListFull, s);
        cekEmptyList();
        return true;
    }
}
