# Voter-app
An application for multi-agent voting, analysing strategic voting options and giving the risk.

## How to use
The program takes as input a matrix of preferences of different voters and the voting style chosen,
 and generates as output the true voting outcome, the total happiness of this outcome, the strategic voting options, and the 
risk of there being strategic voting. 

There are two ways to give lists of preferences for the voters: one is by changing the code, 
the other way is through the command line. 
### Changing preference with code 
The first constructor for the Voting class has as only input in the Main class which voting style 
should be used: 0 for plurality, 1 for voting for two, 2 for anti-plurality, and 3 for Borda. In this 
constructor, a two-dimensional array of characters specifies the preference matrix. This matrix can be 
adjusted as necessary. The characters do have to be lowercase letters, in the range of 'a' to 'a' + n-1, 
with n being the amount of candidates. These candidates also have to be specified in this constructor. 

### Changing preference through command line
Standard setting on this program is to construct a Voting object from the specifications as in the code,
but if the user want to put in the preferences manually that is possible. To do this, comment out the 
first line of the Main class, and comment in the second and third line of the Main class. Here you can
 also still input which voting style you want. Then there will be prompts in the command line 
 asking you how many candidates and voters there are, and what their preferences are. 
 You will only be able to input valid answers to the preferences. 

## How it works
The Voting class makes an array of voters, who are able to give their vote in the form of an array 
of integers. How this array looks is determined by the voting style. The Voting class then counts 
all points for all candidates, and the candidate with the highest amount of points wins the election 
(with alphabetic order in case of ties). The Voting class also remembers the true outcome of this 
election. 

Then, when the strategic voting options are asked for, the program assigns each voter one by one
new temporary preferences (either through compromising/burying or bullet-voting) , performs a 
'mock' election to see what the outcome of the election would be with these preferences. It then 
checks if this simulated outcome would lead to higher happiness for the current voter than the true 
outcome, and if it would, it saves the strategic voting option. 

When all strategic voting options for all voters have been determined, risk gets calculated by 
determining how many voters had strategic voting options, and dividing that number by the total 
amount of voters. 










