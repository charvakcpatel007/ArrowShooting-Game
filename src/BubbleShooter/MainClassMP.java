package BubbleShooter;

import org.newdawn.slick.*;
import org.newdawn.slick.gui.*;

import java.awt.Font;
import java.util.ArrayList;
import java.util.Random;

public class MainClassMP extends BasicGame
{
	static AppGameContainer app;
	private boolean LoadSource = true;
	private CrossBow cb;
	private boolean fullScreen;
	private int Delta;
	private long mills;
	private ArrayList<Balloon> blns;
	private int spawnTimeInterval = 1500;
	private Image gutter;
	private float actualScore = 0;
	private float tempScore = 0;
	private Random rm;
	private int totalTime = 60000;
	private long mill = System.currentTimeMillis();
	private long previousTime = System.currentTimeMillis();
	private Image pauseBG;
	private int pauseSelector;
	private boolean scoreView;
	private boolean helpView;
	private Image mainBG;
	private Image helpBG;
	private Image highScoreBG;
	private Image point;
	private boolean mMenu = true;
	private RecordHandler handler;
	private long hitTime = 0;
	private long temphitTime = 0;
	private float scoreDue = 0;
	private float tempscoreDue;
	private int tempcountBalloon;
	private int countBalloon = 0;
	private int doublefactor;
	private long doubleTimer = 15000;
	private long dT = 15000;
	private long sT;
	private long statusTimer = 2;
	private int statusCode = 0;
	private float yOfPointer;
	private Thread thread;
	private boolean firstTimeInMenu = true;
	private TrueTypeFont ttf;
	private java.awt.Font font;
	private boolean showHighScore;
	private boolean enterScore;
	private boolean showPlayerScore;
	private boolean showHighScoreandMain;
	private String nameOfPlayer;
	private TextField nameInput;
	private boolean enterOne;
	private Music music;
	private long musicTimer = 250000;
	private long mT;
	public MainClassMP(String title) throws SlickException 
	{
		super(title);
	}

	@Override
	public void init( GameContainer gc) throws SlickException
	{	
		gc.setVSync(true);
		if( LoadSource )
		{
			try 
			{
				Balloon.ballonBlastSound= new Sound("/res/BalloonBlast.wav");
			} catch (SlickException e)
			{
				e.printStackTrace();
			}
			cb = new CrossBow();
			rm = new Random();
			gutter = new Image( "/res/Gutter.png" );
			LoadSource = false;
			pauseBG = new Image( "/res/pauseMen.png" );
			mainBG = new Image( "/res/mainMenu.png" );
			helpBG = new Image( "/res/Help.png" );
			point = new Image( "/res/point.png" );
			highScoreBG = new Image( "/Lib/HighScore.png" );
			music = new Music( "/res/MainSong.ogg" );
			music.loop();
			gc.setMusicOn(true);
			gc.setMusicVolume( 0.65f );
			music.play();
			gc.setAlwaysRender(true);
		}
		statusTimer = 2000;
		doubleTimer = 15000;
		dT = System.currentTimeMillis() - 1000000;
		cb.arrowsLeft = 15;
		for( int i = cb.arrows.size() - 1; i >= 0 ; i-- )
		{
			cb.arrows.remove( 0 );
		}
		handler = new RecordHandler();
		gc.resume();
		gc.setAlwaysRender(true);
		gc.setShowFPS( false );
		mills = System.currentTimeMillis();
		blns = new ArrayList<Balloon>();
		spawnTimeInterval = 2000;
		actualScore = 0;
		tempScore = 0;
		totalTime = 60000;
		mill = System.currentTimeMillis();
		gc.setSoundOn( true );
		doublefactor = 1;
		scoreView = true;
		font = new java.awt.Font( "Snap ITC" , Font.PLAIN, 22);
		ttf = new TrueTypeFont(font, true);
		gc.setFullscreen(true);
		showHighScore = false;
		showHighScoreandMain = false;
		enterScore = false;
		nameInput = new TextField( gc, ttf, 570, 380,500,35, new ComponentListener() {
	         public void componentActivated(AbstractComponent source)
	         {
	            nameOfPlayer = nameInput.getText();
				handler.RecordCheck( actualScore , nameOfPlayer );
				nameInput.setFocus(false);
				enterScore = false; 
				showHighScoreandMain = true;
				
	         }
	    }); 
		nameInput.setBorderColor(  new Color( 0, 0, 0, 0 ) );
		nameInput.setBackgroundColor( new Color( 0, 0, 0, 0 ) );
		nameInput.setTextColor( new Color( 73, 182, 255 ) );
		enterOne = false;
		mT = System.currentTimeMillis();
		music.play();
		music.setVolume(0.1f);
	
	}
		
