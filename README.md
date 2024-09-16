# Othello

## Motivation
Othello, sometimes referred to as Reversi, is a two player board game where the goal of each player is two maximize their number of pieces on the board an minimize their opponents. However, unlike many other classical games like chess and go, the structure of the game makes constant shifts in determining who the winner is going to be as a single move could turn upto 56 disks. On an 8x8 board, that is enough to turn the game around. Given the computational complexity of the game and the uncertainty in what constitutes a good position it is usefull to adapt the minimax algorithm to consider depth and a heuristic evaluation of the state given what we know leads to beter circumstances. More detail on these is provided below. <br /> 
This program was created as the final project for CPSC 210 in the summer of 2024. The course is about software design and thus encourages extensive testing which has been done by Junit for the model.

## Algorithm
The design of the algorihtm's structure for this project was taken from <a href="https://people.engr.tamu.edu/guni/csce421/files/AI_Russell_Norvig.pdf"> Artificial Intelligence: A Modern Approach</a>. The adaptation for considering heuristic evaluations described below is discussed in <a href="https://courses.cs.washington.edu/courses/cse573/04au/Project/mini1/RUSSIA/Final_Paper.pdf"> An analysis of Heuristics in Othello</a>. <br />
The heuristic evaluation for deciding the value of the state is a linear combination of disk parity, mobility, stability and status. The weights are set thus:
$$  Heval = 1*parity + 3*mobility + 3*stability + 100*winner $$
Below is the definition of each term.
- Parity is the difference in number of pieces on the board.
- Mobility is the difference in number of available moves on the boar.
- Stability is the difference of addition of numbers on the following board matrix associated to disks on the board. As side and corner pieces are less likely to be flipped. Acquirig pieces there is of higher value.
- Lastly winner is 1 if the bot has won, 0 if it is a tie or game isn't over and -1 if the player has won. (It is assumed that the bot is the maximizing player)


