# connect-four
This is Evan Speciner's Project 3 for Matthew Henigman's ISE 340 Class: Summer 2020.

Things you may want to know about how the game works:
You are always Player 1, and your pieces are colored yellow.
The CPU is Player 2, and its pieces are colored yellow.

If the entire board gets filled up without either player winning, the game will determine that a tie occurred.
If the player is about to win the game, the CPU will notice and block your winning move sometimes.
Also, if the player is capable of connecting four on its turn, it will also attempt to do so sometimes.
The CPU doesn't use a minimax algorithm, it uses a for-loop to determine if either three of your or three of its pieces are in a row, and places a piece adjacent to either try to win or try to block your winning move.
Other than that, the CPU mostly performs moves randomly. If column 4, the center column, is empty, the CPU will place its first piece there, since this is the best opening move a player can make.

There is no implementation for replaying the game, so if you want to play again, you'll have to wait for the current game to end, and then run ConnectFour.jar again.