	@Override
	public void render( GameContainer gc, Graphics g ) throws SlickException 
	{
		System.out.println( gc.getFPS() );
		if( ( System.currentTimeMillis() - mT ) > musicTimer )
		{
			mT = System.currentTimeMillis();
			music.play();
		}
		Image backGround = new Image("/res/BackGround.png");
		backGround.draw(0, 0, gc.getWidth(), gc.getHeight());
		setFullScreen(gc);
		balloonSpawn( gc );
		updateCollider();
		balloonUpdate();
		ballonCheck();
		drawGutters( g );
		g.setColor( Color.black );
		if( !mMenu )
		{
			MenuInputs(gc);
			scoreShowAnim(g);
			cb.arrowDisplay(g, Delta);
			if( cb.arrowsLeft > 0 )
			{
				cb.bowAnimi(gc, g);
				cb.bowControl(gc, g, Delta);
			}
			showArrowNum( gc, g );
			doubleTime( gc, g );
			statusShow( gc, g );
			Timer( gc, g );
		}
		else	
		{
			mainMenuCheck( gc, g );
		}
		if( gc.isPaused() && !mMenu && !showPlayerScore && !enterScore && !showHighScoreandMain )
		{
			pauseSystem( gc, g );
		}
		handlePostGame( gc, g );
	}
	
	private void statusShow(GameContainer gc, Graphics g) 
	{
		if( System.currentTimeMillis() - sT < statusTimer )
		{
			switch( statusCode )
			{
			case 1:
				ttf.drawString( 300, 40, "+5 Bonus Arrows", Color.black );
				break;
			case 2:
				ttf.drawString( 300, 40, "+10 s Bonus Time", Color.black );
				break;
			default:
			}
		}
	}
	
	private void handlePostGame( GameContainer gc, Graphics g )
	{
		Input input = gc.getInput();
		if( showPlayerScore )
		{
			highScoreBG.draw( 0, 0, gc.getWidth(), gc.getHeight() );
			ttf.drawString(570, 360, "Your Score : " + actualScore, new Color( 73, 182, 255 ));
			if( input.isKeyPressed( Input.KEY_ENTER ) )
			{
				showPlayerScore = false;
				if( handler.newRecord(actualScore) )
				{
					enterScore = true;
					nameOfPlayer = "";
				}
				else
				{
					showHighScoreandMain = true;
				}
			}
		}
		if( enterScore )
		{
			highScoreBG.draw( 0, 0, gc.getWidth(), gc.getHeight() );
			ttf.drawString(570, 360, "Enter Your Name : ", new Color( 73, 182, 255 ));
			Color temp = g.getColor();
			g.setColor( new Color( 255, 255, 255 ) );
			nameInput.render( gc, g);
			nameInput.setFocus( true );
			g.setColor( temp );
		}
		if( showHighScoreandMain )
		{
			highScoreBG.draw( 0, 0, gc.getWidth(), gc.getHeight() );
			drawHighScore(g);
			if( enterOne && input.isKeyPressed( Input.KEY_ENTER ) )
			{
				showHighScoreandMain = false;
				try 
				{
					music.stop();
					gc.reinit();
				} catch (SlickException e) 
				{
					e.printStackTrace();
				}
				mMenu = true;
			}
			if( input.isKeyPressed( Input.KEY_ENTER ) )
			{
				enterOne = true;
			}
		}
	}
	
	
	
	private void doubleTime(GameContainer gc, Graphics g)
	{
		if( System.currentTimeMillis() - dT > doubleTimer )
		{
			doublefactor = 1;
		}
		else
		{
			doublefactor = 2;
			ttf.drawString(  300, 20, "Double Score!!!" , Color.black);
		}
	}
	
