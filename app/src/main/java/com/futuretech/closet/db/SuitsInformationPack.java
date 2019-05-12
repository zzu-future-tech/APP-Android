package com.futuretech.closet.db;

import android.content.ContentValues;

public class SuitsInformationPack {
    private ContentValues values;
    public SuitsInformationPack(int dressid1, int dressid2, String userid){
        values =new ContentValues();
        values.put("dressid1",dressid1);
        values.put("dressid2",dressid2);
        values.put("userid",userid);
    }
    public ContentValues getValues(){
        return values;
    }
}
