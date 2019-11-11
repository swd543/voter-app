package com.buga.boxes.voters;

import com.buga.boxes.random.Randomizer;

public class BordaVoter extends Voter implements IVoter {
    public BordaVoter(int numberOfChoices) {
        super(numberOfChoices);
    }

    @Override
    public int[] vote() {
        setVotingVector(Randomizer.fillRandomUnique(getNumberOfChoices()));
        return getVotingVector();
    }
}
