package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rajesh on 5/23/2017.
 * This class runs the APriori algorithm
 */
public class APriori {

    ArrayList<Basket> data;
    int support;

    public APriori(String data, int support){

        this.support = support;
        this.data = readFile(data);
    }

    //controls the Apriori running
    public void runApriori(int support){

        int k = 2;

        ArrayList<HashMap<String, ItemSet>> CX = new ArrayList<HashMap<String, ItemSet>>();
        ArrayList<HashMap<String, ItemSet>> LX = new ArrayList<HashMap<String, ItemSet>>();

        HashMap<String, ItemSet> currentC = firstCount(data);
        HashMap<String, ItemSet> currentL = firstFilter(currentC, support);
        HashMap<String, ItemSet> lastL = null;

        CX.add(currentL); // add in the first set of itemsets

        //create first table, c1 and l1
        currentC = firstCount(data);

        currentL = firstFilter(currentC, support);



        while(LX.get(LX.size() - 1).size() > 0){

            //create table of candidate itemsets -> arraylist of itemsets, size k
            currentC = AP_firstPass(data, k, lastL);

            currentL = AP_secondPass(currentC, support);
        }
    }

    public HashMap<String, ItemSet> firstFilter(HashMap<String, ItemSet> c1, int support){

        HashMap<String, ItemSet> L1 = new HashMap<String, ItemSet>();

        //filter out non freq items
        for(Map.Entry<String, ItemSet> item : c1.entrySet()){
            //add to new hashtable if count > support
            if(item.getValue().getCount() > support){
                L1.put(item.getKey(), item.getValue())
            }
        }

        return L1;
    }

    public HashMap<String, ItemSet> firstCount(ArrayList<Basket> data){
        HashMap<String, ItemSet> firstCount = new HashMap<String, ItemSet>();

        for(Basket b : data){
            for(Integer item : b.getBasket()){
                ItemSet singleton = new ItemSet(item);
                firstCount.put(singleton.getName(), singleton);
            }
        }
        return firstCount;
    }

    /*In the first pass, we create two tables. The first table, if necessary, translates
    item names into integers from 1 to n, as described in Section 6.2.2. The other
    table is an array of counts; the ith array element counts the occurrences of the
    item numbered i. Initially, the counts for all the items are 0.
    As we read baskets, we look at each item in the basket and translate its
    name into an integer. Next, we use that integer to index into the array of
    counts, and we add 1 to the integer found there.*/
    //essentially this just counts all size k combinations of possible items
    public ArrayList<ItemSet> AP_firstPass(ArrayList<Basket> data, int k, HashMap<String, ItemSet> lastL){
        //read each item, hash the item and increment count
        /*1. For each basket, look in the frequent-items table to see which of its items are frequent.
        2. In a double loop, generate all pairs of frequent items in that basket.
        3. For each such pair, add one to its count in the data structure used to store counts.*/
        HashMap<String, Integer> countHash = new HashMap<String, Integer>();
        for(Basket b : data){
            //create all combinations of frequent itemsets of size k using the previous L
            ArrayList<ItemSet> basketFreqCombinations = createCombinations(b, k, lastL);
            //add the result to the hashmap to count
        }
    }

    //takes a basket, a size of combinations and the last L,  and returns an array of
    // combinations of frequent itemsets with the size provided
    public ArrayList<ItemSet> createCombinations(Basket b, int size,  HashMap<String, ItemSet> lastL){
        //trying for a double loop first...
        // TODO: 5/25/2017 Fix this wrong doubleloop
        //get all frequent items
        ArrayList<ItemSet> allItemSetCombinations = generateCombinationsions(size);
        ArrayList<ItemSet> freqItemHolder = filterFrequentItems(b, lastL);



        //add to arrayList if this itemset is frequent
        for (Integer i : b.getBasket()){
            //create potential itemset of size - 1

        }
    }

    public ArrayList<ItemSet> filterFrequentItems(Basket b, HashMap<String, ItemSet> hash){
        ArrayList<Integer> freqItems = new ArrayList<Integer>();
        for(Integer item : b.getBasket()){
            if()
        }
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
    public ArrayList<ItemSet> AP_secondPass(ArrayList<ItemSet> C, int s){

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
