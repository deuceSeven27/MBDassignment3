package com.company;

import java.util.ArrayList;

/**
 * Created by Rajesh on 5/24/2017.
 */
public class Basket {

    ArrayList<Integer> container;

    public Basket(String line){
        container = new ArrayList<Integer>();
        String[] parts = line.split(" ");
        for(String item : parts){
            container.add(Integer.valueOf(item));
        }
    }

    public int size(){
        return container.size();
    }

    public Integer getItem(int i){
        return container.get(i);
    }

    public ArrayList<Integer> getBasket(){
        return container;
    }

    //creates all combinations of itemsets of some size, no repitition
    public ArrayList<ItemSet> generateCombinations(int size){
        ArrayList<ItemSet> temp = new ArrayList<ItemSet>();


    }
}
