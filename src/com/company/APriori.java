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


        for(Map.Entry<String, ItemSet> i : currentL.entrySet()){
            System.out.println(i.getKey() + " " + i.getValue().getCount());
        }

        //lastL = currentL; //lastL is the last element is the array

        System.out.println("entering while loop!");
        while(/*lastL.size() > 0*/k < 4 ){

            lastL = LX.get(LX.size() - 1); // lastL is the last set of freq items found

            //start first pass starting with the C2 and L2
            currentC = AP_firstPass(data, k, lastL);



            for(Map.Entry<String, ItemSet> i : currentC.entrySet()){
                System.out.println(i.getKey() + " " + i.getValue().getCount());
            }

            currentL = AP_secondPass(currentC, support);


            LX.add(currentL);

            k++;
        }

        System.out.println("size is " + LX.size());

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
    public HashMap<String, ItemSet> AP_firstPass(ArrayList<Basket> data, int k, HashMap<String, ItemSet> lastL){
        //read each item, hash the item and increment count
        /*1. For each basket, look in the frequent-items table to see which of its items are frequent.
        2. In a double loop, generate all pairs of frequent items in that basket.
        3. For each such pair, add one to its count in the data structure used to store counts.*/
        HashMap<String, ItemSet> countHash = new HashMap<String, ItemSet>();



        /*
        * for each basket, generate all combinations size k of items.
        * for each of these size k combinations, generate all combinations of size k -1
        * for any size k combination, if its component combination (size k - 1) is not in lastL
        * then it is not frequent
        * */

        for(Basket b : data){

            //first get all frequent items by checking lastL
            ArrayList<ItemSet> freqItems = new ArrayList<ItemSet>();

            //create all combinations of size k from the basket
            ArrayList<ItemSet> allCombinationsK = b.generateCombinations(k);
            //for each combinations created, if this combination's parts are in Lk-1
            for (ItemSet isK : allCombinationsK){
                //create component combinations
                ArrayList<ItemSet> componentCombinations = Combinations.createCombinations(isK.getItemsArray(), k - 1);
                //now check if each component combination is in L(k - 1); i.e lastL
                for (ItemSet isK_minus1 : componentCombinations){
                    if( !lastL.containsKey(isK_minus1.getName())){
                        break; //if the component combination !in lastL, then its not frequent
                        //so dont add it into freqItems
                    }
                }
                //if it checks out that all component itemsets are in lastL, then add as a freq items
                freqItems.add(isK);
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



    //takes a basket, a size of combinations and the last L,  and returns an array of
    // combinations of frequent itemsets with the size provided
    public ArrayList<ItemSet> createCombinations(Basket b, int size,  HashMap<String, ItemSet> lastL){
        //trying for a double loop first...
        // TODO: 5/25/2017 Fix this wrong doubleloop
        //get all frequent items
        //ArrayList<ItemSet> allItemSetCombinations = generateCombinationsions(size);
        ArrayList<ItemSet> freqItemHolder = filterFrequentItems(b, lastL);



        //add to arrayList if this itemset is frequent
        for (Integer i : b.getBasket()){
            //create potential itemset of size - 1


        }

        return new ArrayList<ItemSet>();
    }

    public ArrayList<ItemSet> filterFrequentItems(Basket b, HashMap<String, ItemSet> hash){
        ArrayList<Integer> freqItems = new ArrayList<Integer>();
        for(Integer item : b.getBasket()){

        }
        return new ArrayList<ItemSet>();
    }

    /*
    * After the first pass, we examine the counts of the items to determine which of
    them are frequent as singletons. It might appear surprising that many singletons
    are not frequent. But remember that we set the threshold s sufficiently high
    that we do not get too many frequent sets; a typical s would be 1% of the
    baskets. If we think about our own visits to a supermarket, we surely buy
    certain things more than 1% of the time: perhaps milk, bread, Coke or Pepsi,
    and so on. We can even believe that 1% of the customers buy diapers, even
    though we may not do so. However, many of the items on the shelves are surely
    not bought by 1% of the customers: Creamy Caesar Salad Dressing for example.
    For the second pass of A-Priori, we create a new numbering from 1 to m for
    just the frequent items. This table is an array indexed 1 to n, and the entry
    for i is either 0, if item i is not frequent, or a unique integer in the range 1 to
    m if item i is frequent. We shall refer to this table as the frequent-items table.*/
    public void AP_betweenPass(){

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
