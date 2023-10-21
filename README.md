# snake_ai

Made back in 2019.

Uses A* algorithm to navigate snake to apple. Constantly checks if the snake will "close in" onto itself, trapping it and preventing it from escaping, by checking if it can nagivate to the tail. If it can't, it will take the longest possible route to follow the tail until it can safely collect the apple.  

Possible suggestions in future:
- you don't need astar, the search space is small enough to use BFS
- there is probably a faster way to do the chasing own tail... hamiltonian cycles maybe? 
