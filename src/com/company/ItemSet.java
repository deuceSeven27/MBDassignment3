package com.company;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Rajesh on 5/24/2017.
 */
//contains an ordered set of items
    //representing an itemset
public class ItemSet {

    ArrayList<Integer> items;
    int count = 1;

    public int getCount(){
        return count;
    }

    public void incrementCount(){
        count++;
    }

    public ArrayList<Integer> getItemsArray(){
        return items;
    }

    //returns the name of the itemset,
    //used for hashing and counting
    //the name is a sorted, space separated string of items (numbers)
    public String getName(){
        String name = "";
        for(Integer i : items){
            name += String.valueOf(i) + " ";
        }
        return name;
    }

    public ItemSet(){
        items = new ArrayList<Integer>();
    }

    public void addItem(Integer i){
        items.add(i);
        Collections.sort(items);
    }

    public ItemSet(int itemsForSet){
        items = new ArrayList<Integer>();
        this.addItem(itemsForSet);
    }

    public ItemSet(ArrayList<Integer> itemsForSet){
        items = new ArrayList<Integer>();
        for(Integer i : itemsForSet){
            items.add(i);
        }
        Collections.sort(items);
    }


}
