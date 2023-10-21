import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class SnakeGame {
  private static int speed=100;
	private static int grid[][],grid2[][];
	private static final int snakeValue=-1;
	private static boolean ended=false;
	private static int dx[]= {1,0,-1,0};
	private static int dy[]= {0,-1,0,1};
	public static int movement=0;
  private static List<Node> path=null;
  private static List<Node> path2=null;
	private static ArrayList<Point> snake;
	private static long startTime,endTime;
	private static DrawingBoard board= new DrawingBoard(50,50,528,528);
	private static Graphics2D g = (Graphics2D) board.getBufferedGraphics();
	public static Apple apple,apple2;
	private static boolean newPath,chase;
	private static int minScore=230;
	public static void main(String[] args) {
		while(true) {
		speed=100;
		grid=new int[15][15];
		grid2=new int[15][15];
		snake= new ArrayList<Point>(3);
		snake.add(new Point(3,7));
		grid[3][7]=snakeValue;
		snake.add(new Point(2,7));
		grid[2][7]=snakeValue;
		snake.add(new Point(1,7));
		grid[1][7]=snakeValue;
		apple=new Apple();
		while(grid[apple.x][apple.y]<0)apple=new Apple();
		newPath=true;
    chase=false;
    int size=3;
		while(!ended) {
			startTime=System.currentTimeMillis();
			if(Math.random()<0.0001) {
				if(size==snake.size())break;
				size=snake.size();
			}
			draw();
			if(newPath||chase) {
				pathTo(apple.x,apple.y);
			}
			if(path==null&&path2==null)break;
			else if(path!=null&&path.size()>1) {
				chase=false;
		        path.remove(0);
		        if(snake.get(0).x<path.get(0).x)movement=0;
		        else if(snake.get(0).x>path.get(0).x)movement=2;
		        else if(snake.get(0).y<path.get(0).y)movement=3;
		        else if(snake.get(0).y>path.get(0).y)movement=1;
		        newPath=false;
		        check();
			}
			if(chase&&path2.size()>1){
				path2.remove(0);
		        if(snake.get(0).x<path2.get(0).x)movement=0;
		        else if(snake.get(0).x>path2.get(0).x)movement=2;
		        else if(snake.get(0).y<path2.get(0).y)movement=3;
		        else if(snake.get(0).y>path2.get(0).y)movement=1;
		        check();
		     }
			if(snake.size()>200) {
				//speed=100;
			}
			endTime=System.currentTimeMillis();
			try {
				if(speed-endTime+startTime>0) Thread.sleep(speed-endTime+startTime);
			} catch (InterruptedException e) { }
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {}
		if(snake.size()>150)
			minScore=Math.min(minScore, snake.size());
			System.out.println(minScore+ " "+ snake.size());
		}
	}
	private static void draw() {
		board.clear();
		g.setColor(Color.BLACK);
		g.fillRect(100,100,328,328);
		apple.draw(g);
		drawSnake();
		board.repaint();
	}
	private static void drawSnake() {
		g.setColor(Color.WHITE);
		for(int i=1;i<snake.size();i++) {
			if(snake.get(i).x<snake.get(i-1).x)g.fillRect(100+22*snake.get(i).x,100+22*snake.get(i).y,22,20);
			else if(snake.get(i).x>snake.get(i-1).x)g.fillRect(100+22*snake.get(i).x-2,100+22*snake.get(i).y,22,20);
			else if(snake.get(i).y>snake.get(i-1).y)g.fillRect(100+22*snake.get(i).x,100+22*snake.get(i).y-2,20,22);
			else if(snake.get(i).y<snake.get(i-1).y)g.fillRect(100+22*snake.get(i).x,100+22*snake.get(i).y,20,22);
		}
		g.fillRect(100+22*snake.get(0).x,100+22*snake.get(0).y,20,20);
		g.setColor(Color.BLUE);
		g.fillOval(100+22*snake.get(0).x,100+22*snake.get(0).y,20,20);
		g.setColor(Color.YELLOW);
		g.fillOval(100+22*snake.get(snake.size()-1).x,100+22*snake.get(snake.size()-1).y,20,20);
	}
  private static void pathTo(int endx,int endy){
    for(int i = 0; i < grid.length; i++) grid2[i] = grid[i].clone();
	ArrayList<Point> al=new ArrayList<Point>(snake);
	AStar a =new AStar(grid2,al);
	path=a.findPathTo(endx,endy);
  }
  private static void pathToChase(int endx,int endy,boolean rev){
    for(int i = 0; i < grid.length; i++) grid2[i] = grid[i].clone();
		ArrayList<Point> al=new ArrayList<Point>(snake);
		AStar a =new AStar(grid2,al);
		path2=a.findPathTo(endx,endy,rev);
  }
  private static boolean check(){
	apple2=apple;
    ArrayList<Point> ala=new ArrayList<Point>(snake);
    int[][] grid3 = new int[grid.length][];
    for(int i = 0; i < grid.length; i++) grid3[i] = grid[i].clone();
    
	if(updateSnake()) pathToChase(snake.get(snake.size()-1).x,snake.get(snake.size()-1).y,false);
    if(path2==null){
    	apple=apple2;
        snake=new ArrayList<Point>(ala);
        for(int i = 0; i < grid3.length; i++) grid[i] = grid3[i].clone();
        chase=true;
        draw();
        pathToChase(snake.get(snake.size()-1).x,snake.get(snake.size()-1).y,false);
        if(path2!=null&&path2.size()>1) {
	        path2.remove(0);
	        if(snake.get(0).x<path2.get(0).x)movement=0;
	        else if(snake.get(0).x>path2.get(0).x)movement=2;
	        else if(snake.get(0).y<path2.get(0).y)movement=3;
	        else if(snake.get(0).y>path2.get(0).y)movement=1;
	        updateSnake();
        }
        if(path2==null) {
        	updateSnake();
        	chase=false;
        }
        return false;
    }
    return true;
  }
	private static boolean updateSnake() {
		if(snake.get(0).x+dx[movement]==apple.x&&snake.get(0).y+dy[movement]==apple.y) {
			snake.add(0, new Point(apple.x,apple.y));
			grid[snake.get(0).x][snake.get(0).y]=snakeValue;
			apple=new Apple();
			while(grid[apple.x][apple.y]<0)apple=new Apple();
			newPath=true;
			return true;
		}
		grid[snake.get(snake.size()-1).x][snake.get(snake.size()-1).y]=0;
		for(int i=snake.size()-1;i>0;i--) {
			snake.set(i,snake.get(i-1));
			grid[snake.get(i).x][snake.get(i).y]=snakeValue;
		}
		snake.set(0,new Point(snake.get(1).x+dx[movement],snake.get(1).y+dy[movement]));
    if(snake.get(0).x==-1||snake.get(0).x==15||snake.get(0).y==-1||snake.get(0).y==15) {
			ended=true;
			return false;
		}
		if(grid[snake.get(0).x][snake.get(0).y]<0) {
			ended=true;
			return false;
		}
    grid[snake.get(0).x][snake.get(0).y]=snakeValue;
    return true;
	}
}
