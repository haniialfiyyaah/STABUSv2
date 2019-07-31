package com.stabus.app.Model;

import java.util.List;

public class CollectBahanCRUD {

    //List<MBahanBaku> bahanBakuList;
    List<MProdukRelasi> produkRelasiList;

    public CollectBahanCRUD(List<MProdukRelasi> produkRelasiList) {
        this.produkRelasiList = produkRelasiList;
    }

    //Add
    public boolean addNew(MProdukRelasi produkRelasi){
        try {
            produkRelasiList.add(produkRelasi);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    //retrive
    public List<MProdukRelasi> getRelasi() {
        return produkRelasiList;
    }
    //update
    public boolean update(int position, MProdukRelasi newRelasi){
        try {
            produkRelasiList.remove(position);
            produkRelasiList.add(position, newRelasi);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    //delete
    public boolean delete(int position){
        try {
            produkRelasiList.remove(position);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
