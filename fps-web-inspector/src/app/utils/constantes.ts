/*************************** FUENTE ORIGEN ***********************/
export const SOURCE_INSPECTOR = 'inspector'

/************************ STATUS MULTIMEDIA **********************/
export const EMPTY = 'empty';
export const UPLOADED = 'uploaded';
export const APPROVED = 'approved';
export const REJECTED = 'rejected';

/**************** STATUS INSPECCION - FIREBASE DB ****************/
export const ON_HOLD = 'onHold';
export const AVAILABLE = 'available';
export const COMPLETE = 'complete';

/**************** STATUS INSPECCION - POSTGRES DB ****************/
export const DB_ON_HOLD = 'ESIN001';
export const DB_AVAILABLE = 'ESIN002';
export const DB_COMPLETE = 'ESIN003';

/******************* COLECCIONES - FIREBASE DB *******************/
export const COLLECTION_INSPECTION = 'inspections';
export const COLLECTION_BRANDS= 'brands';
export const COLLECTION_CHATS = 'chats';
export const COLLECTION_PHOTOS = 'photos';
export const COLLECTION_VIDEOS = 'videos';
export const COLLECTION_NOTIFICATION = 'notification';

/******************* FORMATO FECHA *******************/
export const FORMATO_FECHA_NOTIFICACION = 'DD/MM/YYYY HH:mm';

/******************** PARAMETROS ORDENAMIENTO ********************/
export const ORD_ASC = 'asc';
export const ORD_DES = 'desc';

/******************** ESTADO FECHA INSPECCION ********************/
export const SCHEDULED = 'scheduled';
export const RESCHEDULED = 'rescheduled';

/************************ GENERAR LINKS **************************/
export const LINK_BASE = 'http://inspeccion.legall.com.pe/virtual?hash='

export const URL_PLAYSTORE = 'https://play.google.com/store/apps/details?id=pe.com.legall.inspeccion';
export const URL_APPSTORE= 'https://apps.apple.com/ec/app/youtube/id544007664'

/**************************** SEGURIDAD **************************/
export const TOKEN_NAME = 'access_token';
export const TOKEN_ID = 'token_id';

/************************ MENSAJES USUARIO ***********************/
export const TXT_NOTIF_OK = 'OK';
export const MSJ_BIENVENIDA = 'Bienvenido usuario: ';
export const TXT_FOTO_SUBIDA = 'El asegurado ha subido una nueva foto...'
export const MSJ_FOTO_RECHAZADA= 'Se ha rechazado la foto'
export const MSJ_REPROGRAMAR_ESPERA = 'Reprogramando inspecion.....';
export const MSJ_REPROGRAMAR_OK = 'Se ha Reprogramado la Inspeccion del asegurado: ';
export const MSJ_ACTIVAR_INSP_ESPERA = 'Activando la inspeccion, por favor espere....';
export const MSJ_ACTIVAR_INSP_OK = 'Se habilito la inspeccion del asegurado: '
export const ERROR_403_TITULO = 'Acceso Denegado';
export const ERERROR_403_DESCRIPCION = 'Lo sentimos no tiene los permisos suficientes.';
export const ERROR_500_TITULO = 'Error Interno';
export const ERROR_500_DESCRIPCION = 'Por favor contactese con el administrador de sistemas.';
export const ERROR_LOGIN_TITULO = 'Incorrecto';
export const ERROR_LOGIN_DESCRIPCION = 'Por favor revise su usuario o contraseña';
export const NUEVO_REGISTRO = 'Nuevo-Registro';
export const SIZE_ELEMENTS = 10;



