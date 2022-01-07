package pe.farmaciasperuanas.legall.core.util;

public class Constantes {

	private Constantes() {}
	
	/************************** MENSAJES DE GUARDADO **************************/
	public static final String ESTADO_CREADO = "200";
	public static final String MENSAJE_GUARDADO_EXITO = "Guardado Correctamente";
	public static final String MENSAJE_GUARDADO_ERROR = "Ocurrio un error al guardar";
	public static final String MENSAJE_SAVE_ERROR = "Ocurrio un error al guardar, por favor revise los campos";
	public static final String MENSAJE_TRAMITE_DUPLICADO = "Tramite Duplicado, por favor ingrese otro Nro. Tramite";
	public static final String MENSAJE_TRAMITE_DUPLICADO_TECNICO = "No se puede repetir los valores del campo 'nro_tramite_compania_seguro'";
	public static final String ESTADO_OK = "OK";
	public static final String ESTADO_ERROR= "ERROR";
	
	/************************ ESTADO PARA LOS REGISTROS ***********************/
	public static final String ESTADO_INACTIVO = "0";
	public static final String ESTADO_ACTIVO = "1";

	/************************** DATOS PARAMETRIA *****************************/
	public static final String DOCUMENTO_DNI = "TIDO001";
	public static final String ID_CT_ESTADO_INSPECCION_PENDIENTE = "ESIN001";
	public static final String ID_CT_ESTADO_TRAMITE_PENDIENTE = "ESTR001";
	public static final String MOTIVO_INFORME_INSPECCION_PROGRAMADO = "TMII005";


	/******************* COLECCIONES FIREBASE *********************/
	public static final String COLECTION_NOTIFICATION = "notification";
	public static final String NUEVO_REGISTRO = "Nuevo-Registro";
	
	

	public static final String MSJ_VALIDAR_USUARIO= "Usuario no encontrado";
	public static final String USUARIO_DEFAULT= "Admin";

	public static final String MENSAJE_CODIGO_GENERADO = "Código Generado";
	public static final String MENSAJE_VERIFICACION_EXITO = "Verificacion Exitosa";
	public static final String MENSAJE_VERIFICACION_ERROR = "Error de Verificacion";

	public static final String MENSAJE_ACCESO_EXITO = "Acceso correcto";
	public static final String API_MENSAJE_SAVE_ERROR = "No se puede registrar";
}
