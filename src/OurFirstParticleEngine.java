import acm.program.*;
import acm.graphics.*;
import java.awt.*;
import acm.util.*;

public class OurFirstParticleEngine extends GraphicsProgram {
	private static final int BOARD_HEIGHT=960;
	private static final int BOARD_WIDTH=1080; 
	private static final int PARTICLE_COUNT=1000;
	//private static final int ANIMATION_DELAY_MS=50;
	private static final float FPS=20;
	
	public static RandomGenerator rGen = new RandomGenerator();
	

	private class Particle extends GOval {
		
		private static final double RADIUS=10;
		private static final double LIFE_SPAN_SEC=80; //life span in seconds
		private static final double REGENERATE_SEC = 10; // delay time before particle is regenerated.
		
		private boolean isAlive=false;
		private double lifeSpan=0;
		private double timeDead=0;
		private double velocityX;  // speed in x as pixels/s
		private double velocityY;  // speed in y as pixels/s
		private double maxVelocity = 5;  // pixels/sec
		private double minVelocity = -5; //pixels/sec
		
		public Particle(){
			this(RADIUS,RADIUS);	
		}
		
		public Particle(double arg0, double arg1) {
			super(arg0, arg1);
			isAlive = false;
			setVisible(false);
			//add(this);
			// TODO Auto-generated constructor stub
		}
		
		
		// over loading setColor method
		public void setColor(Color c){
			super.setFillColor(c);
			super.setColor(c);
			super.setFilled(true);
		}
		
		
		public void bringToLife(){
			lifeSpan = LIFE_SPAN_SEC+ rGen.nextDouble(-30, 0);
			isAlive = true;
			setVisible(true);
			this.setColor(rGen.nextColor());
			velocityX = rGen.nextDouble(minVelocity,maxVelocity)/FPS;  // convert to pixels/frame
			velocityY = rGen.nextDouble(minVelocity,maxVelocity)/FPS; // convert to pixels/frame
			double xOffset = (rGen.nextDouble()-1)*25;
			double yOffset = (rGen.nextDouble()-1)*25;
			setLocation(BOARD_WIDTH/2+xOffset,BOARD_HEIGHT/2+yOffset);
		}		

		public void update(){
			if(isAlive){
				lifeSpan-=1/FPS;
				move(velocityX,velocityY);
				if(lifeSpan<=0){
					die();	
				}
			}
			else{
				timeDead+=1/FPS;
				if(timeDead>REGENERATE_SEC){
					bringToLife();
				}
			}
		}
		
		public void die(){
			isAlive = false;
			setVisible(false);
		}
	}	
	
	private Particle particles[];
	
	public void run() {
		particles = new Particle[PARTICLE_COUNT];
		
		setTitle("An animation");
		setupBoard();		
		
		float animationDelay = 100/FPS;
		while(true){
			updateBoard();
			pause(animationDelay);
		}
	}
	
	private void setupBoard(){
		setSize(BOARD_WIDTH, BOARD_HEIGHT);
		GRect rect = new GRect(0,0,50,50);
		rect.setVisible(true);
		add(rect);
		for(int i=0; i<PARTICLE_COUNT; i++){
			this.particles[i] = new Particle();
			this.particles[i].bringToLife();
//			this.particles[i].bringToLife();
			add(this.particles[i]);
		}

		/*
		 * 	GRect rect = new GRect(10,10,100,100);
		 * 	rect.setColor(new Color(128,0,0,100));
		 * 	rect.setFilled(true);
		*/
	}
	
	private void updateBoard(){
		for(int i=0; i<PARTICLE_COUNT; i++){
			this.particles[i].update();			
		}
		
	}
}

