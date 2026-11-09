package org.redtierobotics.robot2027;

import com.ctre.phoenix6.CANBus;

public class Constants {
	public static class Canbus {
		public static final CANBus CV = new CANBus("CV");
		public static final CANBus RIO = new CANBus("rio");
	}
}
