import java.util.ArrayList;
import java.util.Random;

public class Voter {
    Random r = new Random();
    private char[] preferences;
    int[][] bulletVotes;

    //0 = plurality, 1 = voting for 2, 2 = anti-plurality (veto), 3 = borda
    private int votingStyle;

    // set voter preference and voting style
    public Voter(char[] preferences, int votingStyle){
        this.preferences = preferences;
        this.votingStyle = votingStyle;
        initBulletVote();
    }

    // constructor for random preferences
    public Voter(int candidates, int votingStyle){
        this.votingStyle = votingStyle;
        randomizeCandicates(candidates);
        initBulletVote();
    }

    //create 
    private void randomizeCandicates(int candidates){
        ArrayList<Character> allCandidates = new ArrayList<>();
        for (int i = 0; i<candidates; i++){
            char candidate = (char) ('a'+i);
            allCandidates.add(candidate);
        }
        preferences = new char[candidates];
        for (int i = 0; i < candidates; i++){
            int random = r.nextInt(allCandidates.size());
            preferences[i] = allCandidates.get(random);
            allCandidates.remove(random);
        }
    }

    // returns int array with amount of points for each candidate.
    public int[] getVote(){
        switch (votingStyle){
            case 0 : return plurality();
            case 1 : return for2();
            case 2 : return anti();
        }
        return borda();
    }

    // returns one 1, all zero
    private int[] plurality(){
        char choice = preferences[0];
        int[] vote = new int[preferences.length];
        for (char i = 'a'; i < ('a'+preferences.length); i++){
            if (i == choice){
                vote[i-'a']=1;
            }
            else{
                vote[i-'a']=0;
            }
            //System.out.print(vote[i - 'a']);
        }
        //System.out.println();
        return vote;
    }

    // returns two 1, rest zero
    private int[] for2(){
        char choice1 = preferences[0];
        char choice2 = preferences[1];
        int[] vote = new int[preferences.length];
        for (char i = 'a'; i < ('a'+preferences.length); i++){
            if (i == choice1 || i == choice2){
                vote[i-'a']=1;
            }
            else{
                vote[i-'a']=0;
            }
        }
        return vote;
    }

    // returns one 0, rest 1
    private int[] anti(){
        char notChoice = preferences[preferences.length-1];
        int[] vote = new int[preferences.length];
        for (char i = 'a'; i < ('a'+preferences.length); i++){
            if (i == notChoice){
                vote[i-'a']=0;
            }
            else{
                vote[i-'a']=1;
            }
        }
        return vote;
    }

    // returns values from n-1 till 0
    private int[] borda(){
        int[] vote = new int[preferences.length];
        for (char i = 'a'; i < ('a'+preferences.length); i++){
            int value = 0;
            for (int j = 0; j < preferences.length; j++){
                if(preferences[j]==i){
                    value = preferences.length - 1 - j;
                }
            }
            vote[i - 'a'] = value;
        }
        return vote;
    }


    //setters and getters for preferences.
    public char[] getPref() {
        return preferences;
    }

    public void setPreferences(char[] preferences) {
        this.preferences = preferences;
    }

    //Returns the appropriate outcome applying bulletvoting
    public void initBulletVote() {
        if (votingStyle > 0) {
            int [][] votes;
            if (votingStyle == 1){
                votes = new int[2][preferences.length];
            } else {
                votes = new int[preferences.length - 1][preferences.length];
            }
            int[] vote = anti();
            for (int i = 0; i < votes.length; i++) {
                for (int j = 0; j < votes[0].length; j++) {
                    votes[i][j] = 0;
                }
            }
            int times = 1;
            if (votingStyle > 2) {
                times = preferences.length - 1;
            }
            int minus = 0;
            int count = 0;
            for (int i = 0; (i < vote.length && count < 2); i++) {
                if (vote[i] == 1) {
                    votes[i - minus][i] = times;
                    count++;
                } else {
                    minus = minus + 1;
                }

            }
            bulletVotes = votes;
        }
    }

    public int[] bulletVote(int k) {
        return bulletVotes[k];
    }
}
