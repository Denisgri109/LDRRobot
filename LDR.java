package LDRRobot;
import robocode.*;
import java.awt.Color;
import java.util.Random;

// API help : https://robocode.sourceforge.io/docs/robocode/robocode/Robot.html

/**
 * LDR - a robot by LDR
 */
public class LDR extends Robot
{
	boolean offensive = true; // If true, rotate, searching for bots, otherwise, stick to walls
	int shootAmount=3; // How many times to shoot before turning	

	boolean peek; // Don't turn if there's a robot there
	double moveAmount; // How much to move
	double energy; // Keeps track of how much health the bot currently has

	/**
	 * run: Chase then stick to walls
	 */
	public void run()
	{
		setColors(Color.black,Color.blue,Color.black); // Body, Gun, Radar
		
		while(offensive==true)
		{
			turnRight(5); // Keep rotating, searching for bots
			
			energy = getEnergy();
			System.out.println(energy);
			if(energy<=55) // If energy is low enough
			{
				offensive=false; // Switch to defensive mode
			}
		}
		
		// Stick to the walls
		while(offensive==false)
		{
			// Initialize moveAmount to the maximum possible for this battlefield.
			moveAmount = Math.max(getBattleFieldWidth(), getBattleFieldHeight());
			// Initialize peek to false
			peek = false;
	
			// turnLeft to face a wall.
			turnLeft(getHeading() % 90);
			ahead(moveAmount);
			// Turn the gun to turn right 90 degrees
			peek = true;
			turnGunRight(90);
			turnRight(90);
	
			while (true) {
				// Look before we turn when ahead() completes
				peek = true;
				// Move up the wall
				ahead(moveAmount);
				// Don't look now
				peek = false;
				// Turn to the next wall
				turnRight(90);
			}
		}
	}

	/**
	 * onHitRobot:  Move away
	 */
	public void onHitRobot(HitRobotEvent e) {
		// If hit was from the front, move backwards
		if (e.getBearing() > -90 && e.getBearing() < 90) {
			back(100);
		} // Otherwise, move forward
		else {
			ahead(100);
		}
	}
	/**
	 * onHitWall: Move away
	 */
	public void onHitWall(HitWallEvent e){
		if(offensive==true){
			if(e.getBearing() >= 0){ //Right side
				turnLeft(e.getBearing()+90);
			} else {
				turnRight(e.getBearing()+90);
			}
		}
	}

	/**
	 * onScannedRobot:  Fire!
	 */ 
	public void onScannedRobot(ScannedRobotEvent e) {
		// Change firepower depending on the distance from opponent
		if(e.getDistance()>300){    
			fire(1);
		} else if (e.getDistance()>150){
			fire(2);
		} else {
			fire(3);
		}
		// Failsafe for if there's a robot on the next wall, so that it doesn't start moving up it until it's gone.
		if (peek){
			scan();
		}
	}
}