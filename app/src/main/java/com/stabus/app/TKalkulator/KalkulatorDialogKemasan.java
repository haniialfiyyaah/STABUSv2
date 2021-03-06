package com.stabus.app.TKalkulator;

import android.app.Dialog;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.view.Window;
import android.widget.Spinner;
import android.widget.TextView;

import com.stabus.app.Class.MyTextWatcher;
import com.stabus.app.Model.CollectKemasan;
import com.stabus.app.Model.CollectKemasanCRUD;
import com.stabus.app.Model.CollectString;
import com.stabus.app.Model.CollectStringCRUD;
import com.stabus.app.Model.MKemasan;
import com.stabus.app.R;
import com.stabus.app.RecyclerView.KemasanAdapter;

import java.util.Locale;

public class KalkulatorDialogKemasan implements View.OnClickListener {

    private Dialog d ;
    private View view;

    private KemasanAdapter mAdapter;

    //widget
    private TextInputLayout mTxNama;
    private TextInputLayout mTxJumlah;
    private Spinner mSpSatuan;
    private TextInputLayout mTxHarga;
    private MaterialButton mfabOk;
    private TextView mTvJmlHarga;

    //value
    private String mNama;
    private String mSatuan;
    private int mJumlah;
    private float mHarga;

    private CollectStringCRUD stringCRUD;
    private CollectKemasanCRUD kemasanCRUD;


    //value
    KalkulatorDialogKemasan(View view, KemasanAdapter mAdapter) {
        this.view = view;
        this.mAdapter = mAdapter;
    }

    public void showDialog(){
        d = new Dialog(view.getContext());
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d.setContentView(R.layout.dialog_pilih_kemasan);
        init();
        d.show();
    }
    private void init() {
        initView();
        initListener();
        initObject();
    }
    private void initView(){
        mTxNama = d.findViewById(R.id.textNama);
        mTxJumlah = d.findViewById(R.id.textJumlahdg);
        mSpSatuan = d.findViewById(R.id.spSatuan);
        mTxHarga = d.findViewById(R.id.textHarga);
        mfabOk = d.findViewById(R.id.fabOk);
        mTvJmlHarga = view.findViewById(R.id.tvJumlahHarga);
    }
    private void initListener(){
        mfabOk.setOnClickListener(this);
        if (mTxNama.getEditText()!=null&&mTxHarga.getEditText()!=null&&mTxJumlah.getEditText()!=null) {
            mTxNama.getEditText().addTextChangedListener(new MyTextWatcher(mTxNama, "SetNull"));
            mTxHarga.getEditText().addTextChangedListener(new MyTextWatcher(mTxHarga, "SetNull"));
            mTxJumlah.getEditText().addTextChangedListener(new MyTextWatcher(mTxJumlah, "SetNull"));
        }
    }

    private void initObject(){
        stringCRUD = new CollectStringCRUD(CollectString.getString());
        kemasanCRUD = new CollectKemasanCRUD(CollectKemasan.getKemasanList());
    }
    private void getValue(){
        if (mTxNama.getEditText()!=null&&mTxJumlah.getEditText()!=null&&mTxHarga.getEditText()!=null) {
            mNama = mTxNama.getEditText().getText().toString();
            mJumlah = Integer.valueOf(mTxJumlah.getEditText().getText().toString());
            mSatuan = mSpSatuan.getSelectedItem().toString();
            mHarga = Float.valueOf(mTxHarga.getEditText().getText().toString());
        }
    }
    private void setErrorMessage(TextInputLayout inputLayout){
        clearError();
        inputLayout.setError("Tidak Boleh Kosong");
        inputLayout.requestFocus();
    }
    private void clearError(){
        mTxNama.setError(null);
        mTxJumlah.setError(null);
        mTxHarga.setError(null);
    }
    private void clearText(){
        if (mTxNama.getEditText()!=null&&mTxHarga.getEditText()!=null&&mTxJumlah.getEditText()!=null) {
            mTxNama.getEditText().setText("");
            mTxJumlah.getEditText().setText("");
            mTxHarga.getEditText().setText("");
        }
    }
    private void setKemasanCRUD(){
        if (mTxNama.getEditText()!=null&&mTxHarga.getEditText()!=null&&mTxJumlah.getEditText()!=null) {
            if (mTxNama.getEditText().getText().toString().trim().length() <= 0) {
                setErrorMessage(mTxNama);
                return;
            }
            if (mTxJumlah.getEditText().getText().toString().trim().length() <= 0) {
                setErrorMessage(mTxJumlah);
                return;
            }
            if (mTxHarga.getEditText().getText().toString().trim().length() <= 0) {
                setErrorMessage(mTxHarga);
                return;
            }
            getValue();
            MKemasan kemasan = new MKemasan();
            kemasan.setNama(mNama);
            kemasan.setJumlah(mJumlah);
            kemasan.setSatuan(mSatuan);
            kemasan.setHarga(mHarga);
            kemasanCRUD.addNew(kemasan);
            mAdapter.notifyDataSetChanged();
            setHargaSatuan();
            clearError();
            clearText();
            d.dismiss();
        }
    }
    private void setHargaSatuan(){
        /*
        Total Harga = SUM( jumlah_terpakai/1 * Harga )
         */
        float harga_total = stringCRUD.getString().get(0).getHarga_total();
        float harga_bagi =  mJumlah*mHarga ;

        harga_total = harga_total + harga_bagi;

        stringCRUD.getString().get(0).setHarga_total(harga_total);
        mTvJmlHarga.setText(String.format(Locale.US,"Rp. %,.0f", harga_total));
    }


    @Override
    public void onClick(View v) {
        if (v==mfabOk){
            setKemasanCRUD();
        }
    }
}
