package com.buga.boxes.voters;

import com.buga.boxes.random.Randomizer;

public class OneVoter extends Voter implements IVoter {

    public OneVoter(int numberOfChoices) {
        super(numberOfChoices);
    }

    @Override
    public int[] vote() {
        var randomVote=Randomizer.random(getNumberOfChoices());
        getVotingVector()[randomVote]=1;
        return getVotingVector();
    }
}
