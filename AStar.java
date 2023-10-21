import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class AStar {
    private final List<Node> open;
    private final List<Node> closed;
    private final List<Node> path;
    private Node now;
    private final int xstart;
    private final int ystart;
    private int xend, yend;
    public static int reversed=1;
    AStar(int[][] grid,ArrayList<Point> snake) {
        this.open = new ArrayList<>();
        this.closed = new ArrayList<>();
        this.path = new ArrayList<>();
        this.now = new Node(null,grid,snake, snake.get(0).x, snake.get(0).y, 0, 0);
        this.xstart = snake.get(0).x;
        this.ystart = snake.get(0).y;
    }
    public List<Node> findPathTo(int xend, int yend,boolean rev) {
    	if(rev)reversed=-1;
        this.xend = xend;
        this.yend = yend;
        this.closed.add(this.now);
        addNeigborsToOpenList();
	      while (this.now.x != this.xend || this.now.y != this.yend) {
	          if (this.open.isEmpty()) return null;
	          this.now = this.open.get(0); 
	          this.open.remove(0);
	          this.closed.add(this.now);
	          addNeigborsToOpenList();
	      }
        this.path.add(0, this.now);
        while (this.now.x != this.xstart || this.now.y != this.ystart) {
            this.now = this.now.parent;
            this.path.add(0, this.now);
        }
        return this.path;
    }
    
    public List<Node> findPathTo(int xend, int yend) {
        this.xend = xend;
        this.yend = yend;
        this.closed.add(this.now);
        addNeigborsToOpenList();
	      while (this.now.x != this.xend || this.now.y != this.yend) {
	          if (this.open.isEmpty()) return null;
	          this.now = this.open.get(0); 
	          this.open.remove(0);
	          this.closed.add(this.now);
	          addNeigborsToOpenList();
	      }
        this.path.add(0, this.now);
        while (this.now.x != this.xstart || this.now.y != this.ystart) {
            this.now = this.now.parent;
            this.path.add(0, this.now);
        }
        return this.path;
    }

    
    private static boolean findNeighborInList(List<Node> array, Node node) {
        return array.stream().anyMatch((n) -> (n.x == node.x && n.y == node.y));
    }
    
    
    
    private double distance(int dx, int dy) {
    	return Math.abs(this.now.x + dx - this.xend) + Math.abs(this.now.y + dy - this.yend);
    }
    private void addNeigborsToOpenList() {
        Node node;
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                if (x != 0 && y != 0) {
                    continue;
                }
                int[][] grid2=new int[now.grid.length][];
                for(int i = 0; i < now.grid.length; i++) grid2[i] = now.grid[i].clone();
                node = new Node(this.now,grid2,new ArrayList<Point>(this.now.snake), this.now.x + x, this.now.y + y, this.now.g, this.distance(x, y));
                if ((x != 0 || y != 0) 
                    && this.now.x + x >= 0 && this.now.x + x < this.now.grid[0].length
                    && this.now.y + y >= 0 && this.now.y + y < this.now.grid.length
                    && (node.grid[this.now.x + x][this.now.y + y] >=0)
                    && !findNeighborInList(this.open, node) && !findNeighborInList(this.closed, node)) { 
                		if(!node.updateSnake())continue;
                        node.g = node.parent.g + 1.;
                        this.open.add(node);
                }
            }
        }
        Collections.sort(this.open);
    }
}
