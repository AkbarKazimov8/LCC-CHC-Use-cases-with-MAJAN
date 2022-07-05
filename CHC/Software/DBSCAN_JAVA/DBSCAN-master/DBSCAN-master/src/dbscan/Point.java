package dbscan;

public class Point 
{
	private int id;
	private int x;

	private int y;

	
	Point(int id, int a, int b)
	 { this.id=id;
		x=a;
		y=b;
	 }
	
	public int getX ()
	 {

		return x;

	 }


	public int getY () 
	{

		return y;

	}
	public int getId () 
	{

		return this.id;

	}

	

}