	private void showArrowNum( GameContainer gc, Graphics g )
	{
		ttf.drawString( 10 ,20, "Arrows Left : "+cb.arrowsLeft+"", Color.black );
		if( cb.arrowsLeft <= 0 && cb.arrows.isEmpty() && !gc.isPaused() )
		{
			gc.pause();
			showPlayerScore = true;
		}
	}

	private void mainMenuCheck( GameContainer gc, Graphics g ) throws SlickException
	{
		Input input = gc.getInput();
		/*
		 * 0 start 269.40
		 * 1 high scores 363.35
		 * 2 help 458.79
		 * 3 exit 557.23
		 */
		float[] position = { 269.40f, 363.35f, 458.79f, 557.23f };
		Image temp = null;
		if( showHighScore )
		{
			if( input.isKeyPressed( Input.KEY_ENTER  ))
			{
				showHighScore = false;
			}
			temp = highScoreBG;
		}
		if( helpView )
		{
			if( input.isKeyPressed( Input.KEY_ENTER ))
			{
				helpView = false;
			}
			temp = helpBG;
		}
		
		if( !helpView && !showHighScore)
		{
			temp = mainBG;
		}
		temp.draw( 0, 0, gc.getWidth(), gc.getHeight() );
		if( firstTimeInMenu )
		{
			pauseSelector = 0;
			yOfPointer = position[ pauseSelector ];
			firstTimeInMenu = false;
		}
		if( input.isKeyPressed( Input.KEY_DOWN ) )
		{
			pauseSelector = ( pauseSelector + 1 ) % 4;
		}
		if( input.isKeyPressed( Input.KEY_UP ) )
		{
			pauseSelector = ( pauseSelector - 1 ) % 4;
			if( pauseSelector == -1 )
			{
				pauseSelector = 3;
			}
		}
		
		try
		{
			if( input.isKeyPressed( Input.KEY_ENTER ))
			{
				firstTimeInMenu = true;
				switch( pauseSelector )
				{
				case 0://start
					mMenu = false;
					music.stop();
					gc.reinit();
					break;
				case 1://high scores
					showHighScore = true;
					break;
				case 2://help
					helpView = true;
					break;
				case 3://exit
					gc.exit();
					break;
				}
			}
			if( position[ pauseSelector ] > yOfPointer )
			{
				yOfPointer += 0.6*Delta;
			}
			if( position[ pauseSelector ] < yOfPointer )
			{
				yOfPointer -= 0.6*Delta;
			}
			if( showHighScore )
			{
				drawHighScore(g);
			}
			if( !helpView && !showHighScore )
			{
				point.draw( 475, yOfPointer );
			}
		}
		catch( SlickException e )
		{
			e.printStackTrace();
		}
		
	}
	
	private void pauseSystem( GameContainer gc, Graphics g )
	{
		Input input = gc.getInput();
		Image temp = null;
		float[] position = { 220.25f, 305.15f, 394, 482f, 574f };
		if( helpView )
		{
			if( input.isKeyPressed( Input.KEY_ENTER ))
			{
				helpView = false;
			}
			temp = helpBG;
		}
		else
		{
			temp = pauseBG;
		}
		temp.draw( 0, 0, gc.getWidth(), gc.getHeight() );
		if( firstTimeInMenu )
		{
			pauseSelector = 0;
			yOfPointer = position[ pauseSelector ];
			firstTimeInMenu = false;
		}
		/*
		 * 0 resume
		 * 1 restart
		 * 2 help
		 * 3 main-menu
		 * 4 exit
		 */
		if( input.isKeyPressed( Input.KEY_DOWN ) )
		{
			pauseSelector = ( pauseSelector + 1 ) % 5;
		}
		if( input.isKeyPressed( Input.KEY_UP ) )
		{
			pauseSelector = ( pauseSelector - 1 ) % 5;
			if( pauseSelector == -1 )
			{
				pauseSelector = 4;
			}
		}
		try
		{
			if( input.isKeyPressed( Input.KEY_ENTER ))
			{
				firstTimeInMenu = true;
				switch( pauseSelector )
				{
				case 0://resume
					gc.resume();
					break;
				case 1://restart
					music.stop();
					gc.reinit();
					break;
				case 2://help
					helpView = true;
					break;
				case 3://main-menu
					mMenu = true;
					music.stop();
					gc.reinit();
					break;
				case 4://exit
					gc.exit();
					break;
				}
			}		
			if( position[ pauseSelector ] > yOfPointer )
			{
				yOfPointer += 10;
			}
			if( position[ pauseSelector ] < yOfPointer )
			{
				yOfPointer -= 10;
			}
			if( !helpView )
			{
				point.draw( 475, yOfPointer );
			}
		}
		catch( SlickException e )
		{
			e.printStackTrace();
		}
		
	}
	
