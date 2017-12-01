
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.Motor;
public interface UltrasonicController {
	static final int DEFAULT_SPEED = 200;
	static final EV3LargeRegulatedMotor leftMotor = Motor.A, rightMotor = Motor.C;
	
	public int readSensorDistance();
	
	// 1mm <= sensorDistance <= 255mm
	public void processSensorData(int distance);
	
	public String getType();
}
