package io.github.notoriousnapper.pqservice.commandlinerunner;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import lombok.Data;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class LeetCodeCommandLineRunner implements CommandLineRunner {

  @Data
  class Edge {

    public Character k;
    public Character v;
    public Integer weight;
    Edge(Character k, Character v, Integer weight){
      this.k = k;
      this.v = v;
      this.weight = weight;

    }

  }

  class PairComparator implements Comparator<Pair>{
    @Override
    public int compare(Pair o1, Pair o2) {
      return o1.weight - o2.weight;
    }
  }
  @Data
  class Pair {
    Character k;
    Integer weight;

    Pair(Character k, Integer weight){
      this.k = k;
      this.weight = weight;
    }
    public Integer getWeight(){
      return this.weight;
    }
  }

  @Override
  public void run(String... args) throws Exception {
    runDjikstraAlgorithm();

    //  default initial capacity (16) and load factor (0.75), and default is insertionOrder - removing oldest
    // What is default for KeyToRemove?
    // removeEldestEntry() default tis false, so like normal map it doesnt' evict*


    LinkedHashMap<Integer, String> lhm = new LinkedHashMap<>();
    lhm.put(1,"Jesse");
    lhm.put(2,"Ren");
    lhm.put(3,"Someone");
    lhm.put(5,"Else");

    Iterator<Integer> it = lhm.keySet().iterator();
    int keyToRemove = 0;
    while (it.hasNext()){
      Integer key = it.next();
      System.out.println("values of linkedHashMap: " + lhm.get(key));
    }
//        lhm.remove(keyToRemove);

    it = lhm.keySet().iterator();
//    lhm.removeEldestEntry()
    while (it.hasNext()) {
      Integer key = it.next();
      System.out.println("values of linkedHashMap: " + lhm.get(key));
    }

    Map<Integer, Integer> map = new HashMap(){
      @Override
      public String toString(){
        return "A";
      }
    };
    System.out.println("Overridden " + map);


    // putIfAbsent (saves you the contains if check)
    // getOrDefault (saves you null)



  }




  private void runDjikstraAlgorithm() {
    List<Edge> edges = Arrays.asList(
        new Edge('A','B',2),
        new Edge('D','B',1),
        new Edge('D','C',2),
        new Edge('B','C',4),
        new Edge('E','C',1),
        new Edge('C','F',3),
        new Edge('E','G',5),
        new Edge('C','G',7),
        new Edge('G','F',2)
    );

    Map<Character, Map<Character, Integer>> map = new HashMap();
    Set<Character> visited = new HashSet();

    // Create graph representation for use
    // O(E)
    for(int i = 0; i < edges.size(); i++){
      char k = edges.get(i).k;
      char v = edges.get(i).v;
      int weight = edges.get(i).weight;

      // O(1)
      if (!map.containsKey(k)){
        Map<Character, Integer> adjMap = new HashMap();
        adjMap.put(v, weight);
        map.put(k, adjMap);
      } else {
        map.get(k).put(v, weight);
      }

      // O(1)
      if (!map.containsKey(v)){
        Map<Character, Integer> adjMap = new HashMap();
        adjMap.put(k, weight);
        map.put(v, adjMap);
      } else {
        map.get(v).put(k, weight);
      }
    }
    char target = 'F';

//    PriorityQueue<Pair> pq = new PriorityQueue(edges.size(), new PairComparator());
    PriorityQueue<Pair> pq = new PriorityQueue<>(edges.size(), (a, b)->{
      return a.getWeight() - b.getWeight() ;
    });

//    PriorityQueue<Pair> pq = new PriorityQueue<>(edges.size(),
//        Comparator.comparingInt(Pair::getWeight));
    // Binary Tree recursive *search* - walk through that process * ( bc choosing division)

    pq.add(new Pair(edges.get(0).k, 0));
    while (!pq.isEmpty()){
      Pair p = pq.poll();
      System.out.println("Vertex visited: " + p.toString());
      int total = p.weight;
      if (p.k == target){
        System.out.println("Found target, best path is " + p.weight + map.get(p.k).get(target));
        break;
      }
      if (visited.contains(p.k)){
        continue;
      } else {
        visited.add(p.k);
        // explore neighbors
        for(char vertex : map.get(p.k).keySet()){
          int weight = map.get(p.k).get(vertex) + total;
          pq.add(new Pair(vertex, weight));
        }
      }
    }
    System.out.println("End algorithm");
  }
}















