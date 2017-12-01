
import lejos.hardware.motor.NXTRegulatedMotor;
import lejos.hardware.motor.Motor;
public interface UltrasonicController {
	static final int DEFAULT_SPEED = 200;
	static final NXTRegulatedMotor leftMotor = Motor.B, rightMotor = Motor.C;
	
	public int readSensorDistance();
	
	// 1mm <= sensorDistance <= 255mm
	public void processSensorData(int distance);
	
	public String getType();
}
