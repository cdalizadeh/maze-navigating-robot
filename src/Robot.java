import java.util.HashMap;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.SensorPort;

import lejos.hardware.sensor.EV3GyroSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;

public class Robot<T> extends GraphNavigator<T> {
	Pose pose;
	HashMap<int, String>[] walls;
	
	EV3GyroSensor orientationSensor;
	EV3UltrasonicSensor distanceSensor;
	
	public Robot(Graph<T> graph, Pose pose) {
		super(graph);
		this.pose = pose;
		EV3UltrasonicSensor distanceSensor = new EV3UltrasonicSensor(SensorPort.S4);
		EV3GyroSensor orientationSensor = new EV3GyroSensor(SensorPort.S3);

		HashMap<int, String>[] walls = new HashMap[games.size()];
		walls[0].put(5, "r");
		walls[1].put(2, "l");
		walls[1].put(6, "r");
		walls[2].put(3, "l");
		walls[2].put(1, "r");
		walls[3].put(4, "l");
		walls[3].put(2, "r");
		walls[4].put(3, "r");
		walls[4].put(9, "l");
		walls[5].put(0, "l");
		walls[5].put(10, "r");
		walls[6].put(1, "l");
		walls[6].put(11, "l");
		walls[7].put(8, "l");
		walls[7].put(12, "r");
		walls[8].put(7, "r");
		walls[9].put(4, "r");
		walls[9].put(14, "l");
		walls[10].put(5, "l");
		walls[11].put(6, "r");
		walls[11].put(16, "l");
		walls[12].put(7, "l");
		walls[12].put(17, "r");
		walls[13].put(18, "r");
		walls[14].put(9, "r");
		walls[15].put(20, "r");
		walls[16].put(11, "r");
		walls[17].put(12, "l");
		walls[17].put(22, "r");
		walls[18].put(13, "l");
		walls[18].put(23, "r");
		walls[19].put(24, "l");
		walls[20].put(15, "l");
		walls[20].put(21, "r");
		walls[21].put(20, "l");
		walls[22].put(17, "l");
		walls[23].put(18, "l");
		walls[23].put(24, "r");
		walls[24].put(19, "r");
		walls[24].put(23, "l");
		
		this.walls = walls;
	}
	
	public void moveAhead(double distance) {
		double wheelDiameter = 5.6;
		double revolutions = distance / (wheelDiameter * Math.PI);
		int angle =  (int) (revolutions * 1.733);
		Motor.B.rotate(angle);
		Motor.C.rotate(angle);
	}
	
	public void followWall(String wall, double distance) {
		if (wall == "left") {
			Motor.A.rotate(-200);
		}
		else if (wall == "right") {
			Motor.A.rotate(200);
		}
		
	}
	
	public void moveNextNode(T nextNode) {
		int node = (int) nextNode;
		int delta = pose.node - node;
		double distance = 30.48;
		switch (delta){
			case -1:
				orient(0);
				moveAhead(distance);
				// followWall(wall, distance)
				break;
			case -5:
				orient(270);
				moveAhead(distance);
				break;
			case 1:
				orient(180);
				moveAhead(distance);
				break;
			case 5:
				orient(90);
				moveAhead(distance);
				break;
		}
		this.pose.node = (int) nextNode;
	}
	
	public void orient(double newTheta) {
		double deltaTheta = newTheta - this.pose.theta;
		double pivotTheta = deltaTheta > 180.0 ? deltaTheta : deltaTheta - 360.0;
		pivot(pivotTheta);
		this.pose.theta = newTheta;
	
	public void pivot(double turnAngle){
		orientationSensor.reset();
		int sampleSize = orientationSensor.sampleSize();
		float[] angle = new float[sampleSize];
		orientationSensor.getAngleMode().fetchSample(angle, 0);
		
		int speed = 8;
		int slowSpeed = 3;
		Motor.C.setSpeed(speed);
		Motor.B.setSpeed(speed);
		
		if (turnAngle > 0){
			Motor.B.forward();
			Motor.C.backward();
			while(angle[0] < turnAngle - 5){
				orientationSensor.getAngleMode().fetchSample(angle, 0);
			}
			Motor.C.setSpeed(slowSpeed);
			Motor.B.setSpeed(slowSpeed);
			while(angle[0] < turnAngle){
				orientationSensor.getAngleMode().fetchSample(angle, 0);
			}
		}
		else{
			Motor.C.forward();
			Motor.B.backward();
			while(angle[0] > turnAngle + 5){
				orientationSensor.getAngleMode().fetchSample(angle, 0);
			}
			Motor.C.setSpeed(slowSpeed);
			Motor.B.setSpeed(slowSpeed);
			while(angle[0] > turnAngle){
				orientationSensor.getAngleMode().fetchSample(angle, 0);
			}
		}
		Motor.B.setSpeed(0);
		Motor.C.setSpeed(0);
	}

	public void pivot_test(){
		pivot(90);
		pivot(-90);
		System.exit(0)
	}

	public void moveAhead_test(){
		moveAhead(30.48);
		System.exit(0)
	}

	public void orient_test1(){
		this.pose.theta = 0;
		orient(90)
		assert(this.pose.theta == 90);
		System.exit(0)
	}

	public void orient_test2(){
		this.pose.theta = 90;
		orient(270)
		assert(this.pose.theta == 270);
		System.exit(0)
	}
}
	