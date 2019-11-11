package com.buga.boxes.voters;

import org.junit.jupiter.api.Test;

import java.util.List;

public class TestVoter {

    static int numberOfCandidates=10;
    static int numberOfVoters=10000;

    @Test
    public void testB() {
        var voterTypes=List.of(
                new OneVoter(numberOfCandidates),
                new TwoVoter(numberOfCandidates),
                new VetoVoter(numberOfCandidates),
                new BordaVoter(numberOfCandidates));
        for (var voterType:voterTypes){
            voterType.vote();
            System.out.println(voterType);
        }
    }
}
