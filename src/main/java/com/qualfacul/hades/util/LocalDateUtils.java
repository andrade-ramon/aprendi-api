package com.qualfacul.hades.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;

public class LocalDateUtils {

	private static final ZoneId UTC_ZONE = ZoneId.of("America/Sao_Paulo");

	public static LocalDateTime from(Calendar calendar) {
		Instant instant = calendar.getTime().toInstant();
		
		return instant.atZone(UTC_ZONE).toLocalDateTime();
	}

}
