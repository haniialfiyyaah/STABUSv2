package com.stabus.app;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.stabus.app.Database.DBMBahan;
import com.stabus.app.Database.DBMHarga;
import com.stabus.app.Interface.ISetListener;
import com.stabus.app.Interface.OnListener;
import com.stabus.app.Model.CollectBahanBaku;
import com.stabus.app.Model.CollectBahanCRUD;
import com.stabus.app.Model.MBahanBaku;
import com.stabus.app.RecyclerView.BahanBakuAdapter;
import com.stabus.app.RecyclerView.HargaBahanAdapter;

import java.util.ArrayList;
import java.util.List;

public class ProdukPilihBahan extends Fragment implements OnListener, View.OnClickListener, SearchView.OnQueryTextListener {

    //widget
    ScrollView scrollView;
    FrameLayout frameLayout;
    SearchView searchView;
    Toolbar toolbar;
    ImageButton imageButton;
    RecyclerView recyclerView;
    FloatingActionButton fab;

    //class
    ISetListener mISetListener;
    DBMBahan dbmBahan;
    DBMHarga dbmHarga;
    BahanBakuAdapter mAdapter;
    ClassDialogTambah dialogTambah;
    CollectBahanCRUD crud;

    //list
    List<MBahanBaku> bahanBakuList;
    List<MBahanBaku> bahanBakuListFull;

    //widget dialog
    TextView nmBahan;
    TextInputLayout mJumlah;
    Spinner mSatuan;
    ImageButton mOk;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_pilih_bahan, container, false);
        initView(view);
        initListener();
        initObject(view);
        return view;
    }

    private void initView(View view){
        searchView = view.findViewById(R.id.svPilihBK);
        toolbar = view.findViewById(R.id.toolbarPilihBK);
        imageButton = view.findViewById(R.id.pilihTambah);
        recyclerView = view.findViewById(R.id.rvPilihBahan);
        fab = view.findViewById(R.id.fabPilihBahan);
        scrollView = view.findViewById(R.id.scrollRV);
        frameLayout = view.findViewById(R.id.frameEmpty);
    }
    private void initObject(View view){
        dbmBahan = new DBMBahan(view);
        dbmHarga = new DBMHarga(view);
        setRV(view);
        bahanBakuListFull = new ArrayList<>();
        dbmBahan.getAllBahan(bahanBakuListFull);
        dialogTambah = new ClassDialogTambah(getString(R.string.BahanBaku), getActivity(), view, bahanBakuList, mAdapter, frameLayout, scrollView);
        //selected list
        crud=new CollectBahanCRUD(CollectBahanBaku.getBahanBakuList());

        setSelected();
    }
    private void initListener(){
        fab.setOnClickListener(this);
        searchView.setOnQueryTextListener(this);
        imageButton.setOnClickListener(this);
    }
    private void setRV(View view){
        bahanBakuList = new ArrayList<>();
        mAdapter = new BahanBakuAdapter(bahanBakuList, this, true);
        mISetListener.setRecyclerView(new LinearLayoutManager(view.getContext()), recyclerView, mAdapter);
        mAdapter.notifyDataSetChanged();
        refreshList();
    }
    private void refreshList(){
        dbmBahan.getAllBahan(bahanBakuList);
        mAdapter.notifyDataSetChanged();
        cekEmptyList();
    }
    private void cekEmptyList(){
        if (bahanBakuList.size()==0){
            scrollView.setVisibility(View.GONE);
            frameLayout.setVisibility(View.VISIBLE);
        }else {
            scrollView.setVisibility(View.VISIBLE);
            frameLayout.setVisibility(View.GONE);
        }
    }

    private void setSelected(){
        for (int x = 0 ; x < bahanBakuList.size() ; x++){
            MBahanBaku bahanBaku = bahanBakuList.get(x);
            String nama_bahan = bahanBaku.getNama_bahan();
            for (int pos=0; pos< crud.getBahanBakuList().size();pos++){
                MBahanBaku selected = crud.getBahanBakuList().get(pos);
                String nama_select = selected.getNama_bahan();
                if (nama_bahan.matches(nama_select)){
                    bahanBaku.setSelected(true);
                    bahanBaku.setPos(pos);
                }
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    private void showDialog(View view, final int pos){
        final Dialog d = new Dialog(view.getContext());
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d.setContentView(R.layout.list_pilih_jumlahdg);

        nmBahan = d.findViewById(R.id.tvNmBahan);
        mJumlah = d.findViewById(R.id.textJumlahdg);
        mSatuan = d.findViewById(R.id.spSatuan);
        mOk = d.findViewById(R.id.fabOk);

        MBahanBaku bahanBaku = bahanBakuList.get(pos);
        String satuan = dbmHarga.hargaBahan(0,bahanBaku.getId()).getSatuan();
        String nama = bahanBaku.getNama_bahan();
        if (satuan.equals("ml")) {
            mSatuan.setSelection(0);
        } else if (satuan.equals("gr")) {
            mSatuan.setSelection(1);
        } else {
            mSatuan.setSelection(2);
        }
        mSatuan.setEnabled(false);
        nmBahan.setText(nama);

        //set jumlah on edit
        if (bahanBaku.isSelected()){
            int poscrud = bahanBaku.getPos();
            mJumlah.getEditText().setText(String.valueOf(crud.getBahanBakuList().get(poscrud).getJumlah_dg()));
        }
        d.show();

        mOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pilihBahan(pos,d);
            }
        });

    }

    private void pilihBahan(int pos, Dialog dialog){
        MBahanBaku bahanBaku = new MBahanBaku();

        if (!(mJumlah.getEditText().getText().toString().trim().length() >0)){
            mJumlah.setError("Tidak Boleh Kosong");
            return;
        }

        //set id_bahan to crud
        int id_bahan = bahanBakuList.get(pos).getId();
        bahanBaku.setId(id_bahan);
        //set jumlahdg to crud
        int jumlah_dg = Integer.parseInt(mJumlah.getEditText().getText().toString());
        bahanBaku.setJumlah_dg(jumlah_dg);
        //set satuan to crud
        String satuan = mSatuan.getSelectedItem().toString();
        bahanBaku.setSatuan_dg(satuan);
        //set nama bahan to crud
        String nama = bahanBakuList.get(pos).getNama_bahan();
        bahanBaku.setNama_bahan(nama);

        //add to crud
        if (crud.addNew(bahanBaku)){
            dialog.dismiss();

            //set selected true to bahan baku list
            bahanBakuList.get(pos).setSelected(true);
            mAdapter.notifyDataSetChanged();
        }
        //set pos to bahan baku list
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mISetListener = (ISetListener)getActivity();
    }

    @Override
    public void OnClickListener(int position, View view) {
        showDialog(view,position);
    }

    @Override
    public boolean OnLongListener(int position, View view) {
        return false;
    }

    @Override
    public void onClick(View v) {
        if (v==imageButton){
            dialogTambah.tambahDialog(getString(R.string.TambahBahan),0);
        }else if (v==fab){
            getActivity().onBackPressed();
        }
    }

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
}
