package com.company;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Rajesh on 5/24/2017.
 */
//contains an ordered set of items
//representing an itemset.
    //can consists of itemsets as well as Integers.
public class ItemSet <T extends  Comparable>{

    ArrayList<T> items;
    int count = 1;

    public int getCount(){
        return count;
    }

    public void incrementCount(){
        count++;
    }

    public ArrayList<T> getItemsArray(){
        return items;
    }

    public int getSize(){
        return items.size();
    }

    public T getItem(int i){
        return items.get(i);
    }

    //returns the name of the itemset,
    //used for hashing and counting
    //the name is a sorted, space separated string of items (numbers)
    public String getName(){
        String name = "";
        for(T i : items){
            name += String.valueOf(i) + " ";
        }
        return name;
    }

    public ItemSet(){
        items = new ArrayList<T>();
    }

    public void addItem(T i){
        items.add(i);
        Collections.sort(items);
    }

    public ItemSet(T itemsForSet){
        items = new ArrayList<T>();
        this.addItem(itemsForSet);
    }

    public ItemSet(ArrayList<T> itemsForSet){
        items = new ArrayList<T>();
        for(T i : itemsForSet){
            items.add(i);
        }
        Collections.sort(items);
    }


    /*public int compareTo(ItemSet other){
        if(this.getSize() > other.getSize()){
            return 1;
        }else if(this.getSize() == other.getSize()){
            //then compare elements
            int index = 0;
            while(true){
                if(this.getItem(index) == other.getItem(index)){
                    index++;
                    continue;
                }else if(this.getItem(index) > other.getItem(index)){
                    return 1;
                }else{
                    return -1;
                }
            }
            return 0;
        }else{
            return -1
        }
    }*/

    /*
    *
    * support for holding itemsets
    *
    * */



}
