import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class TumblingDuke extends JFrame {
	private static final int TIMER_PAUSE  = 1500; // the length of the pause between loops
	private static final int TIMER_SPEED  =  100; // animation speed
	private static final int TOTAL_IMAGES =   16; // number of images to animate

	private Image    images[];     // the images
	private Animator animator;     // the drawing panel
	private Timer    timer; 	   // the timer animating the images	
	private int      index;     // the current frame number
	private boolean  forward;

	public TumblingDuke() {
		images  = new Image[ TOTAL_IMAGES ];
		index   = 0;
		forward = true;

		for (int i = 0; i < images.length; i++) {
			URL url   = getClass().getResource( "T"+(i+1)+".gif" );
			images[i] = new ImageIcon( url ).getImage();
		}
		
		add( animator = new Animator() );
		
		timer = new Timer    ( TIMER_SPEED, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean reset;
				if (forward) reset = ++index == TOTAL_IMAGES;
				else  		 reset = --index == -1;

				if (reset) {
					forward = !forward;
					index   =  forward ? 0 : images.length-1;
					timer.restart();
				}
				animator.repaint();
			}
		});
		timer.setInitialDelay( TIMER_PAUSE );
		timer.start();
		
		setSize( 130, 110 );
		setLocationRelativeTo( null );
		setDefaultCloseOperation( DISPOSE_ON_CLOSE );
	}
	private class Animator extends JPanel {
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage( images[ index ], 0, 0, this );
		}
	}
	
	public static void main(String[] args) {
		JFrame f = new TumblingDuke();
		f.setVisible( true );
	}
}
