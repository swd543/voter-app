import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Voting {
	ArrayList<Character> outCome=new ArrayList<Character>();

    final char[] candidates = {'a', 'b', 'c', 'd'};
    final char[][] votingMatrix = {{'a', 'd','c', 'b'}, {'b', 'd', 'c', 'a'}, {'c', 'b','d', 'a'}};
    Voter[] voters = new Voter[votingMatrix.length];

    //Amount of points each candidate gets. Important for finding votingOutcome. Gets set in vote()
    int [] points = new int[candidates.length];

    //The order in which the candidates would be chosen. Important for calculating happiness. Gets set in vote()
    ArrayList<Character> votingOutcome = new ArrayList<Character>();

    //0 = plurality, 1 = voting for two, 2 = anti-plurality (veto), 3 = borda
    int votingStyle;

    // set voting style 
    public Voting(int votingStyle){
        this.votingStyle = votingStyle;
        for (int i = 0; i < votingMatrix.length; i++){
            voters[i] = new Voter(votingMatrix[i], votingStyle);
        }
    }

    //return the winner
    //Sets global varialbes like votingOutcome and points
    public char vote(){
    	Map<Integer,Character> sortVote = new HashMap <Integer,Character>();
        int[][] votes = new int[voters.length][votingMatrix[0].length];
        for (int i = 0; i < voters.length; i++){
            votes[i] = voters[i].getVote();
        }
        char winner = candidates[0];
        int maxPoints = 0;
        for (int i = 0; i <candidates.length; i++){
            int currentPoints = 0;
            for (int j = 0; j < voters.length; j++){
                currentPoints += votes[j][i];
            }
            points[i] = currentPoints;
            if (currentPoints > maxPoints){
                winner = candidates[i];
                maxPoints = currentPoints;
            }
            
            sortVote.put( currentPoints,candidates[i]);
        }


        while(votingOutcome.size()<candidates.length && maxPoints>=0) {
            for (int i = 0; i < candidates.length; i++) {
                if (points[i] == maxPoints) {
                    votingOutcome.add(votingOutcome.size(), candidates[i]);
                }


            }
            maxPoints--;
        }

        // sortVote represent the point calculated for borda
        for (Map.Entry<Integer,Character> entry : sortVote.entrySet()) {
        	outCome.add(entry.getValue());
	        //System.out.println(entry.getKey() + " -> " + entry.getValue());
	        
	    }


        
        return winner;
    }


    //Method that only handles strategic votes, and does not change any global variables.
    //Returns the voting outcome you would get with the changed preference.
    public ArrayList<Character> strategicVote(){
        //Map<Integer,Character> sortVote = new HashMap <Integer,Character>();
        int[][] votes = new int[voters.length][votingMatrix[0].length];
        int[] tempPoints = new int[points.length];
        ArrayList<Character> tempVotingOutcome = new ArrayList<>();


        for (int i = 0; i < voters.length; i++){
            votes[i] = voters[i].getVote();
        }
        char winner = candidates[0];
        int maxPoints = 0;
        for (int i = 0; i <candidates.length; i++){
            int currentPoints = 0;
            for (int j = 0; j < voters.length; j++){
                currentPoints +=votes[j][i];
            }
            tempPoints[i] = currentPoints;
            if (currentPoints > maxPoints){
                winner = candidates[i];
                maxPoints = currentPoints;
            }
            //System.out.println(currentPoints);


        }
        while(tempVotingOutcome.size()<candidates.length && maxPoints>=0) {
            for (int i = 0; i < candidates.length; i++) {
                if (tempPoints[i] == maxPoints) {
                    tempVotingOutcome.add(tempVotingOutcome.size(), candidates[i]);
                }
            }
            maxPoints--;
        }
        return tempVotingOutcome;



    }




    // method to take the index giving the preferences
    //Doubled, to be able to deal with Arraylists and char[]
    public Integer getChar(char a,ArrayList<Character> preferences) {
		for(int i=0;i<preferences.size();i++) {
			if(preferences.get(i)==a)
				return i;	
		}
		return null;
	}
    public Integer getChar(char a, char[] preferences) {
        for(int i=0;i<preferences.length;i++) {
            if(preferences[i]==a)
                return i;
        }
        return null;
    }


    //calculate the happiness
    //Doubled, to be able to deal with Arraylists and char[]
    public double calcHappins(ArrayList<Character> LAllpref,ArrayList<Character> pref) {
		int result=0;
		double retu;
		for(int i=0;i<LAllpref.size();i++) {
			//System.out.println(i-voto.getChar(LAllpref.get(i)));
			result+=(i-getChar(LAllpref.get(i),pref))*i;
		}
		
		retu=1/(1+(double)result);
		
		return retu;
	}
    public double calcHappins(ArrayList<Character> LAllpref,char[] pref) {
        int result=0;
        double retu;
        for(int i=0;i<LAllpref.size();i++) {
            //System.out.println(i-voto.getChar(LAllpref.get(i)));
            result+=(i-getChar(LAllpref.get(i),pref))*i;
        }

        retu=1/(1+(double)result);

        return retu;
    }

    //this exchange position to make easier the calculation of happiness
    // It reverses the preference list
    public ArrayList<Character> setPosPref(char[] input) {
    	ArrayList<Character> result=new ArrayList<Character>();
    	for(int i=input.length-1;i>-1;i--) {
    		result.add(input[i]);
    	}
    	return result;
    	
    }

    //Now, for the strategic voting outcomes


    //Generate all strategic voting options for all voters
    public ArrayList<StrategicVotingOption> votingOptions(){
        ArrayList<StrategicVotingOption> votingOptions = new ArrayList<StrategicVotingOption>();
        for(int i = 0; i < voters.length; i++){
            ArrayList<StrategicVotingOption> smallList = strategicVote(i);
            for (StrategicVotingOption option : smallList)
                votingOptions.add(option);

        }
        return votingOptions;
    }

    //Method that generates (eventually) all strategic voting options for a voter
    public ArrayList<StrategicVotingOption> strategicVote(int i){
        double currentHap = calcHappins(votingOutcome, votingMatrix[i]);
        char[] currentPref = voters[i].getPref();

        char[] newPref;

        ArrayList<StrategicVotingOption> options = new ArrayList<>();

        //Compromising (okay this might actually just be shuffeling preferences around, but still! It kinda works!
        for (int j = 0; j < candidates.length; j++){
            for (int k = 0; k < j; k++) {
                //Change preferences around
                newPref = getNewPref(currentPref);
                char temp = newPref[j];
                newPref[j] = newPref[k];
                newPref[k] = temp;
                voters[i].setPreferences(newPref);

                //Vote, see what the result is
                ArrayList<Character> tempOutcome = strategicVote();
                double tempHap = calcHappins(tempOutcome, currentPref);

                //If the resulting happiness is higher than original, save as strategic voting option
                if (tempHap > currentHap) {
                    StrategicVotingOption option = new StrategicVotingOption(i);
                    option.changedPreference = getNewPref(newPref);
                    option.votingOutcome = tempOutcome;
                    option.explanation = ("Because " + tempOutcome.get(0) + " is prefered over " + votingOutcome.get(0) + " with an increased happiness of " + (tempHap - currentHap));
                    options.add(option);
                }
            }
        }

        //Reset preference of voter i to old preference
        voters[i].setPreferences(currentPref);




        return options;
    }

    //Method to copy char arrays
    public char[] getNewPref(char[] cur){
        char[] newPref = new char[cur.length];
        for (int i = 0; i< cur.length; i++){
            newPref[i] = cur[i];
        }
        return newPref;
    }
    

    public static void main(String args[]){
        Voting v = new Voting(2);
        System.out.println("Winner : "+v.vote());
        
        //ArrayList<Character> lol=v.setPosPref(v.votingMatrix[0]);
        //for(char a:lol)
        //System.out.println(a);
        System.out.println(v.votingOutcome);
        /*for(char[] pref:v.votingMatrix) {
        	System.out.println(v.calcHappins(v.outCome, v.setPosPref(pref)));
        }*/
        for(char[] pref: v.votingMatrix) {
            System.out.println(v.calcHappins(v.votingOutcome, pref));
        }
        for (StrategicVotingOption option : v.votingOptions()){
            System.out.println(option);
        }


        	
    }

}
