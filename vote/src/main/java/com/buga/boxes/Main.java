package com.buga.boxes;

import com.buga.boxes.voters.*;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        final var numberOfVoters=100;
        final var numberOfCandidates=5;

        var voters=new ArrayList<IVoter>();
        for(var i=0;i<numberOfVoters;i++){
            IVoter voter=new TwoVoter(numberOfCandidates);
            voters.add(voter);
        }

        // The choice voters make
        var choices=new int[numberOfVoters][numberOfCandidates];
        // The voter's actual preferences
        var preferences=new int[numberOfVoters][numberOfCandidates];

        for(var i=0;i<numberOfVoters;i++){
            var voter=voters.get(i);
            choices[i]=voter.vote();
            System.out.println(voter);
            preferences[i]=voter.sayTheTruth();
        }

        // Let the judges decide... ba dum tss!
        var result=new Voter(numberOfCandidates).getVotingResult(choices);

        System.out.println("Individual choices made are:"+Arrays.deepToString(choices));
        System.out.println("Non strategic voting outcome:"+Arrays.toString(result));

        for (var v:voters){
            System.out.println(Arrays.toString(v.sayTheTruth()) +" "+v.getHappiness(result));
        }
        var happiness=voters.stream().map(v->v.getHappiness(result)).reduce(Float::sum).get();
        System.out.println("Overall happiness "+happiness);
    }
}
