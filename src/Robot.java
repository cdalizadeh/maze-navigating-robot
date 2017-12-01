
import lejos.hardware.motor.Motor;
import lejos.hardware.port.SensorPort;

import lejos.hardware.sensor.EV3GyroSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;

public class Robot<T> extends GraphNavigator<T> {
	Pose pose;
	
	EV3GyroSensor orientationSensor;
	EV3UltrasonicSensor distanceSensor;
	
	public Robot(Graph<T> graph, Pose pose) {
		super(graph);
		this.pose = pose;
		EV3UltrasonicSensor distanceSensor = new EV3UltrasonicSensor(SensorPort.S4);
		EV3GyroSensor orientationSensor = new EV3GyroSensor(SensorPort.S3);
	}
	
	public void moveAhead() {
		double revolutions = 30.5 / (5.6 * Math.PI);
		int angle =  (int) (revolutions * 1.733);
		Motor.B.rotate(angle);
		Motor.C.rotate(angle);
	}
	
	
	public void moveNextNode(T nextNode) {
		int node = (int) nextNode;
		int delta = pose.node - node;
		switch (delta){
			case -1:
				orient(0);
				moveAhead();
				break;
			case -5:
				orient(270);
				moveAhead();
				break;
			case 1:
				orient(180);
				moveAhead();
				break;
			case 5:
				orient(90);
				moveAhead();
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
	
	public void pivot(double endAngle){	
		int sampleSize = orientationSensor.sampleSize();
		float[] angle = new float[sampleSize];
		orientationSensor.getAngleMode().fetchSample(angle, 0);
		
		double startAngle = angle[0];
		
		double turn = endAngle - this.pose.theta;
		System.out.println(""+turn+""+angle[0]);
		
		int speed = 0;
		
		
			while(angle[0] - turn > 2){
				speed = Math.max(8,(int)(angle[0] - turn));
				Motor.C.setSpeed(speed);
				Motor.B.setSpeed(speed);
				Motor.B.forward();
				Motor.C.backward();
				orientationSensor.getAngleMode().fetchSample(angle, 0);
				System.out.println(""+angle[0]);
			}
		
		
		
			while(angle[0] - turn < -2){
				speed = Math.max(8,(int)(turn - angle[0]));
				Motor.C.setSpeed(speed);
				Motor.B.setSpeed(speed);
				Motor.B.backward();
				Motor.C.forward();
				orientationSensor.getAngleMode().fetchSample(angle, 0);
				System.out.println(""+angle[0]);
			
		}
		
		Motor.B.setSpeed(0);
		Motor.C.setSpeed(0);
	}
}
	