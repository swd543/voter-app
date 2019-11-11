package com.buga.boxes.random;

import java.util.HashSet;
import java.util.Set;

public class Randomizer {
    public static int random(int max){
        return (int) ((Math.random()*Integer.MAX_VALUE)%max);
    }

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
