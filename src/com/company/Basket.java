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
}
