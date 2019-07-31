package com.stabus.app.TKalkulator;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.stabus.app.Interface.ISetListener;
import com.stabus.app.Interface.OnListener;
import com.stabus.app.Model.CollectBahanBaku;
import com.stabus.app.Model.CollectBahanCRUD;
import com.stabus.app.Model.CollectString;
import com.stabus.app.Model.CollectStringCRUD;
import com.stabus.app.R;
import com.stabus.app.RecyclerView.RelasiAdapter;

public class KalkulatorSetHarga extends Fragment implements OnListener, View.OnClickListener {

    View view;
    private TextView mTvProdukName;
    private TextView mTvJmlProduk;
    private TextView mTvJmlHarga;
    private RecyclerView mRV;
    private LinearLayout nextLine;

    private RelasiAdapter mAdapter;
    private ISetListener mISetListener;

    private CollectStringCRUD stringCRUD;
    private CollectBahanCRUD crud;

    private KalkulatorDialogHarga dialogHarga;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_kalkulator_setharga, container, false);
        init();
        return view;
    }

    private void init(){
        initView();
        initObject();
        initListener();
        setNamaProduk();
        setRV();
        dialogHarga = new KalkulatorDialogHarga(view, mISetListener, mAdapter, this);
    }

    private void initView(){
        mTvProdukName = view.findViewById(R.id.tvProdukName);
        mTvJmlProduk = view.findViewById(R.id.tvJmlProduk);
        mTvJmlHarga = view.findViewById(R.id.tvJumlahHarga);
        mRV = view.findViewById(R.id.rvPilihHarga);
        nextLine = view.findViewById(R.id.next);
    }
    private void initObject(){
        crud = new CollectBahanCRUD(CollectBahanBaku.getRelasi());
        stringCRUD = new CollectStringCRUD(CollectString.getString());

    }
    private void initListener(){
        Resources res = view.getResources();
        Drawable drawFalse = res.getDrawable(R.drawable.shape_rec_darkgray) ;
        Drawable drawTrue = res.getDrawable(R.drawable.selector_rec);
        if (stringCRUD.getString().get(0).isSelectAll()){
            nextLine.setOnClickListener(this);
            nextLine.setBackground(drawTrue);
        }else {
            nextLine.setOnClickListener(null);
            nextLine.setBackground(drawFalse);
        }
    }
    private void setRV(){
        mRV.setLayoutManager(new LinearLayoutManager(view.getContext()));
        mRV.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new RelasiAdapter(crud.getRelasi(), this, true);
        mISetListener.setRecyclerView(new LinearLayoutManager(view.getContext()), mRV, mAdapter);
        //mAdapter.notifyDataSetChanged();
        mAdapter.notifyDataSetChanged();
    }

    @SuppressLint("DefaultLocale")
    private void setNamaProduk(){
        mTvProdukName.setText(stringCRUD.getString().get(0).getNama());
        mTvJmlProduk.setText(String.format("%d %s", stringCRUD.getString().get(0).getJumlah(), stringCRUD.getString().get(0).getSatuan()));
        mTvJmlHarga.setText(String.format("Rp. %,.0f", stringCRUD.getString().get(0).getHarga_total()));

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mISetListener = (ISetListener) getActivity();
    }

    @Override
    public void OnClickListener(int position, View view) {
        dialogHarga.showDialog(position);
    }

    @Override
    public boolean OnLongListener(int position, View view) {
        return false;
    }

    @Override
    public void onClick(View v) {
        if (v==nextLine){
            mISetListener.inflateFragment(getString(R.string.KalkulatorSetKemasan),null);
        }
    }

}
