package Input;
import java.awt.event.*;
public class Mouse implements MouseListener,MouseMotionListener {

	private static int mouseX=-1;
	private static int mouseY=-1;
	private static int mouseB=-1;
	public static int getX() {return mouseX;}
	public static int getY() {return mouseY;}
	public static int getButton() {return mouseB;}
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		mouseX=e.getX();
		mouseY=e.getY();
	}

	
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		mouseX=e.getX();
		mouseY=e.getY();
		
	}

	
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		mouseB=e.getButton();
	}

	
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		mouseB=-1;
	}

	
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
