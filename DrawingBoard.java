
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class DrawingBoard extends JPanel implements KeyListener{

	private JFrame frame;
	private BufferedImage bImage, tImage;
	private Graphics bufferG, transG;
	
	public DrawingBoard(int x, int y, int w, int h){
		frame = new JFrame();
		frame.setTitle("SNAKE");
		frame.setBounds(x, y, w, h);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setPreferredSize(new Dimension(w, h));
		frame.getContentPane().add(this);
		frame.pack();
		frame.setVisible(true);
		
		bImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		bufferG = bImage.getGraphics(); // any thread can access this.
	
		tImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		transG = tImage.getGraphics(); // any thread can access this.
		
		((Graphics2D)bufferG).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		((Graphics2D)transG).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	}
	
	
	public Graphics getBufferedGraphics(){ return bufferG; }
	public Graphics getTransGraphics(){ return transG; }
	
	
	public void clear(){ // clear the bImage
		int i, j;
		for(i=0; i<this.getWidth(); i++){
			for(j=0; j<this.getHeight(); j++){
				bImage.setRGB(i, j, 0xffffffff);
			} 
		}
	}
	
	public void clearTrans(){ // clear the bImage
		int i, j;
		for(i=0; i<this.getWidth(); i++){
			for(j=0; j<this.getHeight(); j++){
				tImage.setRGB(i, j, 0x00000000);
			} 
		}
	}
	
	public void paintComponent(Graphics g){
		g.drawImage(bImage, 0, 0, this);
		g.drawImage(tImage, 0, 0, this);
	}


	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void keyPressed(KeyEvent e) {
		
	}


	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}









