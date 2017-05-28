package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rajesh on 5/23/2017.
 * This class runs the APriori algorithm
 */
public class APriori {

    ArrayList<Basket> data;

    public APriori(String data){

        this.data = readFile(data);
    }

    public APriori(ArrayList<Basket> basketArray){
        this.data = basketArray;
    }

    //controls the Apriori running
    public ArrayList<HashMap<String, ItemSet>> runApriori(int support){

        int k = 2;

        ArrayList<HashMap<String, ItemSet>> CX = new ArrayList<HashMap<String, ItemSet>>();
        ArrayList<HashMap<String, ItemSet>> LX = new ArrayList<HashMap<String, ItemSet>>();

        //create first table, c1 and L1
        HashMap<String, ItemSet> currentC = firstCount(data);
        HashMap<String, ItemSet> currentL = firstFilter(currentC, support);

        LX.add(currentL); // add in the first set of itemsets

        HashMap<String, ItemSet> lastL = LX.get(LX.size() - 1);

        System.out.println("entering while loop!");
        while(lastL.size() > 0){

            System.out.println("Counting for " + k);

            lastL = LX.get(LX.size() - 1); // lastL is the last set of freq items found

            //start first pass starting with the C2 and L2
            currentC = AP_firstPass(data, k, lastL, LX);

            currentL = AP_secondPass(currentC, support);

            LX.add(currentL);

            k++;
        }

        return LX;

    }

    /*In the first pass, we create two tables. The first table, if necessary, translates
    item names into integers from 1 to n, as described in Section 6.2.2. The other
    table is an array of counts; the ith array element counts the occurrences of the
    item numbered i. Initially, the counts for all the items are 0.
    As we read baskets, we look at each item in the basket and translate its
    name into an integer. Next, we use that integer to index into the array of
    counts, and we add 1 to the integer found there.*/
    //essentially this just counts all size k combinations of possible items
    public HashMap<String, ItemSet> AP_firstPass(ArrayList<Basket> data, int k, HashMap<String, ItemSet> lastL, ArrayList<HashMap<String, ItemSet>> LX){
        //read each item, hash the item and increment count
        /*1. For each basket, look in the frequent-items table to see which of its items are frequent.
        2. In a double loop, generate all pairs of frequent items in that basket.
        3. For each such pair, add one to its count in the data structure used to store counts.*/
        HashMap<String, ItemSet> countHash = new HashMap<String, ItemSet>();
        ArrayList<ItemSet> freqItems = new ArrayList<ItemSet>();



        /*
        * for each basket, generate all combinations size k of items.
        * for each of these size k combinations, generate all combinations of size k -1
        * for any size k combination, if its component combination (size k - 1) is not in lastL
        * then it is not frequent
        * */

        /*
        *
        * RATHER than do this, we should eliminate the costs of generating new items
        * that are not frequent items.
        * So, we should first filter out the non-frequent items using the Ls
        *
        * ALGORITHM
        * -------------------------
        * For each basket:
        *   filteredItems[] = filterFreqItems(basket, LX[])
        *   filteredCombinations[] = Combinations.createCombinations(filtereditems)
        *   for each potentialCombination in filteredCombinations:
        *       component combinations = Combinations.createCombinations(combination)
        *       if all component combinations in Lk-1:
        *           add to counthash
        *       else:
        *           don't add to counthash
        *
        *
        * How to filter?
        * -------------------------
        * for item in basket:
        *   for(int i = 0; i < count? ; i++):
        *       hashmap = LX.get(i);
        *       generate (size i + 1) combinations
        *       for each item:
        *           check if in hashmap
        *
        *
        *
        * */

        for(Basket b : data){

            ArrayList<Integer> filtereditems = filterFrequentItems(b, LX, k - 1);

            ArrayList<ItemSet> filteredCombinations = Combinations.createCombinations(filtereditems, k);

            for(ItemSet is : filteredCombinations){
                ArrayList<ItemSet> componentCombinations = Combinations.createCombinations(is.getItemsArray(), k -1);
                boolean canAdd = true;
                for (ItemSet iskMinus1 : componentCombinations){
                    canAdd = true;
                    if(!lastL.containsKey(iskMinus1.getName())){
                        canAdd = false;
                        break; //this item does not have all component combinations in Lk-1
                    }
                }
                if(canAdd == true){
                    freqItems.add(is);
                }
            }

            //now freq items contains all itemsets of size k that are frequent, so count them
            for (ItemSet is : freqItems){
                if(countHash.containsKey(is.getName())){
                    countHash.get(is.getName()).incrementCount();
                }else{
                    countHash.put(is.getName(), is);
                }
            }

        }

        return countHash;
    }

