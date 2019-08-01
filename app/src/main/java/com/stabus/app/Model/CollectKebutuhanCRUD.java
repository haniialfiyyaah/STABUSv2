package com.stabus.app.Model;

import java.util.List;

public class CollectKebutuhanCRUD {

    private List<MKebutuhan> kebutuhanList;

    public CollectKebutuhanCRUD(List<MKebutuhan> kebutuhanList) {
        this.kebutuhanList = kebutuhanList;
    }

    //Add
    public boolean addNew(MKebutuhan kebutuhan){
        try {
            kebutuhanList.add(kebutuhan);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    //retrive
    public List<MKebutuhan> getKebutuhanList() {
        return kebutuhanList;
    }
    //delete
    public boolean delete(int position){
        try {
            kebutuhanList.remove(position);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
