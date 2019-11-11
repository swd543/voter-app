package com.buga.boxes.voters;

/**
 * IVoter is the interface which ALL voting schemes must obey.
 */
public interface IVoter {
    // Must define a function for vote, which returns a voting vector.
    public int[] vote();
    // Must define a getVotingResult for that voting scheme. Must take the entire voting array into account.
    public int[] getVotingResult(int[][] preferenceMatrix);
    // Must define a sayTheTruth function. This will return the voter's "true" preferences.
    public int[] sayTheTruth();
    // Must define a getHappiness function. This will take into consideration the final voting result, and return how happy the voter is with the result.
    public float getHappiness(final int[] preferenceMatrix);
}
