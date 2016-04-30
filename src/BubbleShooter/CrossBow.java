package BubbleShooter;

import org.newdawn.slick.*;
import java.util.*;
public class CrossBow 
{
	ArrayList< Arrow > arrows;
	private float bowPY;
	private float bowSensitivity = 0.45f;
	private float arrowSpeed = 1f;
	private Image f1[];
	private Image f2[];
	private float bowPX = 0;
	private Animation bowDraw;
	private Animation bowRelease;
	private int d1[];
	private int d2[];
	private boolean bowDrawn;
	private Sound bowDrawSound;
	private Sound bowReleaseSound;
	private boolean bowDrawnSound;
	int arrowsLeft;
	CrossBow() throws SlickException
	{
		arrowsLeft = 15;
		arrows = new ArrayList< Arrow >();
		f1 = new Image[5];
		f2 = new Image[4];
		d1 = new int[5];
		d2 = new int[4];
		bowDrawn = false;
		bowDrawSound = new Sound("/res/bowDrawSound.wav");
		bowReleaseSound = new Sound( "/res/bowReleaseSound.wav" );
		f1[0] = f2[3] = new Image("/BowAnimi/Frame1.png");
		f1[1] = f2[2] = new Image("/BowAnimi/Frame2.png");
		f1[2] = f2[1] = new Image("/BowAnimi/Frame3.png");
		f1[3] = f2[0] = new Image("/BowAnimi/Frame4.png");
		f1[4] = new Image("/BowAnimi/Frame5.png");
		for( int i = 0; i<5; i++)
		{
			d1[i] = 90;
		}
		for( int i = 0; i<4; i++)
		{
			d2[i] = 20;
		}
		bowDraw = new Animation(f1, d1);
		bowRelease = new Animation(f2,d2);
	}
	
	void arrowDisplay( Graphics g, int Delta )
	{
		for( int i = 0; i < arrows.size(); i++ )
		{
			if( arrows.get( i ).arrowPX > 1500 )
			{
				arrows.remove(i);
				i--;
			}
		}
		for( int i = 0; i < arrows.size(); i++ )
		{
			arrows.get(i).draw( arrows.get(i).arrowPX, arrows.get(i).arrowPY );
			arrows.get(i).arrowPX = arrows.get(i).arrowPX + arrowSpeed * Delta;
		}
	}
	
	void bowControl( GameContainer gc, Graphics g, int Delta )
	{
		Input input = gc.getInput();
		if( input.isKeyDown( Input.KEY_DOWN ) )
		{
			bowPY = bowPY + bowSensitivity * Delta ;
		}
		if( input.isKeyDown( Input.KEY_UP ) )
		{
			bowPY = bowPY - bowSensitivity * Delta ;
		}
		if( bowPY < 35 )
		{
			bowPY = bowPY + bowSensitivity * Delta ;
		}
		if( bowPY > gc.getHeight() - 133 )
		{
			bowPY = bowPY - bowSensitivity * Delta ;
		}
	}
	
	void bowAnimi( GameContainer gc, Graphics g ) throws SlickException
	{
		Input input = gc.getInput();
		if( !gc.isPaused() )
		{
			if( input.isKeyPressed( Input.KEY_SPACE ) )
			{
				bowDrawSound.play();
			}
			if( !input.isKeyDown( Input.KEY_SPACE ))
			{
				bowDrawSound.stop();
			}
			if( bowDrawnSound && !input.isKeyDown( Input.KEY_SPACE ) )
			{
				bowReleaseSound.play();
				bowDrawnSound = false;
			}
			if( input.isKeyDown(Input.KEY_SPACE) && !bowDrawn )
			{
				bowDraw.draw(0, bowPY);
				
				if( bowDraw.getCurrentFrame() == f1[4] )
				{
					bowDrawnSound = bowDrawn = true;
					bowDraw.restart();
				}	
			}
			else if( !input.isKeyDown(Input.KEY_SPACE) && bowDrawn )
			{
				bowRelease.draw(0, bowPY);
				
				if( bowRelease.getCurrentFrame() == f2[3] )
				{
					bowDrawn = false;
					arrowsLeft--;
					arrows.add( new Arrow( bowPX, bowPY ) );
					bowRelease.restart();
				}	 
			}
			else if( bowDrawn )
			{
				f1[4].draw(0,bowPY);
			}
			else
			{
				f1[0].draw(0, bowPY);
			}
		}
		else
		{
			f1[0].draw(0, bowPY);
		}
		
	}
}
