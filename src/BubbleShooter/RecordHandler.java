package BubbleShooter;

import java.awt.Dimension;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class RecordHandler
{
	private Formatter output;
	private Scanner input;
	public float score[];
	public String name[];
	public int n;
	//public int entries;
	RecordHandler()
	{
		
	}
	
	public void RecordCheckWithGUI( float sc )
	{
		JOptionPane.showMessageDialog(null,"Your Score is : " + sc,"Your Score",1 );
		try 
		{
			input = new Scanner( new File( "Records.txt" ) );
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		score = new float[10];
		name = new String[10];
		n = 0;
	    while( input.hasNext() )
		{
	    	if( n == 10 )
	    	{
	    		break;
	    	}
			name[ n ] = input.nextLine();
			score[ n ] = Float.parseFloat( input.nextLine() );
			n++;
		}
		input.close();
		boolean addFlag = false;
		int newRecordPosition = 0;
		if( n == 0 )
		{
			addFlag = true;
		}
		else
		{
			for( int i = 0; i<n; i++ )
			{
				if( sc >= score[ i ] )
				{
					addFlag = true;
					newRecordPosition = i;
					break;
				}
			}	
		}
		n = n >= 10 ? 9 : n;
		if( addFlag )
		{
			try 
			{
				output = new Formatter( "Records.txt" );
			} catch (FileNotFoundException e) 
			{
				e.printStackTrace();
			}
			String nn = JOptionPane.showInputDialog( String.format( "New High Score \nEnter Your Name : \n" ) );;
			String buffer = "";
			int cnt = 1;
			for( int i = 0; i <= n; i++ )
			{
				if( i < newRecordPosition )
				{
					buffer =  buffer + String.format( "%s\n%f\n", name[ i ], score[ i ] );
				}
				else if( i == newRecordPosition )
				{
					buffer =  buffer + String.format( "%s\n%f\n",nn,sc );
				}
				else
				{
					buffer =  buffer + String.format( "%s\n%f\n", name[ i - 1 ], score[ i - 1 ] );
				}
				cnt++;
			}
			output.format( "%s", buffer );
			output.close();
		}
	}
	
	boolean newRecord( float sc )
	{
		try 
		{
			input = new Scanner( new File( "Records.txt" ) );
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		score = new float[10];
		name = new String[10];
		n = 0;
	    while( input.hasNext() )
		{
	    	if( n == 10 )
	    	{
	    		break;
	    	}
			name[ n ] = input.nextLine();
			score[ n ] = Float.parseFloat( input.nextLine() );
			n++;
		}
		input.close();
		boolean addFlag = false;
		int newRecordPosition = 0;
		if( n <= 9 )
		{
			addFlag = true;
		}
		else
		{
			for( int i = 0; i<n; i++ )
			{
				if( sc >= score[ i ] )
				{
					addFlag = true;
					newRecordPosition = i;
					break;
				}
			}	
		}
		return addFlag;
	}
	
	void RecordCheck( float sc, String nn )
	{
		try 
		{
			input = new Scanner( new File( "Records.txt" ) );
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		score = new float[10];
		name = new String[10];
		n = 0;
	    while( input.hasNext() )
		{
	    	if( n == 10 )
	    	{
	    		break;
	    	}
			name[ n ] = input.nextLine();
			score[ n ] = Float.parseFloat( input.nextLine() );
			n++;
		}
		input.close();
		boolean addFlag = false;
		int newRecordPosition = 0;
		if( n == 0 )
		{
			addFlag = true;
		}
		else
		{
			for( int i = 0; i<n; i++ )
			{
				if( sc >= score[ i ] )
				{
					addFlag = true;
					newRecordPosition = i;
					break;
				}
			}	
		}
		n = n >= 10 ? 9 : n;
		if( addFlag )
		{
			try 
			{
				output = new Formatter( "Records.txt" );
			} catch (FileNotFoundException e) 
			{
				e.printStackTrace();
			}
			String buffer = "";
			int cnt = 1;
			for( int i = 0; i <= n; i++ )
			{
				if( i < newRecordPosition )
				{
					buffer =  buffer + String.format( "%s\n%f\n", name[ i ], score[ i ] );
				}
				else if( i == newRecordPosition )
				{
					buffer =  buffer + String.format( "%s\n%f\n",nn,sc );
				}
				else
				{
					buffer =  buffer + String.format( "%s\n%f\n", name[ i - 1 ], score[ i - 1 ] );
				}
				cnt++;
			}
			output.format( "%s", buffer );
			output.close();
		}
	}
	
	void getData( )
	{
		try 
		{
			input = new Scanner( new File( "Records.txt" ) );
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		score = new float[10];
		name = new String[10];
		n = 0;
	    while( input.hasNext() )
		{
	    	if( n == 10 )
	    	{
	    		break;
	    	}
			name[ n ] = input.nextLine();
			score[ n ] = Float.parseFloat( input.nextLine() );
			n++;
		}
		input.close();
	}
	
	public void ViewScore()
	{
		try 
		{
			input = new Scanner( new File( "Records.txt" ) );
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		score = new float[10];
		name = new String[10];
		n = 0;
	    while( input.hasNext() )
		{
	    	if( n == 10 )
	    	{
	    		break;
	    	}
			name[ n ] = input.nextLine();
			score[ n ] = Float.parseFloat( input.nextLine() );
			n++;
		}
		input.close();
		String[][] rowData = new String[10][3];
		for( int i = 0; i < n; i++ )
		{
			rowData[ i ][ 0 ] =""+(i+1);
			rowData[ i ][ 1 ] =""+name[i];
			rowData[ i ][ 2 ] =""+(int)score[i];
		}
		String[] columnNames = new String[3];
		columnNames[0] = "Rank";
		columnNames[1] = "Name";
		columnNames[2] = "Score";
		JTable records = new JTable(rowData, columnNames);	
		records.setEnabled(false);
		records.setPreferredScrollableViewportSize( new Dimension( 400, 160 ) );
		records.setFillsViewportHeight(true);
		JOptionPane.showMessageDialog(null,new JScrollPane(records),"High Scores",1);
	}
}
