package com.qualfacul.hades.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.qualfacul.hades.annotation.WebComponent;

@WebComponent
public class HadesDateFormat extends SimpleDateFormat{
	private static final long serialVersionUID = 1L;
	public static String HADES_FULLDATE_FORMAT = "dd/MM/yyyy hh:mm:ss";
	public static String HADES_SIMPLEDATE_FORMAT = "dd/MM/yyyy";
	
	public HadesDateFormat(){
		super(HADES_FULLDATE_FORMAT);
	}
	
	public HadesDateFormat(String format){
		super(format);
	}
	
	public String format(Calendar calendar){
		if (calendar == null){
			return null;
		}
		Date date = calendar.getTime();
		return super.format(date);
	}
}
