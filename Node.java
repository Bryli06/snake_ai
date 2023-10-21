import java.util.ArrayList;

public class Node implements Comparable{  
	public Node parent;
	public int[][] grid;
	public ArrayList<Point> snake;
    public int x, y;
    public double g;
    public double h;
    Node(Node parent,int[][] grid,ArrayList snake, int xpos, int ypos, double g, double h) {
        this.parent = parent;
        this.grid=grid;
        this.snake=snake;
        this.x = xpos;
        this.y = ypos;
        this.g = g;
        this.h = h;
   }
   // Compare by f value (g + h)
   public boolean updateSnake() {
	   if(x==SnakeGame.apple.x&&y==SnakeGame.apple.y) {
			snake.add(0, new Point(SnakeGame.apple.x,SnakeGame.apple.y));
			grid[snake.get(0).x][snake.get(0).y]=-1;
			return true;
		}
		grid[snake.get(snake.size()-1).x][snake.get(snake.size()-1).y]=0;
		for(int i=snake.size()-1;i>0;i--) {
			snake.set(i,snake.get(i-1));
			grid[snake.get(i).x][snake.get(i).y]=-1;
		}
		snake.set(0,new Point(x,y));
		boolean ans=grid[x][y]==0;
		grid[x][y]=-1;
		return ans;
	}
   public int compareTo(Object o) {
       Node that = (Node) o;
       return AStar.reversed*(int)((this.g + this.h) - (that.g + that.h));
   }
}