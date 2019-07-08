package com.stabus.app;

import android.app.Activity;
import android.view.View;

import com.stabus.app.Database.DBMBahan;
import com.stabus.app.Interface.ISetListener;
import com.stabus.app.Model.MBahanBaku;
import com.stabus.app.RecyclerView.BahanBakuAdapter;

import java.util.ArrayList;
import java.util.List;

public class DialogHapus {

    private View view;
    private List<MBahanBaku> bahanBakuList;
    private List<MBahanBaku> selectedList;
    private BahanBakuAdapter mAdapter;
    private int selected;
    private DBMBahan dbmBahan;
    private Activity activity;

    private ISetListener mISetListener;

    public DialogHapus(View view, List<MBahanBaku> bahanBakuList, List<MBahanBaku> selectedList, BahanBakuAdapter mAdapter, int selected, DBMBahan dbmBahan, Activity activity, ISetListener mISetListener) {
        this.view = view;
        this.bahanBakuList = bahanBakuList;
        this.selectedList = selectedList;
        this.mAdapter = mAdapter;
        this.selected = selected;
        this.dbmBahan = dbmBahan;
        this.activity = activity;
        this.mISetListener = mISetListener;
    }

    public void selectList(int position){
        if (bahanBakuList.get(position).isSelected()){
            selectedList.add(bahanBakuList.get(position));
            selected++;
        }else {
            selectedList.remove(bahanBakuList.get(position));
            selected--;
        }
    }
    public void clickFirstList(int position){
        bahanBakuList.get(position).setSelected(!bahanBakuList.get(position).isSelected());
        selectedList = new ArrayList<>();
        selectedList.add(bahanBakuList.get(position));
    }
    public void setToolbarHapus(){
        mISetListener.setToolbarTitle(selected +" item selected");
        mISetListener.setNavigationListener(R.drawable.ic_arrow_back_white, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearMenu();
            }
        });
        callMenu();
    }
    public void openDelete(){
        for (int ind = 0; ind < bahanBakuList.size(); ind++){
            bahanBakuList.get(ind).setOpen(true);
        }
        mAdapter.notifyDataSetChanged();
    }
    public void closeDelete(){
        for (int ind = 0; ind < bahanBakuList.size(); ind++){
            bahanBakuList.get(ind).setOpen(false);
            bahanBakuList.get(ind).setSelected(false);
        }
        mAdapter.notifyDataSetChanged();
    }
    public void callMenu(){
        if (activity!=null){
            activity.invalidateOptionsMenu();
        }
    }
    public void clearMenu(){
        selected=0;
        callMenu();
        mISetListener.setToolbarTitle(view.getTag().toString());
        mISetListener.setNavigationIcon(R.drawable.ic_home_white, false);
        closeDelete();
    }
    public void deleteDB(){
        for (MBahanBaku bahanBaku : selectedList){
            dbmBahan.delete(bahanBaku.getId());
        }
    }
    public void hapusList(){
        deleteDB();
        clearMenu();
        mAdapter.delete(selectedList);
    }

}
