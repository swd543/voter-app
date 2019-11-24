import java.util.*;

public class Voting {
    static Scanner s = new Scanner(System.in);
	ArrayList<Character> outCome=new ArrayList<Character>();

    char[] candidates; //= {'a', 'b', 'c', 'd'};
    char[][] votingMatrix; //= {{'a', 'b', 'c', 'd'}, {'a','b', 'd', 'c', }, {'b', 'a', 'c', 'd'}};
    Voter[] voters; // = new Voter[votingMatrix.length];
    boolean[] hasStratOption;

    //Amount of points each candidate gets. Important for finding votingOutcome. Gets set in vote()
    int [] points; //= new int[candidates.length];

    //The order in which the candidates would be chosen. Important for calculating happiness. Gets set in vote()
    ArrayList<Character> votingOutcome = new ArrayList<Character>();

    //0 = plurality, 1 = voting for two, 2 = anti-plurality (veto), 3 = borda
    int votingStyle;

    // set voting style 
    public Voting(int votingStyle){
        this.votingStyle = votingStyle;

        candidates = new char[]{'a', 'b', 'c', 'd', 'e'};
        votingMatrix = new char[][]{{'d', 'e', 'a', 'b', 'c'}, {'d', 'e', 'c', 'b', 'a'}, {'a', 'e', 'd', 'b', 'c'}, {'e', 'a', 'b', 'c', 'd'}};

        voters = new Voter[votingMatrix.length];
        points = new int[candidates.length];
        for (int i = 0; i < votingMatrix.length; i++){
            voters[i] = new Voter(votingMatrix[i], votingStyle);
        }
        hasStratOption = new boolean[voters.length];
        for (int i = 0; i< hasStratOption.length; i++){
            hasStratOption[i] = false;
        }

    }

    public Voting(int votingStyle, char[][] votingMatrix, char[] candidates){
        this.votingStyle = votingStyle;
        this.votingMatrix = votingMatrix;
        this.candidates = candidates;
        voters = new Voter[votingMatrix.length];
        for (int i = 0; i < votingMatrix.length; i++){
            voters[i] = new Voter(votingMatrix[i], votingStyle);
        }
        points = new int[candidates.length];
        hasStratOption = new boolean[voters.length];
        for (int i = 0; i< hasStratOption.length; i++){
            hasStratOption[i] = false;
        }
    }

    //return the winner
    //Sets global variables like votingOutcome and points
    public char vote(){
    	//Map<Integer,Character> sortVote = new HashMap <Integer,Character>();
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
            
            //sortVote.put( currentPoints,candidates[i]);
        }

        //generates the outcome as a char array in the right order
        while(votingOutcome.size()<candidates.length && maxPoints>=0) {
            for (int i = 0; i < candidates.length; i++) {
                if (points[i] == maxPoints) {
                    votingOutcome.add(votingOutcome.size(), candidates[i]);
                }


            }
            maxPoints--;
        }

        // sortVote represent the point calculated for borda
        //for (Map.Entry<Integer,Character> entry : sortVote.entrySet()) {
        //	outCome.add(entry.getValue());
	        //System.out.println(entry.getKey() + " -> " + entry.getValue());
	        
