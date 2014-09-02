package com.fyp.ratha;

public class Constants {
	public static final String APPTAG = "RATHA";
	
	public static final String OBDII_REAL_TIME_DATA="obd2_real_time_data";	
	
	public static final String OBDII_SPEED="obd2_speed";	
	public static final String OBDII_SPEED_LABEL="OBD-II Speed";	
	
	public static final String OBDII_RPM="obd2_rpm";	
	public static final String OBDII_RPM_LABEL="RPM";
	
	public static final String TIME="time";	
	public static final String VALUE="value";	
	
	public static boolean validateRootDataType(String datatype){
		if (datatype.equals(OBDII_REAL_TIME_DATA)){
			return true;
		}else return false;
	}
	
	public static boolean validateOBD2DataType(String datatype){
		if (datatype.equals(OBDII_SPEED) || datatype.equals(OBDII_RPM)){
			return true;
		}else return false;
	}
	
}
