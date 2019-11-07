public class Voting {
    char[] candidates = {'a', 'b', 'c'};
    char[][] votingMatrix = {{'b', 'a', 'c'}, {'b', 'a', 'c'}, {'b', 'c', 'a'}};
    Voter[] voters = new Voter[votingMatrix.length];
    int votingStyle;

    public Voting(int votingStyle){
        this.votingStyle = votingStyle;
        for (int i = 0; i < votingMatrix.length; i++){
            voters[i] = new Voter(votingMatrix[i], votingStyle);
        }
    }

    public char vote(){
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
        }
        return winner;
    }

    public static void main(String args[]){
        Voting v = new Voting(3);
        System.out.println(v.vote());
    }

}
