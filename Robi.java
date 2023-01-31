package KoelewijnKeanu;
import robocode.*;

public class Robi extends JuniorRobot {
 	int moveCounter = 0;
    int moveDirection = 1;
    int moveDistance = 100;

    public void run() {
        setColors(black, orange, yellow);
        while (true) {
			remainingPlayerReaction();
        }
    }

    public void onScannedRobot() {
		int scannedDist = scannedDistance;
        turnGunTo(scannedAngle);
		enemyEnergy();
		//distance less than a quarter of the field height, the robot moves backwards.
        if (scannedDistance < fieldHeight / 4) {
            back(fieldHeight / 2);
        }
    }
	
	public void enemyEnergy() {
		int scannedEnerg = scannedEnergy;
		if (scannedEnerg > 50){
			fire(1.5);
		} else if (scannedEnerg < 50) {
			fire(2.5);
		} else if (scannedEnerg < 25) {
			fire(3);
		} else {
			fire(1);
		}
	}	

    public void onHitByBullet() {
	//turns right by the angle it was hit, turns 90 degrees to right and moves forward.
        if (hitByBulletAngle != -1) {
            turnRight(hitByBulletAngle);
			turnRight(90);
            ahead(fieldHeight / 2);
        }
    }

    public void onHitWall() {
	//the robot rotates the angle hitWallAngle, after that it drives backwards.
        if (hitWallAngle != -1) {
           	turnRight(hitWallAngle);
           	back(fieldHeight / 4);
            turnRight(90);
        }
    }
	
	public void remainingPlayerReaction() {
		int checkPlayers = others;
			if ( checkPlayers > 20) {
				defence();
				} 
			else if ( checkPlayers < 20) {
				balanced();
				}
			else if ( checkPlayers < 10) {
				attack();
				}
	}

	public void defence() {
		//calculated by subtracting (fieldHeight) from the current y-position of the robot
        double movement = fieldHeight - robotY;
		int head = heading;
        if (head < 90) {
            ahead((int)movement);
            turnRight(90 - head);
        } else if (head > 90) {
            back((int)movement);
            turnLeft(head - 90);
        } else {
            ahead((int)movement);
        }
        energySafeCheck();
    }

	public void balanced() {
        zigZagMovement();
    }
	
	public void zigZagMovement() {
		//If the modulo through 20 is equal to zero, the direction of the move is reversed.
		if (moveCounter % 20 == 0) {
            moveDirection *= -1;
        }		
        ahead(moveDistance * moveDirection);
        turnRight(90);
        moveCounter++;
        energySafeCheck();
        moveDistance += 5;
	}

	public void attack() {
		int scannedDist = scannedDistance;
		int scannedBear = scannedBearing;
		//turn gun to enemy
		if (scannedDist > 0) {
           int angle = scannedBear + heading - gunHeading;
           bearGunTo(angle);
			//goes to enemy
		   int goToEnemy = scannedDist - 50;
           ahead(goToEnemy);
           turnRight(scannedBear);
         }
         else {
            turnGunRight(360);
            ahead(fieldHeight/2);
         }
		 energySafeCheck();
	}

	public void energySafeCheck() {
        if (energy < 20) {
            back(fieldHeight / 2);
            turnRight(180);
            ahead(fieldHeight / 4);
            turnRight(90);
        }
    }
}
