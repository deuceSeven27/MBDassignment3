package com.company;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Rajesh on 5/26/2017.
 */
public final class Combinations {

    private  Combinations(){}

    //create and returns all combinations OF ITEMSETS (of some size) of some arrayList, in an arrayList
    public static ArrayList<ItemSet> createCombinations(ArrayList<Integer> inputArr, int size){

        Integer[] temp = new Integer[size];
        //holds results
        ArrayList<ItemSet> results = new ArrayList<ItemSet>();

        genComRecursion(inputArr, 0, inputArr.size() - 1, temp, size, 0, results);

        return results;

    }

    private static void genComRecursion(ArrayList<Integer> container, int start, int end, Integer[] temp, int size, int tempIndex, ArrayList<ItemSet> results){

        if(tempIndex == size){

            results.add(new ItemSet(new ArrayList<>(Arrays.asList(temp))));
            return;

        }else{

            for(int i = start; i <= end; i++){

                temp[tempIndex] = container.get(i);

                genComRecursion(container, i + 1, end, temp, size, tempIndex + 1, results);
            }
        }
    }

}
