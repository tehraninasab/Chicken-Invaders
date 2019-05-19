package game.enemy.chickenGroup;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

import game.Animatable;
import game.Velocity;
import game.enemy.Chicken;

public class RectangularGroup implements Animatable,ChickenGroup{
	public ArrayList<Chicken> chickens;
	public Thread comeInThread;
	public Thread velocityHandler;
	private int chickenLevel;
	private int row;
	private int column;

	public RectangularGroup(int row, int column, int chickenLevel)
	{
		this.chickenLevel = chickenLevel;
		this.row = row;
		this.column = column;
		initialize();
	}

	private void initialize()
	{
		chickens = new ArrayList();
		moveHandler();
	}


	@Override
	public void moveHandler() {

		comeInFunction();

		translationalMotion();



	}

	@Override
	public void paint(Graphics2D g2) {
		for(Chicken chicken : chickens)
		{
			chicken.paint(g2);
		}
	}

	@Override
	public void move() {
		System.out.println("in move");
		synchronized(chickens) {
			for(Chicken chicken: chickens)
			{
				System.out.println("in for");

				chicken.move();
			}
		}
	}

	@Override
	public void comeInFunction() {
		System.out.println("in come in");
		comeInThread = new Thread(new Runnable()
		{

			@Override
			public void run() {
				for(int i = 0; i<column; i++)
				{
					for(int j = 0; j<row; j++)
					{
						synchronized(chickens) {
							Chicken c = new Chicken(new Point(-50, 150+100*j),new Velocity(10, 0), chickenLevel);
							chickens.add(c);
							System.out.println("added");
						}

					}
					try {
						Thread.sleep(800);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

		});
//		comeInThread.start();
		try {
			comeInThread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void translationalMotion() {
		velocityHandler = new Thread(new Runnable()
		{

			@Override
			public void run() {
				try {
					Thread.sleep(9000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				while(RectangularGroup.this.chickens.size() != 0) {
					for(Chicken chicken : chickens)
					{
						synchronized(chicken)
						{
							Velocity v = chicken.getVelocity();
							v.vx *= -1;

						}

					}
					try {
						Thread.sleep(4000-400*column);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

		});
//		velocityHandler.start();
	}

	@Override
	public void rotationalMotion() {

	}

	@Override
	public ArrayList<Chicken> getGroup() {
		return chickens;
	}

	@Override
	public void startThreads() {
		comeInThread.start();
		velocityHandler.start();
	}

	@Override
	public void joinThreads() {
		// TODO Auto-generated method stub
		
	}

//	@Override
//	public void joinThreads() {
//		this.comeInThread.join();
//		this.velocityHandler.join();
//		
//	}




}