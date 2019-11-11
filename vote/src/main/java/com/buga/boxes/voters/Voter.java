package com.buga.boxes.voters;

import com.buga.boxes.random.Randomizer;

import java.util.Arrays;
import java.util.Comparator;

public class Voter {
    private int numberOfChoices;

    // Hey, psst, also tell me your actual preferences :)
    private int[] realPreferences;

    // Now whether the voter is truthful is upto the pseudorandomnumber generator to decide :)
    private int[] votingVector;

    @Override
    public String toString() {
        return "Hey, I'm voter "+hashCode()+" and my real preferences are " + Arrays.toString(realPreferences)+" but I chose "+Arrays.toString(votingVector);
    }

    public Voter(int numberOfChoices){
        this.numberOfChoices = numberOfChoices;
        this.realPreferences = Randomizer.fillRandomUnique(this.numberOfChoices);
        this.votingVector=new int[this.numberOfChoices];
    }

    public int getNumberOfChoices() { return numberOfChoices; }
    public int[] sayTheTruth() { return realPreferences; }
    public int[] getVotingVector() { return votingVector; }
    public void setVotingVector(int[] votingVector) { this.votingVector = votingVector; }

    static class RanksAndScores {
        int score;
        int index;

        RanksAndScores(int score, int index) {
            this.score = score;
            this.index = index;
        }
    }

    public int[] getVotingResult(int[][] preferenceMatrix) {
        var scores=new int[getNumberOfChoices()];
        for (var i = 0; i < preferenceMatrix.length; i++) {
            for (var j=0;j<preferenceMatrix[i].length;j++){
                scores[j]+=preferenceMatrix[i][j];
            }
        }
        var ranksAndScores=new RanksAndScores[scores.length];
        for(var i=0;i<scores.length;i++){
            ranksAndScores[i]= new RanksAndScores(scores[i], i);
        }
        // Return only the voting outcome ranks as an array
        return Arrays.stream(ranksAndScores)
                .sorted(Comparator.comparingInt(a -> -a.score))
                .mapToInt(r->r.index)
                .toArray();
    }

    public final float getHappiness(final int[] finalResults) {
        int distance=0;
        for(var i=0;i<getNumberOfChoices();i++){
            distance+=(finalResults[i]-realPreferences[i])*realPreferences[i];
        }

        return 1/(1+Math.abs((float) distance));
    }
}
