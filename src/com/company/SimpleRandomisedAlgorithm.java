package com.company;

import jdk.nashorn.internal.runtime.ECMAErrors;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Rajesh on 5/27/2017.
 */
/*
*
* This class picks of a random subset of the baskets,
* and passes it to the some FrequentItemset algorithm
* passes on a ArrayList<Basket>
* */
public class SimpleRandomisedAlgorithm {

    public SimpleRandomisedAlgorithm(){}

    //creates a sample of a data file into an arrayList of baskets
    public ArrayList<Basket> createSample(String file, double probability){

        ArrayList<Basket> sample = new ArrayList<Basket>();

        Random rand = new Random();

        try{
            BufferedReader br = new BufferedReader( new FileReader(file));

            String line;
            while ((line = br.readLine()) != null){

                //randomly add to basketArrayList
                double chance = rand.nextDouble();

                if(chance < probability){
                    Basket b = new Basket(line);
                    sample.add(b);
                }
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return sample;
    }

}
