package io.github.notoriousnapper.pqservice.service;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import lombok.Data;
import lombok.NonNull;
import org.springframework.stereotype.Component;

/*
 * Goal is to provide feature to randomize list items to remove burden
 * of choosing and thinking.
 *
 * Things to randomize:
 * items
 * moves
 */
@Component
@Data
public class RandomizerService {

//  @NonNull
  Random random;
//  @NonNull
  Map<String, LinkedHashSet> keyToLRUMap = new HashMap<>();

    String[] teas = {
        "chamomile",
        "peppermint tea",
        "ginger",
        "hibiscus",
        "chrysanthemum",
        "mint",
        "rooibos"
    };

    // Low head
    String[] artActivity = {
        "play piano",
        "play piano",
        "play piano",
    };


    Map<String, List> itemMap = new HashMap<>();
//    public RandomizerService() {
//      itemMap.put("teas", Arrays.asList(teas));
//    }

    public <T> T getRandomItem(List<T> items){
        return items.get(random.nextInt(items.size()));
    }

    /*
     * Returns a random item given a list.  If the list has been used before, service will
     * check within existing LRU, otherwise it will create a new one.
     * If item generated exists in the LRU, it will keep generating a random one element until
     * unused element is selected.
     *
     * @param listKey is key of type String that is used to fetch or create corresponding LRU
     * @param
     * @param
     *
     */

    public <T> T getRandomItem(String listKey, List<T> items, Class clazz) {

      if (keyToLRUMap.containsKey(listKey)){
        LinkedHashSet linkedHashset = keyToLRUMap.get(listKey);
        int randomIndex = random.nextInt(items.size());
        while(linkedHashset.contains(randomIndex)) {
            randomIndex = random.nextInt(items.size());
        }
        return items.get(randomIndex);

      } else {
        LinkedHashSet linkedHashset = new LinkedHashSet();
      }
      return null;
    }
    public String getRandomItem(String listKey) {
      return null;
    }
}
