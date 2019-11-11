package com.buga.boxes.voters;

/**
 * IVoter is the interface which ALL voting schemes must obey.
 */
public interface IVoter {
    /**
     * Must define a function for vote, which returns a voting vector.
     * @return a voting vector.
     */
    public int[] vote();

    /**
     * Must define a getVotingResult for that voting scheme. Must take the entire voting array into account.
     * @param preferenceMatrix
     * @return the ranks of the candidates in preference matrix. For example, for candidates with voting scores [5, 10, 7] return [2, 0, 1]
     */
    public int[] getVotingResult(int[][] preferenceMatrix);

    /**
     * Must define a sayTheTruth function. This will return the voter's "true" preferences.
     * @return array of integers denoting true preferences.
     */
    public int[] sayTheTruth();

    /**
     * Must define a getHappiness function. This will take into consideration the final voting result, and return how happy the voter is with the result.
     * @param preferenceMatrix
     * @return happiness
     */
    public float getHappiness(final int[] preferenceMatrix);
}
