package com.buga.boxes.voters;

import com.buga.boxes.random.Randomizer;

public class TwoVoter extends Voter implements IVoter {

    public TwoVoter(int numberOfChoices) {
        super(numberOfChoices);
    }

    @Override
    public int[] vote() {
        for(var i=0;i<2;){
            var randomVote= Randomizer.random(getNumberOfChoices());
            if(getVotingVector()[randomVote]==1) continue;
            getVotingVector()[randomVote]=1;
            i++;
        }
        return getVotingVector();
    }
}
