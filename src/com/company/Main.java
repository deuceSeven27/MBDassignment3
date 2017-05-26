package com.company;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
	// write your code here

        Basket b = new Basket("1 2 3 4 5");
        ArrayList<ItemSet> res =  b.generateCombinations(3);

        for(ItemSet i : res){
            System.out.println(i.getName());
        }
    }
}
