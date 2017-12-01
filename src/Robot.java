import java.util.HashMap;
import java.util.Vector;

import lejos.hardware.motor.Motor;
import lejos.hardware.port.SensorPort;

import lejos.hardware.sensor.EV3GyroSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;

public class Robot<T> extends GraphNavigator<T> {
	Pose pose;
	Vector<HashMap<Integer, String>> walls;
	
	EV3GyroSensor orientationSensor;
	EV3UltrasonicSensor distanceSensor;
	
	public Robot(Graph<T> graph, Pose pose, Vector<HashMap<Integer, String>> walls) {
		super(graph);
		this.pose = pose;
		EV3UltrasonicSensor distanceSensor = new EV3UltrasonicSensor(SensorPort.S4);
		EV3GyroSensor orientationSensor = new EV3GyroSensor(SensorPort.S3);

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
	}
	
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
		System.exit(0);
	}

	public void moveAhead_test(){
		moveAhead(30.48);
		System.exit(0);
	}

	public void orient_test1(){
		this.pose.theta = 0;
		orient(90);
		assert(this.pose.theta == 90);
		System.exit(0);
	}

	public void orient_test2(){
		this.pose.theta = 90;
		orient(270);
		assert(this.pose.theta == 270);
		System.exit(0);
	}
}
	