	    //}


        
        return winner;
    }


    //Method that only handles strategic votes, and does not change any global variables.
    //Returns the voting outcome you would get with the changed preference.
    //Applies bulletvoting if int bulletVoter is between 0-n.
    public ArrayList<Character> strategicVote(int bulletVoter, int k){
        int[][] votes = new int[voters.length][votingMatrix[0].length];
        int[] tempPoints = new int[points.length];
        ArrayList<Character> tempVotingOutcome = new ArrayList<>();
        for (int i = 0; i < voters.length; i++){
            if (bulletVoter == i){
                votes[i] = voters[i].bulletVote(k);
            } else {
                votes[i] = voters[i].getVote();
            }
        }
        int maxPoints = 0;
        for (int i = 0; i <candidates.length; i++){
            int currentPoints = 0;
            for (int j = 0; j < voters.length; j++){
                currentPoints +=votes[j][i];
            }
            tempPoints[i] = currentPoints;
            if (currentPoints > maxPoints){
                maxPoints = currentPoints;
            }

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
    public double calcHappiness(ArrayList<Character> outcome, char[] pref){
        double distance = 0;
        for (int i = 0; i < outcome.size(); i++){
            distance += (i - outcome.indexOf(pref[i]))*(outcome.size()-i) ;
        }
        //System.out.print(distance +" hi ");
        return (1/(1+Math.abs(distance)));

    }

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


    //Now, for the strategic voting outcomes


    //Generate all strategic voting options for all voters
    public ArrayList<StrategicVotingOption> votingOptions(){
        ArrayList<StrategicVotingOption> votingOptions = new ArrayList<StrategicVotingOption>();
        for(int i = 0; i < voters.length; i++){
            ArrayList<StrategicVotingOption> smallList = strategicVotingOptions(i);
            for (StrategicVotingOption option : smallList) {
                votingOptions.add(option);
                hasStratOption[i] = true;
            }

        }
        return votingOptions;
    }

    //Method that generates (eventually) all strategic voting options for a voter
    public ArrayList<StrategicVotingOption> strategicVotingOptions(int i){
        double currentHap = calcHappins(votingOutcome, votingMatrix[i]);
        char[] currentPref = voters[i].getPref();

        char[] newPref;

        ArrayList<StrategicVotingOption> options = new ArrayList<>();

        for (int j = 0; j < candidates.length; j++){
            for (int k = j; k < candidates.length; k++) {
                //Compromise
                newPref = compromise(getNewPref(currentPref), j, k);
                voters[i].setPreferences(newPref);

                //Vote, see what the result is
                ArrayList<Character> tempOutcome = strategicVote(-1, -1);
                double tempHap = calcHappins(tempOutcome, currentPref);

                //If the resulting happiness is higher than original, save as strategic voting option
                if (tempHap > currentHap) {
                    StrategicVotingOption option = new StrategicVotingOption(i);
                    option.changedPreference = getNewPref(newPref);
                    option.votingOutcome = tempOutcome;
                    option.explanation = ("Because " + tempOutcome + " is prefered over " + votingOutcome + " which increased happiness by " + (tempHap - currentHap) + ", applying compromising.");
                    options.add(option);
                }
            }

        }
        //Bulletvoting
        if(!(votingStyle == 0)){
            //Try bulletvoting for all candidates but the last one

            for (int j = 0; j < voters[i].bulletVotes.length; j++){
                //Make bulletvote preference array.
                //Not used in code, only used to safe in Strategic Voting option
                char[] bulletVote = new char[candidates.length];
                bulletVote[0] = currentPref[j];
                for (int k = 1; k <currentPref.length; k++){
                    bulletVote[k] = '-';
                }
                voters[i].setPreferences(bulletVote);

                //Vote, see what the result is
                ArrayList<Character> tempOutcome = strategicVote(i, j);
                double tempHap = calcHappins(tempOutcome, currentPref);

                //If the resulting happiness is higher than original, save as strategic voting option
                if (tempHap > currentHap) {
                    StrategicVotingOption option = new StrategicVotingOption(i);
                    option.changedPreference = getNewPref(bulletVote);
                    option.votingOutcome = tempOutcome;
                    option.explanation = ("Because " + tempOutcome + " is prefered over " + votingOutcome + " with an increased happiness of " + (tempHap - currentHap) + ", applying bulletvoting." );
                    options.add(option);
                }
            }
        }

        //Reset preference of voter i to old preference
        voters[i].setPreferences(currentPref);




        return options;
    }

    public char[] compromise(char[] pref, int highPosition, int lowPosition){
        if(highPosition<lowPosition) {
            char temp = pref[lowPosition];
            pref[lowPosition] = pref[highPosition];
            pref[highPosition] = temp;
        }
        return pref;
    }


    //Method to copy char arrays
    public char[] getNewPref(char[] cur){
        char[] newPref = new char[cur.length];
        for (int i = 0; i< cur.length; i++){
            newPref[i] = cur[i];
        }
        return newPref;
    }

    public String printArray(char[] c){
        String s = "";
        for (char ch : c){
            s += " " + ch;
        }
        return s;
    }

    public double getRisk(){
        double stratOptions = 0;
        for (int i = 0; i < hasStratOption.length; i++){
            if (hasStratOption[i])
                stratOptions++;
        }
        return stratOptions/(double)voters.length;
    }
    

    public static void main(String args[]){
        Voting v = new Voting(3);                                   //Standard input

        //char[] candidates = askCandidates();                                 //Command-line
        //Voting v = new Voting(3, askVotingMatrix(candidates), candidates);   // input

        //char[] candidates = new char[]{'a', 'b', 'c', 'd', 'e'};
        //char[][] matrix = new char[][]{{'d', 'e', 'a', 'b', 'c'}, {'d', 'e', 'c', 'b', 'a'}, {'a', 'e', 'd', 'b', 'c'}, {'e', 'a', 'b', 'c', 'd'}};
        //Voting v = new Voting(3, matrix, candidates);                         //Own input

        System.out.println("Winner : "+v.vote());
        System.out.println(v.votingOutcome);

        double totalHap =0;
        for(char[] pref: v.votingMatrix) {
            double thisHapp = v.calcHappins(v.votingOutcome, pref);
            System.out.println(thisHapp);
            totalHap += thisHapp;
        }
        System.out.println("Total happiness is: " + totalHap);
        System.out.println();

        ArrayList<StrategicVotingOption> options = v.votingOptions();
        for (StrategicVotingOption option : options){
            System.out.println(option);
            System.out.println();
        }

        double risk = v.getRisk();
        System.out.println("Risk is: " + risk);

    }


    //Methods used to give input in the command line
    public static char[][] askVotingMatrix(char[] candidates){
        System.out.println("How many voters? ");
        int amount = s.nextInt();
        char[][] votingMatrix = new char[amount][candidates.length];
        for (int i = 0 ; i < amount; i ++){
            ArrayList<Character> list = new ArrayList<>();
            for (int j = 0; j < candidates.length; j++){
                char in;
                do {
                    System.out.println("Please give the number " + j + " preference, between " + candidates[0] + " and "+ candidates[candidates.length-1]);
                    in = s.next().charAt(0);
                } while ((in - 'a' <0 && in - 'a' >=candidates.length && !list.contains(in)));
                votingMatrix[i][j] = in;
                list.add(in);
            }
        }
        return votingMatrix;
    }


    public static char[] askCandidates(){
        System.out.println("How many candidates?");
        int amount = s.nextInt();
        char[] candidates = new char[amount];
        for (int i = 0; i < amount; i++){
            candidates[i] = (char) ('a' + i);
        }
        return candidates;
    }

}
