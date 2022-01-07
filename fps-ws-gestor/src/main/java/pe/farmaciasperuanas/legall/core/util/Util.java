package pe.farmaciasperuanas.legall.core.util;

public final class Util {
	
	private Util() {}

	public static boolean isEmptyWithTrim(String character) {
		return (character == null) || (character.trim().length() == 0);
	}

	public static boolean isNotNull(Object object) {
		return (object != null);
	}

}
