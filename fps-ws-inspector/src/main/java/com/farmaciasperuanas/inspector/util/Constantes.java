package com.farmaciasperuanas.inspector.util;

public class Constantes {
	
	private Constantes() {}

	/******************* ESTADOS FIREBASE *********************/
	public static final String ON_HOLD = "onHold";
	public static final String AVAILABLE = "available";
	public static final String COMPLETE = "complete";
	public static final String REPROGRAMADO = "scheduled";

	/******************* COLECCIONES FIREBASE *********************/
	public static final String COLECCION_INSPECCION = "inspections";

	/******************* ROLES DE BASE DE DATOS **********************/
	public static final String ROL_SUPER_USUARIO = "ROLE001";
	public static final String ROL_INSPECTOR = "ROLE002";

	/******************* ESTADO INSPECCION BASE DATOS ****************/
	public static final String ESTADO_EN_ESPERA = "ESIN001";
	public static final String INFORME_ANULADO_CORE_TABLE = "TMII001";
	public static final String INFORME_PROGRAMADO_CORE_TABLE = "TMII005";
	public static final String INFORME_ANULADO= "ANULADO";

	/******************* ZONA HORARIA ***********************/
	public static final String TIME_ZONE = "America/Lima";
	public static final String FORMATO_FECHA_LARGA_SERVER = "YYYY-MM-dd HH:mm:ss";
	public static final String EMPTY = "";


	public static final Integer PAGINA_DEFAULT = 0;
	public static final Integer ELEMENTOS_DEFAULT = 30;

}
