package com.buga.boxes.random;

import java.util.HashSet;
import java.util.Set;

public class Randomizer {
    /**
     * Get a random int.
     * @param max
     * @return a random int from 0 to max with equal probability.
     */
    public static int random(int max){
        return (int) ((Math.random()*Integer.MAX_VALUE)%max);
    }

    /**
     * Get a random array of size [max]
     * @param max
     * @return array of size [max] with unique random values from 0 to max.
     */
    public static int[] fillRandomUnique(int max){
        Set<Integer> alreadyExistingChoices=new HashSet<>();
        var a=new int[max];
        for(var i=0;i<max;){
            var random= Randomizer.random(max);
            if(alreadyExistingChoices.contains(random)) continue;
            alreadyExistingChoices.add(random);
            a[random]=i++;
        }
        return a;
    }
}
