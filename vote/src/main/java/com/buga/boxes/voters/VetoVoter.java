package com.buga.boxes.voters;

import com.buga.boxes.random.Randomizer;

import java.util.Arrays;

public class VetoVoter extends Voter implements IVoter {
    public VetoVoter(int numberOfChoices) {
        super(numberOfChoices);
    }

    @Override
    public int[] vote() {
        Arrays.fill(getVotingVector(), 1);
        var randomVote=Randomizer.random(getNumberOfChoices());
        getVotingVector()[randomVote]=0;
        return getVotingVector();
    }

    @Override
    public int[] getVotingResult(int[][] preferenceMatrix) {
        return new int[0];
    }
}
