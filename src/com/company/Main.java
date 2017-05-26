package com.company;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
	// write your code here

        Basket b = new Basket("3 2 1");
        ArrayList<ItemSet> res =  b.generateCombinations(2);

        for(ItemSet i : res){
            System.out.println(i.getName());
        }
    }
}
