import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Voting {
	ArrayList<Character> outCome=new ArrayList<Character>();
    char[] candidates = {'a', 'b', 'c'};
    char[][] votingMatrix = {{'a', 'b', 'c'}, {'b', 'c', 'a'}, {'c', 'b', 'a'}};
    Voter[] voters = new Voter[votingMatrix.length];
    
    int votingStyle;

    // set voting style 
    public Voting(int votingStyle){
        this.votingStyle = votingStyle;
        for (int i = 0; i < votingMatrix.length; i++){
            voters[i] = new Voter(votingMatrix[i], votingStyle);
        }
    }

    //return the winner
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
                currentPoints +=votes[j][i];
            }
            if (currentPoints > maxPoints){
                winner = candidates[i];
                maxPoints = currentPoints;
            }
            System.out.println(currentPoints);
            
            sortVote.put( currentPoints,candidates[i]);
        }
        // sortVote represent the point calculated for borda
        for (Map.Entry<Integer,Character> entry : sortVote.entrySet()) {
        	outCome.add(entry.getValue());
	        System.out.println(entry.getKey() + " -> " + entry.getValue());
	        
	    }
        
        return winner;
    }
    // method to take the index giving the preferences 
    public Integer getChar(char a,ArrayList<Character> preferences) {
		for(int i=0;i<preferences.size();i++) {
			if(preferences.get(i)==a)
				return i;	
		}
		return null;
	}
    //calculate the happiness
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
    //this exchange position to make easier the calculation of happiness
    public ArrayList<Character> setPosPref(char[] input) {
    	ArrayList<Character> result=new ArrayList<Character>();
    	for(int i=input.length-1;i>-1;i--) {
    		result.add(input[i]);
    	}
    	return result;
    	
    }
    

    public static void main(String args[]){
        Voting v = new Voting(3);
        System.out.println("Winner"+v.vote());
        
        //ArrayList<Character> lol=v.setPosPref(v.votingMatrix[0]);
        //for(char a:lol)
        //System.out.println(a);
        for(char[] pref:v.votingMatrix) {
        	System.out.println(v.calcHappins(v.outCome, v.setPosPref(pref)));
        }
        	
    }

}
