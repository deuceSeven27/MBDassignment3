package com.company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
	    // write your code here
        APriori ap = new APriori(args[0]);
        ArrayList<HashMap<String, ItemSet>> freqItems = ap.runApriori(3);
        int c = 1;
        for (HashMap<String, ItemSet> L : freqItems){
            System.out.println("Now on " + c);
            c++;
            for (Map.Entry<String, ItemSet> is : L.entrySet()){
                System.out.println(is.getKey());
            }
        }


    }
}
