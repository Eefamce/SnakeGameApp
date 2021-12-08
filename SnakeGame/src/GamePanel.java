import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements ActionListener{

		static final int SCREEN_Width = 600;
		static final int SCREEN_Height = 600;
		static final int UNIT_Size = 25;
		static final int GAME_Units = (SCREEN_Width*SCREEN_Height)/UNIT_Size;
		static final int DELAY = 80;
		private static final int SCREEN_Heigh = 0;
		final int x[] = new int[GAME_Units];
		final int y[] = new int[GAME_Units];
		int bodyParts = 6;
		int applesEaten;
		int appleX;
		int appleY;
		char direction = 'R';
		boolean running = false; 
		Timer timer;
		Random random;
		
	
		GamePanel(){
			
			random = new Random();
			this.setPreferredSize(new Dimension(SCREEN_Width,SCREEN_Height));
			this.setBackground(Color.black);
			this.setFocusable(true);
			this.addKeyListener(new MyKeyAdapter());
			startGame();
			
		}
	
	public void startGame() {
		
		newApple();
		running = true;
		timer = new Timer(DELAY,this);
		timer.start();
		
	}
		
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		draw(g);
		
		
	}
	
	
	public void draw(Graphics g) {
		if(running) {
			g.setColor(Color.red);
			g.fillOval(appleX, appleY, UNIT_Size, UNIT_Size);
		
			for(int i = 0; i< bodyParts;i++) {
				if(i == 0) {
					g.setColor(Color.green);
					g.fillRect(x[i], y[i], UNIT_Size, UNIT_Size);
				}
				else {
					g.setColor(new Color(45,180,0));
					//g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
					g.fillRect(x[i], y[i], UNIT_Size, UNIT_Size);
				}			
			}
			g.setColor(Color.red);
			g.setFont( new Font("Ink Free",Font.BOLD, 40));
			FontMetrics metrics = getFontMetrics(g.getFont());
			g.drawString("Score: "+applesEaten, (SCREEN_Width - metrics.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize()); // !
		
		}
		else {
			gameOver(g);
		}
	}
	
	
	public void newApple() {
		
		appleX = random.nextInt((int)(SCREEN_Width/UNIT_Size))*UNIT_Size;
		appleY = random.nextInt((int)(SCREEN_Height/UNIT_Size))*UNIT_Size;
		
	}
	
	public void move() {
		
		for(int i = bodyParts;i>0;i--) {
			x[i] = x[i-1];
			y[i] = y[i-1];
		}
		
		switch(direction) {
		case 'U':
			y[0] = y[0] - UNIT_Size;
			break;
		case 'D':
			y[0] = y[0] + UNIT_Size;
			break;
		case 'L':
			x[0] = x[0] - UNIT_Size;
			break;
		case 'R':
			x[0] = x[0] + UNIT_Size;
			break;
		}
		
	}
	
	public void checkApple() {
		
		if((x[0] == appleX) && (y[0] == appleY)) {
			bodyParts++;
			applesEaten++;
			newApple();
		}
		
	}
	
	public void checkCollisions() {
		// check for head
		for(int i = bodyParts; i<0 ; i--) {
			if((x[0] == x[i]) && (y[0] == y[i])) {
				running = false;
			}
		}	
		// check for border - left
		if(x[0] <0) {
			running = false;
		}
		// check for border - right
		if(x[0] > SCREEN_Width) {
			running = false;
		}
		// check for border - down
		if(y[0] < 0) {
			running = false;
		}
		// check for border - up
		if(y[0] > SCREEN_Height) {
			running = false;
		}
		if(!running) {
			timer.stop();
		}
	}		
	
	
	public void gameOver(Graphics g) {
		
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free",Font.BOLD, 75));
		FontMetrics metrics = getFontMetrics(g.getFont());							//  !!!!!!!!!!!!!!!!!
		g.drawString("Game Over", (SCREEN_Width - metrics.stringWidth("Game Over"))/2, SCREEN_Height/2);
		
		g.setColor(Color.red);
		g.setFont( new Font("Ink Free",Font.BOLD, 40));
		FontMetrics metrics2 = getFontMetrics(g.getFont());
		g.drawString("Score: "+applesEaten, (SCREEN_Width - metrics2.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize());
		
	}
	@Override	
	public void actionPerformed(ActionEvent e) {
		
		if(running) {
			move();
			checkApple();
			checkCollisions();
		}
		repaint();
		
	}
		
	public class MyKeyAdapter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getExtendedKeyCode()) {
			case KeyEvent.VK_LEFT:
				if(direction != 'R') {
					direction = 'L';
				}
				break;
			case KeyEvent.VK_RIGHT:
				if(direction != 'L') {
					direction = 'R';
				}
				break;
			case KeyEvent.VK_UP:
				if(direction != 'D') {
					direction = 'U';
				}
				break;
			case KeyEvent.VK_DOWN:
				if(direction != 'U') {
					direction = 'D';
				}
				break;
			}	
		}
	}
		
}
