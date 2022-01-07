package com.farmaciasperuanas.inspector.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public final class Util {

	private Util() {
	}

	public static boolean isEmptyWithTrim(String character) {
		return (character == null) || (character.trim().length() == 0);
	}

	public static boolean isNotNull(Object object) {
		return (object != null);
	}

	public static LocalDateTime getCurrentLocalDateTime() {
		ZonedDateTime fecha = ZonedDateTime.now(ZoneId.systemDefault());
		return fecha.withZoneSameInstant(ZoneId.of(Constantes.TIME_ZONE)).toLocalDateTime();
	}

	public static String getCurrentDateTimeString() {
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constantes.FORMATO_FECHA_LARGA_SERVER);
		return now.format(formatter);
	}

	@SuppressWarnings("deprecation")
	public static void toUpperCase(Object request) {
		try {
			for (java.lang.reflect.Field field : request.getClass().getDeclaredFields()) {
				if (field.getType().equals(String.class)) {
					if (!field.isAccessible())
						field.setAccessible(true);
					if (field.get(request) != null && !((String) field.get(request)).trim().equals(Constantes.EMPTY)) {
						field.set(request, ((String) field.get(request)).toUpperCase());
					}
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
