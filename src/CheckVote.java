import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Collections;



public class CheckVote {
	int nOfElector;
	ArrayList<Vote> totalVote=new ArrayList<Vote>();
	
	//number of electors
	public CheckVote(int n, Type type,int nCandidate ) {
		this.nOfElector=n;	
		for(int i=0;i<n;i++) {
			System.out.println("new vote");
			totalVote.add(new Vote(nCandidate,type));
			
		}
	}
	// calculate the outcome of the election
	public Map<Character,Integer> OutComeBorda() {
		int pA=0;
		int pB=0;
		int pC=0;
		int pD=0;
		int pE=0;
		
		
		for(Vote a:totalVote) {
			for(int i=0;i<a.preferences.size();i++) {
				if(a.preferences.get(i)=='a') {
					pA+=i;
				}
				else if(a.preferences.get(i)=='b') {
					pB+=i;
				}
				else if(a.preferences.get(i)=='c') {
					pC+=i;
				}
				else if(a.preferences.get(i)=='d') {
					pD+=i;
				}
				else  {
					pE+=i;
				}
				
			}
			
		}
		
		//System.out.println(pA+" "+pB+" "+pC+" "+pD+" "+pE);
		//create data structure for the outcome of the election
		Map<Character,Integer> sortVote = new HashMap <Character,Integer>();
				
		sortVote.put( 'a',pA);
		System.out.println(pA);
		sortVote.put( 'b',pB);
		System.out.println(pB);
		sortVote.put( 'c',pC);
		System.out.println(pC);
		sortVote.put( 'd',pD);
		System.out.println(pD);
		sortVote.put( 'e',pE);
		System.out.println(pE);
		sortVote.entrySet().stream()
        .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))  
        .forEach(System.out::println);
		
		
		 
        // print the outcome
		 return sortVote;
	}
	
	// calculate happiness
	public double calcHappines(ArrayList<Character> LAllpref,Vote voto) {
		int result=0;
		double retu;
		for(int i=0;i<LAllpref.size();i++) {
			//System.out.println(i-voto.getChar(LAllpref.get(i)));
			result+=(i-voto.getChar(LAllpref.get(i)))*i;
		}
		
		retu=1/(1+(double)result);
		
		return retu;
	}
	
	public void Strategic(ArrayList<Character> lPrefer,Vote vote) {
		
		
	}
	public static void main(String [] args) {
		
		CheckVote election=new CheckVote(5,Type.Borda,5);
		//outcome
		Map<Character,Integer> a=election.OutComeBorda();
		
		//order map by value
		a.entrySet().stream()
        .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()));  
        //arrayList represent the vote after the order 
		ArrayList<Character> lPrefer=new ArrayList<Character>();
		for (Map.Entry<Character,Integer> entry : a.entrySet()) {
	        System.out.println(entry.getKey() + " -> " + entry.getValue());
	        lPrefer.add(entry.getKey());
	    }
		
		Map<Vote,Double> HappyeachVoter=new HashMap<Vote,Double>();
		//return happiness
		for(Vote vote:election.totalVote) {	
			HappyeachVoter.put(vote, election.calcHappines(lPrefer, vote));
			}
		
		}
		
	
		
		
	
	
	

}
