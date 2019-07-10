package com.stabus.app.Model;

import java.util.List;

public class CollectAdd {

    List<MBahanBaku> bahanBakuList;

    public CollectAdd(List<MBahanBaku> bahanBakuList) {
        this.bahanBakuList = bahanBakuList;
    }
    //Add
    public boolean addNew(MBahanBaku bahanBaku){
        try {
            bahanBakuList.add(bahanBaku);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    //retrive

    public List<MBahanBaku> getBahanBakuList() {
        return bahanBakuList;
    }

    //update
    public boolean update(int position, MBahanBaku newBahanBaku){
        try {
            bahanBakuList.remove(position);
            bahanBakuList.add(position, newBahanBaku);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    //delete
    public boolean delete(int position){
        try {
            bahanBakuList.remove(position);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
