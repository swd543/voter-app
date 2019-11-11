package com.buga.boxes.voters;

public interface IVoter {
    public int[] vote();
    public int[] getVotingResult(int[][] preferenceMatrix);
    public int[] sayTheTruth();
    public float getHappiness(final int[] preferenceMatrix);
}