	private void drawHighScore( Graphics g )
	{
		handler.getData();
		ttf.drawString( 480, 200, "Rank", new Color( 73, 182, 255 ) );
		for( int i = 1; i <= 10; i++ )
		{
			ttf.drawString( 480, 200 + i * 40, i + ".", new Color( 73, 182, 255 ) );
		}
		ttf.drawString( 620, 200,"Name", new Color( 73, 182, 255 ) );
		for( int i = 0; i < handler.n; i++ )
		{
			ttf.drawString( 620, 200 + ( i + 1 )* 40, handler.name[ i ], new Color( 73, 182, 255 ) );
		}
		ttf.drawString( 795, 200, "Score", new Color( 73, 182, 255 ) );
		for( int i = 0; i < handler.n; i++ )
		{
			ttf.drawString( 795, 200 + ( i + 1 )* 40, "" + (int)handler.score[ i ], new Color( 73, 182, 255 ) );
		}
		
	}
	
	private void Timer(GameContainer gc, Graphics g )
	{
		ttf.drawString( 650, 20, "Time Remaining : " + (totalTime - System.currentTimeMillis() + mill)/1000, Color.black );
		if( gc.isPaused() )
		{
			mill += System.currentTimeMillis() - previousTime;
		}
		if( System.currentTimeMillis() - mill >= totalTime )
		{
			mill  = System.currentTimeMillis();
			gc.pause();
			showPlayerScore = true;
		}
		previousTime = System.currentTimeMillis();
	}

