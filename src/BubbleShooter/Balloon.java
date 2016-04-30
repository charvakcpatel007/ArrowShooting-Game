package BubbleShooter;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Polygon;

class Balloon extends Image
{
	Image b[][];
	int[] bduration;
    Animation ballonBlast;
	float Bx;
	float By;
	float reward;
	Polygon collider;
	boolean blast;
	public static Sound ballonBlastSound;
	float ballonSpeed = 0.2f;
	int balloonCode;
	/*
	 * 0 for normal
	 * 1 for arrowAdder green
	 * 2 for timeAdder red
	 * 3 for doublescore golden
	 */
	
	public Balloon() throws SlickException 
	{
		super("/BallonAnimi/Collider.png");
		b = new Image[4][11];
		bduration = new int[11];
		Bx = 0;
		By = 0;
		b[0][0] = new Image("/BallonAnimi/Collider.png");
		b[0][1] = new Image("/BallonAnimi/Frame1.png");
		b[0][2] = new Image("/BallonAnimi/Frame2.png");
		b[0][3] = new Image("/BallonAnimi/Frame3.png");
		b[0][4] = new Image("/BallonAnimi/Frame4.png");
		b[0][5] = new Image("/BallonAnimi/Frame5.png");
		b[0][6] = new Image("/BallonAnimi/Frame6.png");
		b[0][7] = new Image("/BallonAnimi/Frame7.png");
		b[0][8] = new Image("/BallonAnimi/Frame8.png");
		b[0][9] = new Image("/BallonAnimi/Frame9.png");
		b[0][10] = new Image("/BallonAnimi/Frame10.png");
		
		b[1][0] = new Image("/Green/Collider.png");
		b[1][1] = new Image("/Green/Frame1.png");
		b[1][2] = new Image("/Green/Frame2.png");
		b[1][3] = new Image("/Green/Frame3.png");
		b[1][4] = new Image("/Green/Frame4.png");
		b[1][5] = new Image("/Green/Frame5.png");
		b[1][6] = new Image("/Green/Frame6.png");
		b[1][7] = new Image("/Green/Frame7.png");
		b[1][8] = new Image("/Green/Frame8.png");
		b[1][9] = new Image("/Green/Frame9.png");
		b[1][10] = new Image("/Green/Frame10.png");
		
		b[2][0] = new Image("/Red/Collider.png");
		b[2][1] = new Image("/Red/Frame1.png");
		b[2][2] = new Image("/Red/Frame2.png");
		b[2][3] = new Image("/Red/Frame3.png");
		b[2][4] = new Image("/Red/Frame4.png");
		b[2][5] = new Image("/Red/Frame5.png");
		b[2][6] = new Image("/Red/Frame6.png");
		b[2][7] = new Image("/Red/Frame7.png");
		b[2][8] = new Image("/Red/Frame8.png");
		b[2][9] = new Image("/Red/Frame9.png");
		b[2][10] = new Image("/Red/Frame10.png");
		
		b[3][0] = new Image("/Golden/Collider.png");
		b[3][1] = new Image("/Golden/Frame1.png");
		b[3][2] = new Image("/Golden/Frame2.png");
		b[3][3] = new Image("/Golden/Frame3.png");
		b[3][4] = new Image("/Golden/Frame4.png");
		b[3][5] = new Image("/Golden/Frame5.png");
		b[3][6] = new Image("/Golden/Frame6.png");
		b[3][7] = new Image("/Golden/Frame7.png");
		b[3][8] = new Image("/Golden/Frame8.png");
		b[3][9] = new Image("/Golden/Frame9.png");
		b[3][10] = new Image("/Golden/Frame10.png");
		for( int i = 0; i < 11; i++ )
		{
			bduration[i] = 34;
		}	
	}
	
	public void update()
	{
		collider = new Polygon();
		float x[] = { Bx + 34, Bx + 20, Bx + 15, Bx + 14, Bx + 19, Bx + 28, Bx + 32 };
		float y[] = { By + 4, By + 10, By + 22, By + 35, By + 47, By + 58, By + 60 };
		for( int i = 0; i< 7; i++ )
		{
			collider.addPoint(x[i], y[i]);
		}
	}
	
	@Override
	public void draw(float a, float b)
	{	
		super.draw(a, b);
		Bx = a;
		By = b;	
	}
}