    //filters out non-frequent items using past Ls
    public ArrayList<Integer> filterFrequentItems(Basket b, ArrayList<HashMap<String, ItemSet>> LX, int level){

        ArrayList<Integer> filteredItems = new ArrayList<Integer>();

        //try for first level
        for(Integer i : b.getBasket()){
            if(LX.get(0).containsKey(new ItemSet(i).getName())){
                filteredItems.add(i);
            }
        }

        return filteredItems;

        // TODO: 5/28/2017 make this work filtering other levels
        /*for(int count = 0; count < level; count++){
            HashMap<String, ItemSet> hashMap = LX.get(count);
            ArrayList<ItemSet> combos = Combinations.createCombinations(b.getBasket(), count+1);
            for(ItemSet is : combos){
                ArrayList<ItemSet> combos = Combinations.createCombinations()
            }
        }*/

    }

    /*
    * During the second pass, we count all the pairs that consist of two frequent
    items. Recall from Section 6.2.3 that a pair cannot be frequent unless both its
    members are frequent. Thus, we miss no frequent pairs. The space required on
    the second pass is 2m2 bytes, rather than 2n
    2 bytes, if we use the triangularmatrix
    method for counting. Notice that the renumbering of just the frequent
    items is necessary if we are to use a triangular matrix of the right size. The
    complete set of main-memory structures used in the first and second passes is
    shown in Fig. 6.3.
    Also notice that the benefit of eliminating infrequent items is amplified; if
    only half the items are frequent we need one quarter of the space to count.
    Likewise, if we use the triples method, we need to count only those pairs of two
    frequent items that occur in at least one basket.
    The mechanics of the second pass are as follows.
    1. For each basket, look in the frequent-items table to see which of its items
    are frequent.
    2. In a double loop, generate all pairs of frequent items in that basket.
    3. For each such pair, add one to its count in the data structure used to
    store counts.
    Finally, at the end of the second pass, examine the structure of counts to
    determine which pairs are frequent.
    */
    public HashMap<String, ItemSet> AP_secondPass(HashMap<String, ItemSet> C, int s){

        HashMap<String, ItemSet> filteredCounts = new HashMap<String, ItemSet>();
        for (Map.Entry<String, ItemSet> is : C.entrySet()){
            if(is.getValue().getCount() > s){
                filteredCounts.put(is.getKey(), is.getValue());
            }
        }
        return filteredCounts;
    }

    public HashMap<String, ItemSet> firstFilter(HashMap<String, ItemSet> c1, int support){

        HashMap<String, ItemSet> L1 = new HashMap<String, ItemSet>();

        //filter out non freq items by hasing to another table
        for(Map.Entry<String, ItemSet> item : c1.entrySet()){
            //add to new hashtable if count > support
            if(item.getValue().getCount() > support){
                L1.put(item.getKey(), item.getValue());
            }
        }

        return L1;
    }

    public HashMap<String, ItemSet> firstCount(ArrayList<Basket> data){

        HashMap<String, ItemSet> firstCount = new HashMap<String, ItemSet>();

        for(Basket b : data){
            for(Integer item : b.getBasket()){
                ItemSet singleton = new ItemSet(item);
                if(firstCount.containsKey(singleton.getName())){
                    //increment counter for the itemset
                    firstCount.get(singleton.getName()).incrementCount();
                }else{
                    //else add it to the hashmap
                    firstCount.put(singleton.getName(), singleton);
                }
            }
        }
        return firstCount;
    }

    //read the data into memory, creating basket objects to represent them
    public ArrayList<Basket> readFile(String data){

        ArrayList<Basket> basketData = new ArrayList<Basket>();

        try{

            BufferedReader br = new BufferedReader( new FileReader(data) );

            String line;
            while((line = br.readLine()) != null){
                Basket b = new Basket(line);
                basketData.add(b);
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        return basketData;
    }



}