	private void MenuInputs(GameContainer gc)
	{
		Input input = gc.getInput();
		if( input.isKeyPressed( Input.KEY_ESCAPE ) )
		{
			if( gc.isPaused() )
			{
				gc.resume();
			}
			else
			{
				gc.pause();
			}
		}
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException
	{
			Delta = delta;
	}
	
	void updateCollider()
	{
		for( Balloon b : blns )
		{
			b.update();
		}
		for( Arrow a : cb.arrows )
		{
			a.update();
		}
	}
	
	void balloonSpawn(GameContainer gc) throws SlickException
	{
		spawnTimeInterval = (rm.nextInt( 1300   ) + 1100); 
		if( !gc.isPaused() )
		{
			if( (System.currentTimeMillis() - mills) >= spawnTimeInterval )
			{
				mills = System.currentTimeMillis();
				Balloon temp = new Balloon();
				temp.ballonSpeed = rm.nextFloat() * 0.2f + 0.05f;
				if( rm.nextInt( 100 ) <= 35 )  
				{
					temp.balloonCode = rm.nextInt( 4 );
				}
				temp.ballonBlast = new Animation( temp.b[temp.balloonCode], temp. bduration );
				temp.ballonBlast.setLooping( false );
				chooseGutterAndSpawn( temp );
				blns.add( temp );
			}
		}  
	}
	
	void chooseGutterAndSpawn( Balloon temp )
	{
		temp.By = 740;
		switch( rm.nextInt(5) )
		{
		case 0:
			temp.Bx = 450;
			temp.reward = 10;
			break;
		case 1:
			temp.Bx = 650;
			temp.reward = 20;
			break;
		case 2:
			temp.Bx = 850;
			temp.reward = 30;
			break;
		case 3:
			temp.Bx = 1050;
			temp.reward = 40;
			break;
		case 4:		
			temp.Bx = 1250;
			temp.reward = 50;
			break;
		}
	}
	
	void balloonUpdate()
	{
		for( int i = 0; i < blns.size(); i++ )
		{
			if( blns.get(i).By < -100 )
			{
				blns.remove( i );
				i--;
			}
			else if( blns.get(i).blast == false )
			{
				blns.get(i).By = blns.get(i).By - blns.get(i).ballonSpeed * Delta;
			}
			
		}
	}
	
	void drawGutters( Graphics g )
	{
		gutter.draw( 450, 710 );
		gutter.draw( 650, 710 );
		gutter.draw(  850, 710 );
		gutter.draw(  1050, 710 );
		gutter.draw(  1250, 710 );
	}
	
	void ballonCheck()
	{
		for( Arrow arrow : cb.arrows )
		{
			for( Balloon balloon : blns )
			{
				if( arrow.collider.intersects( balloon.collider ) )
				{
					balloon.blast = true;
					Balloon.ballonBlastSound.play();
				}
			}
		}
		for( int i = 0; i < blns.size(); i++ )
		{
			if( blns.get(i).blast )
			{
				blns.get(i).ballonBlast.draw( blns.get(i).Bx, blns.get(i).By);
			}
			else if( blns.get(i).ballonBlast.getFrame() == 9 )
			{
				blns.get(i).ballonBlast.restart();
				Balloon.ballonBlastSound.stop();
				blns.get(i).blast = false;
			}
			else
			{
				blns.get(i).b[blns.get(i).balloonCode][0].draw( blns.get(i).Bx, blns.get(i).By);
			}
			
		}
		for( int i = 0; i < blns.size(); i++ )
		{
			if( blns.get(i).ballonBlast.getFrame() > 8 )
			{
				switch( blns.get(i).balloonCode )
				{
				case 0:
					statusCode = 0;
					break;
				case 1:
					cb.arrowsLeft += 5;
					statusCode = 1;
					sT = System.currentTimeMillis();
					break;
				case 2:
					totalTime = totalTime + 10000;
					statusCode = 2;
					sT = System.currentTimeMillis();
					break;
				case 3:
					doublefactor = 2;
					dT = System.currentTimeMillis();
					break;
				}
				scoreAdder( blns.get(i).reward );
				blns.remove( i );
				i--;
			}
		}
	}
	
	void scoreShowAnim( Graphics g )
	{
		if( System.currentTimeMillis() - hitTime > 1300 )
		{
			actualScore += doublefactor * scoreDue * countBalloon;
			scoreDue = 0;
			countBalloon = 0;
		}
		if( System.currentTimeMillis() - temphitTime < 2000 )
		{
			if( tempcountBalloon > 1 )
			{
				ttf.drawString( 1100, 45, tempcountBalloon + " * " + (int)tempscoreDue + " Bonus ", Color.black );
			}
		}
		else
		{
			tempcountBalloon = 0;
			tempscoreDue = 0;
		}
		if( tempScore < actualScore )
		{
			tempScore += 0.1 * Delta;
		}
		else 
		{
			tempScore = actualScore;
		}
		ttf.drawString( 1100, 20, "Your Score : "+(int)tempScore, Color.black );
	}
	
	void scoreAdder( float reward )
	{
		scoreDue += reward;
		countBalloon++;
		tempscoreDue = scoreDue;
		tempcountBalloon = countBalloon;
		temphitTime = System.currentTimeMillis();
		hitTime = System.currentTimeMillis();
	}
	
	void setFullScreen( GameContainer gc ) throws SlickException
	{
		Input input = gc.getInput();
		if( input.isKeyPressed( Input.KEY_F ))
		{
			fullScreen = !fullScreen;
			gc.setFullscreen( fullScreen );
		}
	}
	
	public static void main(String[] args)
	{
		try 
		{
			app = new AppGameContainer( new MainClassMP("ArrowShooting"));
			app.setAlwaysRender(true);
			app.setDisplayMode( 1366, 768, false );
			app.start();
		}
		catch (SlickException e) 
		{
			e.printStackTrace();
		}

	}
	

}
