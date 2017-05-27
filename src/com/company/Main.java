package com.company;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
	    // write your code here
        APriori ap = new APriori(args[0]);
        //ap.runApriori(2);
        ArrayList<Integer> temp = new ArrayList<Integer>();
        for(int i = 1; i < 5; i++){
            temp.add(i);
        }

        ArrayList<ItemSet> res = Combinations.createCombinations(temp, 2);

        for (ItemSet is : res){
            System.out.println(is.getName());
        }
    }
}
