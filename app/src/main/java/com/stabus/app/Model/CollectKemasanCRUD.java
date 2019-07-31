package com.stabus.app.Model;

import java.util.List;

public class CollectKemasanCRUD {

    //List<MBahanBaku> bahanBakuList;
    List<MKemasan> kemasanList;

    public CollectKemasanCRUD(List<MKemasan> kemasanList) {
        this.kemasanList = kemasanList;
    }

    //Add
    public boolean addNew(MKemasan kemasan){
        try {
            kemasanList.add(kemasan);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    //retrive
    public List<MKemasan> getKemasanList() {
        return kemasanList;
    }
}
