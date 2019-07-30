package com.stabus.app.Model;

import java.util.ArrayList;
import java.util.List;

public class CollectStringCRUD {

    //List<MBahanBaku> bahanBakuList;
    List<MString> stringList;

    public CollectStringCRUD(List<MString> stringList) {
        this.stringList = stringList;
    }

    //Add
    public boolean addNew(MString string){
        try {
            stringList.add(string);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    //retrive
    public List<MString> getString() {
        return stringList;
    }

}



//index 0 = spinner
//index 1 = namaproduk
//index 2 = jumlah
