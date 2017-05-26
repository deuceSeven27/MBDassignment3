package com.company;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
	    // write your code here
        APriori ap = new APriori(args[0]);
        ap.runApriori(2);

    }
}
