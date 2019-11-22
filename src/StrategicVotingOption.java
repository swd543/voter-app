import java.util.ArrayList;

public class StrategicVotingOption {

    //Easiest way to make a tuple: just make a class.
    //The tuple from the instructions, on how we were to return a Strategic Voting Option

    int voter;
    char[] changedPreference;
    ArrayList<Character> votingOutcome;
    String explanation;

    public StrategicVotingOption(int i){
        voter = i;
    }

    @Override
    public String toString() {
        return ("Voter " + voter + " has the changed preference " + arrayChar() + " which leads to outcome: " +votingOutcome + ".\n"
        + explanation);
    }

    private String arrayChar(){
        String s = "{";
        for (char c : changedPreference){
            s += (c + " ");
        }
        s += "}";
        return s;
    }
}
