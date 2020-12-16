package com.example.atelierul_digital.week_4;

import java.util.ArrayList;
import java.util.List;

public class DataSource {
    public static List<Item>getItemData(int itemCount){
        List<Item>itemList = new ArrayList<>();
        for (int i=0; i<itemCount; i++){
            itemList.add(new Item("FirstName "+i,"LastName "+i));
        }
        return itemList;
    }

}
