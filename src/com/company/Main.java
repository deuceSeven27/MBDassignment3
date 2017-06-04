package com.company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        SimpleRandomisedAlgorithm sra = new SimpleRandomisedAlgorithm();

        ArrayList<Basket> sample = sra.createSample(args[0], 0.1);

        /*for(Basket b : sample){
            System.out.println(b.getRawInput());
        }*/
        System.out.println("Sample size: " + sample.size());

        APriori ap = new APriori(sample);

        ArrayList<HashMap<String, ItemSet>> freqItems = ap.runApriori(500);

    }
}
