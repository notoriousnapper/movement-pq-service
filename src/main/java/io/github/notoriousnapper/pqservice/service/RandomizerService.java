package io.github.notoriousnapper.pqservice.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Data;
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

    // TODO: - save random seed for next time! and say no basically - for now
    // TODO:

    Map<String, List> itemMap = new HashMap<>();

    public RandomizerService() {
      itemMap.put("teas", Arrays.asList(teas));
    }





    // TODO: using past knowledgof choices & access
    // Needs an "acknowledge"
    public String getRandomItemFromArray(List itemList){
        return itemList.get(0).toString();
    }

    public String returnRandom(String listKey) {
        // Double check item is gettable?
        if (getItemMap().containsKey(listKey)){
            return getItemMap().get(listKey).get(0).toString();
        }
        else {
            return null;
        }
    }
}
