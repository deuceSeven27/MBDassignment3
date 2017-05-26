package com.company;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Rajesh on 5/24/2017.
 */
public class Basket {

    ArrayList<Integer> container;

    public Basket(String line){
        container = new ArrayList<Integer>();
        if(line.equals("")) return;
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

    //creates all combinations of itemsets of some size, no repetition
    public ArrayList<ItemSet> generateCombinations(int size){

        Integer[] temp = new Integer[size];
        ArrayList<ItemSet> results = new ArrayList<ItemSet>();

        genComRecursion(0, this.size() - 1, temp, size, 0, results);

        return results;
    }

    private void genComRecursion(int start, int end, Integer[] temp, int size, int tempIndex, ArrayList<ItemSet> results){

        if(tempIndex == size){

            results.add(new ItemSet(new ArrayList<>(Arrays.asList(temp))));
            return;

        }else{

            for(int i = start; i <= end; i++){

                temp[tempIndex] = container.get(i);

                genComRecursion(i + 1, end, temp, size, tempIndex + 1, results);
            }
        }
    }

}
