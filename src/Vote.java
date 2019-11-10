import java.util.ArrayList;
import java.util.List;
import java.util.Random;
// this class represent the preference for each candidates 
public class Vote {
	Random nm=new Random();
	static int id=0;
	 
	List <Character> preferences= new ArrayList<Character>();
	//constructor
	public Vote() {
		id++;
		//generate preferences
		this.generatePref(5,5);
		
	}
	public Vote(int nCandidate,Type type) {
		switch(type) {
		  case VoteOne:
		    // code block
		    break;
		  case VoteTwo:
		    // code block
		    break;
		  case AntiP:
			    // code block
			    break;
		  case Borda:
			  id++;
				//generate preferences
				this.generatePref(nCandidate,nCandidate);
			    break;
		}
		
	}
	//generate Pref fo voting for one 
	

	//generate preference depends on the number of candidates and the number of preference which can be indicate by electors
	public void generatePref(int numberOfCandidates,int nPref){
		for(int i=0;i<nPref;i++) {
			char vot=(char)(nm.nextInt(numberOfCandidates)+'a');
			while(this.isPresent(vot)==true)
				 vot=(char)(nm.nextInt(numberOfCandidates)+'a');
			
			preferences.add(vot);
		}
		//Uncomment if you want to see the pref
		//for(int i=preferences.size()-1;i>-1;i--) {
		//	System.out.println(preferences.get(i));
		//}
		
	}
	//check if there is another preference for a candidate
	public boolean isPresent(char pref) {
		for(int i=0;i<preferences.size();i++) {
			if(preferences.get(i)==pref)
				return true;	
		}
		 
			return false;
		
	}
	// return the index giving the character
	public Integer getChar(char a) {
		for(int i=0;i<preferences.size();i++) {
			if(preferences.get(i)==a)
				return i;	
		}
		return null;
	}
	public static void main(String [] args) {
		Vote a=new Vote();
		System.out.println("Nuove");
		Vote b=new Vote();
		
		
		
		
	}

}
