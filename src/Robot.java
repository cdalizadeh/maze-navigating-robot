import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.remote.ev3.RemotePort;

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
				pivot(0);
				moveAhead();
				break;
			case -5:
				pivot(270);
				moveAhead();
				break;
			case 1:
				pivot(180);
				moveAhead();
				break;
			case 5:
				pivot(90);
				moveAhead();
				break;
		}
	}

	public void pivot(double turnAngle){
		orientationSensor.reset();
		int sampleSize = orientationSensor.sampleSize();
		float[] angle = new float[sampleSize];
		orientationSensor.getAngleMode().fetchSample(angle, 0);
		
		double startAngle = angle[0];
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
}
	