package BubbleShooter;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.*;



class Arrow extends Image
{
	Polygon collider; 
	float arrowPX;
	float arrowPY;
	
	public Arrow( float x, float y ) throws SlickException
	{
		super( "/res/Arrow.png" );
		arrowPX = x + 134;	
		arrowPY = y + 50.5f ;
	}
	
	@Override
	public void draw(float x, float y) 
	{
		super.draw(x, y);
		arrowPX = x;
		arrowPY = y;
	}
	
	public void update()
	{
		collider = new Polygon();
		collider.addPoint( arrowPX + 48.5f, arrowPY + 11.5f );
		collider.addPoint( arrowPX + 48.5f, arrowPY + 5.5f );
		collider.addPoint( arrowPX + 51f, arrowPY + 9f );
	}
	
	public String toString()
	{
		return String.format( "%f %f", arrowPX, arrowPY );	
	}
}