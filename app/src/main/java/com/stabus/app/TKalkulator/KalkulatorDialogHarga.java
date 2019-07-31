package com.stabus.app.TKalkulator;

import android.app.Dialog;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.stabus.app.Database.DBMHarga;
import com.stabus.app.Interface.ISetListener;
import com.stabus.app.Interface.OnListener;
import com.stabus.app.Model.CollectBahanBaku;
import com.stabus.app.Model.CollectBahanCRUD;
import com.stabus.app.Model.CollectString;
import com.stabus.app.Model.CollectStringCRUD;
import com.stabus.app.Model.MHargaBahan;
import com.stabus.app.R;
import com.stabus.app.RecyclerView.HargaBahanAdapter;
import com.stabus.app.RecyclerView.RelasiAdapter;

import java.util.ArrayList;
import java.util.List;

public class KalkulatorDialogHarga implements OnListener , AdapterView.OnItemSelectedListener {

    private Dialog d ;
    private View view;
    private ISetListener mISetListener;
    private RelasiAdapter mAdapter;

    private TextView mTvJmlHarga;
    private LinearLayout next;
    //value
    private int pos;
    private int id_bahan;
    private String nama_bahan;

    private float harga_total = 0;
    private float harga_lama = 0;
    private boolean cekPilih;


    //widget view
    private TextView mTvTitle;
    private Spinner mSpTempat;
    private RecyclerView mRV;

    //list
    private List<MHargaBahan> hargaBahanList;

    //adapter
    private HargaBahanAdapter mHAdapter;

    //class
    private DBMHarga dbmHarga ;
    private CollectBahanCRUD crud;
    private CollectStringCRUD stringCRUD;
    private View.OnClickListener clickListener;

    public KalkulatorDialogHarga(View view, ISetListener mISetListener, RelasiAdapter mAdapter, View.OnClickListener clickListener) {
        this.view = view;
        this.mISetListener = mISetListener;
        this.mAdapter = mAdapter;
        this.clickListener = clickListener;

    }

    public void showDialog(int pos){
        this.pos = pos;

        d = new Dialog(view.getContext());
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d.setContentView(R.layout.dialog_pilih_harga);
        init();
        d.show();
    }
    private void init(){
        initView();
        initListener();
        initObject();
        initValue();
        setmTvTitle();
        setSPinner();
    }
    private void initView(){
        mTvTitle = d.findViewById(R.id.tvTitle);
        mSpTempat = d.findViewById(R.id.spTempat);
        mRV = d.findViewById(R.id.rvHarga);
        mTvJmlHarga = view.findViewById(R.id.tvJumlahHarga);
        next = view.findViewById(R.id.next);
    }
    private void initListener(){
        mSpTempat.setOnItemSelectedListener(this);
    }
    private void initObject(){
        dbmHarga = new DBMHarga(view);
        crud = new CollectBahanCRUD(CollectBahanBaku.getRelasi());
        stringCRUD = new CollectStringCRUD(CollectString.getString());
    }
    private void initValue(){
        id_bahan = crud.getRelasi().get(pos).getFk_id_bahan();
        nama_bahan = crud.getRelasi().get(pos).getNama_bahan();
        harga_total = stringCRUD.getString().get(0).getHarga_total();
        cekPilih = crud.getRelasi().get(pos).isPilihHarga();

        setJumlah_lama();
    }
    private void setmTvTitle(){
        mTvTitle.setText(nama_bahan);
    }
    private void setSPinner(){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext()
                ,android.R.layout.simple_spinner_dropdown_item, dbmHarga.getTempat(id_bahan));
        mSpTempat.setAdapter(adapter);
    }
    private void setmRV(){
        hargaBahanList = new ArrayList<>();
        mRV.setLayoutManager(new LinearLayoutManager(view.getContext()));
        mRV.setItemAnimator(new DefaultItemAnimator());
        mHAdapter = new HargaBahanAdapter(hargaBahanList,this,true);
        mISetListener.setRecyclerView(new LinearLayoutManager(view.getContext()), mRV, mHAdapter);
        mHAdapter.notifyDataSetChanged();
        setHargaBahanList();
    }
    private void setHargaBahanList(){
        String tempat = mSpTempat.getSelectedItem().toString();
        dbmHarga.getHarga(id_bahan, tempat, hargaBahanList);
        mHAdapter.notifyDataSetChanged();
    }

    private void setCrud(int i){
        float harga_pilih = hargaBahanList.get(i).getHarga();
        int isi_pilih = hargaBahanList.get(i).getIsi();
        String satuan_pilih = hargaBahanList.get(i).getSatuan();

        crud.getRelasi().get(pos).setHarga_pilih(harga_pilih);
        crud.getRelasi().get(pos).setIsi_pilih(isi_pilih);
        crud.getRelasi().get(pos).setSatuan_pilih(satuan_pilih);
        crud.getRelasi().get(pos).setPilihHarga(true);

        mAdapter.notifyDataSetChanged();


    }

    private void setHargaSatuan(){
        /*
        Total Harga = SUM( jumlah_terpakai/isi_total * Harga )
         */
        double jumlah_terpakai = crud.getRelasi().get(pos).getIsi_digunakan();
        double isi_total = crud.getRelasi().get(pos).getIsi_pilih();
        float harga = crud.getRelasi().get(pos).getHarga_pilih();


        float harga_bagi = (float) (jumlah_terpakai/isi_total * harga);

        if (!cekPilih){
            harga_total = harga_total + harga_bagi;
        }else {
            float hargaKurang = harga_total - harga_lama;
            harga_total = hargaKurang + harga_bagi;
        }
        stringCRUD.getString().get(0).setHarga_total(harga_total);
        mTvJmlHarga.setText(String.format("Rp. %,.0f", harga_total));
    }

    private void setJumlah_lama(){
        if (cekPilih){
            harga_lama = 0;
            double jumlah_lama = crud.getRelasi().get(pos).getIsi_digunakan();
            double isi_lama = crud.getRelasi().get(pos).getIsi_pilih();
            harga_lama = crud.getRelasi().get(pos).getHarga_pilih();
            harga_lama = (float) (jumlah_lama / isi_lama * harga_lama);
        }
    }

    private void setAddAll(){
        Resources res = view.getResources();
        Drawable drawFalse = res.getDrawable(R.drawable.shape_rec_darkgray) ;
        Drawable drawTrue = res.getDrawable(R.drawable.selector_rec) ;

        for (int i=0;i<crud.getRelasi().size();i++){
            boolean hargaready = crud.getRelasi().get(i).isPilihHarga();
            if (!hargaready){
                next.setClickable(false);
                next.setBackground(drawFalse);
                stringCRUD.getString().get(0).setSelectAll(false);
                next.setOnClickListener(null);
                break;
            }else {
                next.setClickable(true);
                next.setBackground(drawTrue);
                stringCRUD.getString().get(0).setSelectAll(true);
                next.setOnClickListener(clickListener);
            }
        }
    }

    @Override
    public void OnClickListener(int position, View view) {
        setCrud(position);
        d.dismiss();
        setHargaSatuan();
        setAddAll();

    }

    @Override
    public boolean OnLongListener(int position, View view) {
        return false;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position!=0){
            mRV.setVisibility(View.VISIBLE);
            setmRV();
        }else {
            mRV.setVisibility(View.GONE);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
