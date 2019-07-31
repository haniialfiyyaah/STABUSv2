package com.stabus.app.TKalkulator;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.button.MaterialButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.stabus.app.Interface.ISetListener;
import com.stabus.app.Interface.OnListener;
import com.stabus.app.Model.CollectKemasan;
import com.stabus.app.Model.CollectKemasanCRUD;
import com.stabus.app.Model.CollectString;
import com.stabus.app.Model.CollectStringCRUD;
import com.stabus.app.R;
import com.stabus.app.RecyclerView.KemasanAdapter;

import java.util.Locale;

public class KalkulatorSetKemasan extends Fragment implements View.OnClickListener, OnListener {

    View view;
    private TextView mTvProdukName;
    private TextView mTvJmlProduk;
    private TextView mTvJmlHarga;
    private MaterialButton mBAddKemasan;
    private RecyclerView mRV;
    private LinearLayout nextLine;

    private ISetListener mISetListener;
    private KemasanAdapter mAdapter;
    private CollectKemasanCRUD kemasanCRUD;
    private CollectStringCRUD stringCRUD;

    private KalkulatorDialogKemasan dialogKemasan;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_kalkulator_kemasan,container,false);
        init();
        return view;
    }

    private void init(){
        initView();
        initObject();
        initListener();
        setNamaProduk();
        setRV();
        dialogKemasan = new KalkulatorDialogKemasan(view,mAdapter);
    }
    private void initView(){
        mTvProdukName = view.findViewById(R.id.tvProdukName);
        mTvJmlProduk = view.findViewById(R.id.tvJmlProduk);
        mTvJmlHarga = view.findViewById(R.id.tvJumlahHarga);
        mRV = view.findViewById(R.id.rvPilihHarga);
        nextLine = view.findViewById(R.id.next);
        mBAddKemasan = view.findViewById(R.id.addKemasan);
    }
    private void initObject(){
        //crud = new CollectBahanCRUD(CollectBahanBaku.getRelasi());
        stringCRUD = new CollectStringCRUD(CollectString.getString());
        kemasanCRUD = new CollectKemasanCRUD(CollectKemasan.getKemasanList());
    }
    private void initListener(){
        nextLine.setOnClickListener(this);
        mBAddKemasan.setOnClickListener(this);
    }
    private void setRV(){
        mRV.setLayoutManager(new LinearLayoutManager(view.getContext()));
        mRV.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new KemasanAdapter(kemasanCRUD.getKemasanList(), this);
        new ItemTouchHelper(callback).attachToRecyclerView(mRV);
        mISetListener.setRecyclerView(new LinearLayoutManager(view.getContext()), mRV, mAdapter);
        //mAdapter.notifyDataSetChanged();
        mAdapter.notifyDataSetChanged();
    }

    private void setNamaProduk(){
        mTvProdukName.setText(stringCRUD.getString().get(0).getNama());
        mTvJmlProduk.setText(String.format(Locale.US,"%d %s", stringCRUD.getString().get(0).getJumlah(), stringCRUD.getString().get(0).getSatuan()));
        mTvJmlHarga.setText(String.format(Locale.US,"Rp. %,.0f", stringCRUD.getString().get(0).getHarga_total()));
    }

    @Override
    public void onClick(View v) {
        if (v==mBAddKemasan){
            dialogKemasan.showDialog();
        }if (v==nextLine){
            mISetListener.inflateFragment(getString(R.string.KalkulatorSetKebutuhan),null);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mISetListener = (ISetListener) getActivity();
    }

    @Override
    public void OnClickListener(int position, View view) {

    }

    @Override
    public boolean OnLongListener(int position, View view) {
        return false;
    }
    ItemTouchHelper.SimpleCallback callback =
            new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT|ItemTouchHelper.LEFT) {
                @Override
                public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                    return false;
                }

                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                    float harga_kemasan = kemasanCRUD.getKemasanList().get(viewHolder.getAdapterPosition()).getHarga();
                    float jumlah = kemasanCRUD.getKemasanList().get(viewHolder.getAdapterPosition()).getJumlah();
                    float harga_bagi = harga_kemasan*jumlah;
                    float harga_total = stringCRUD.getString().get(0).getHarga_total()-harga_bagi;

                    stringCRUD.getString().get(0).setHarga_total(harga_total);
                    kemasanCRUD.delete(viewHolder.getAdapterPosition());
                    mTvJmlHarga.setText(String.format(Locale.US,"Rp. %,.0f ", stringCRUD.getString().get(0).getHarga_total()));

                    mAdapter.notifyDataSetChanged();
                }
            };
}
