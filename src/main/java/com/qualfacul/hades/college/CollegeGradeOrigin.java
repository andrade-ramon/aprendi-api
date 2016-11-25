package com.qualfacul.hades.college;

public enum CollegeGradeOrigin {
	MEC_IGC,
	MEC_CONTINUOUS_IGC,
	MEC_CI,
	STUDENT_INFRA,
	STUDENT_PRICE,
	STUDENT_TEACH
	;
	
	public boolean isFromStudent() {
		return this == STUDENT_INFRA || this == STUDENT_PRICE || this == STUDENT_TEACH; 
	}
}
