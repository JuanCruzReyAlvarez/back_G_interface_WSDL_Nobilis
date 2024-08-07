package laboratorio.http.exceptions;

public class MessageException {

    //GLOBALES:
    public static final String FIELD_NAME = "Error";
    public static final String TIME_OUT_NOBILIS = "El servicio Nobilis no respondio. TIME OUT.";

    //LOGIN CONTROLLER:
    public static final String UNAUTHORIZED_MESSAGE = "Usuario o Password invalido";

    //REGISTER CONTROLLER:
    public static final String UPDATE_STATUS_OK = "Origen actualizado con exito";
    public static final String DEACTIVATE_STATUS_OK = "Cambio de estado del origen con exito";

    //ORIGEN CONTROLLER:
    public static final String ORDEN_INDIVIDUAL_CREATED = "La orden se creo con exito. Estado: EN_PROCESO";
    public static final String ORDEN_INDIVIDUAL_FAIL = "La orden fallo. Estado: ERROR";
    public static final String FALLO_CARGA_PACIENTE_INDIVIDUAL_NOBILIS = "Fallo el Servicio de Carga de Paciente en Nobilis. Estado: ERROR, El paciente y su orden no fueron cargados";
    public static final String FALLO_CARGA_ORDEN_INDIVIDUAL_NOBILIS = "Fallo el Servicio de Carga de Orden en Nobilis. Estado: ERROR, El paciente y su orden no fueron cargados"; 

    //RESULTADO CONTROLLER:
    public static final String FALLO_PEDIDO_RESULTADO = "Fallo el pedido de resultado del lado de NOBILIS";
    public static final String FALLO_LOGICA_PEDIDO_RESULTADO = "Fallo la logica interna en el pedido de resultado o FALLO_PEDIDO_RESULTADO del lado de Nobilis. Pedir Soporte.";

    //TEMPLATE CONTROLLER:
    public static final String ARCHIVO_ENVIADO_INCORRECTAMENTE = "Los bytes del archivo fueron enviados incorrectamente";

    //REGISTER SERVICE:

    public static final String USUARIO_REGISTRADO = "El usuario fue Registrado correctamente.";
    public static final String ORIGEN_REGISTRADO = "El origen fue Registrado correctamente.";
    public static final String USUARIO_REPETIDO = "El usuario ya fue Registrado anteriormente.";
    public static final String ORIGEN_REPETIDO = "El Origen ya fue Registrado anteriormente.";
    public static final String RAZON_SOCIAL_REPETIDA = "El Origen ya fue Registrado anteriormente.";
    public static final String DESACTIVACION_INCORRECTA = "No se encontro el origen o no pudo ser desactivado";

    //ORIGEN SERVICE:
    public static final String WITHOUT_ORIGENES = "No hay Origenes cargados en la Base de Datos";
    public static final String WITHOUT_ORIGEN = "No se encuentra el Origen cargado en la Base de Datos";
    public static final String SERVICE_ORIGEN_FAIL = "Fallo el servicio de origenes";

    //ORDENES SERVICE
    public static final String ERROR_GUARDANDO_ORDENES = "Error persistiendo ordenes en el sistema, Problema de TimeOut o Datos Incorrectos. Pedir Soporte."; // Puede ser problema de datos y por eso falla el backend, esta excepcion la activa el timeout o alguna falla del backend q esta dentro de esta excepcion.
    public static final String ERROR_ELIMINANDO_ORDENES = "Error eliminando ordenes";
    public static final String ERROR_GUARDANDO_ORDEN_INDIVIDUAL = "Error persistiendo orden";
    public static final String ERROR_GUARDANDO_PACIENTES = "Error validando y persisitendo pacientes";
    public static final String ERROR_GUARDANDO_ESTUDIOS = "Error validando y persisitendo estudios";
    public static final String ERROR_OBTENIENDO_ORDENES_DERIVACION = "Error obteniendo ordenes a partir de una derivacion";
    public static final String ERROR_ORDEN_GENERO = "Error en la sintaxis esperada del Genero"; 
    public static final String ERROR_ORDEN_URGENCIA = "Error en la sintaxis esperada de la urgencia de la Orden";  
    public static final String ERROR_EN_EL_FORMATO_DE_LA_FECHA_DEL_EXCEL = "La fecha de Nacimiento de un paciente en el Excel, no cumple con el formato: DD/MM/AAAA.";
    public static final String ERROR_EN_EL_CORRECTOR_AUTOMATICO_DE_FECHA = "El corrector automatico de fechas fallo.";
    public static final String ERROR_ACTUALIZANDO_FLAGS = "Error actualizando Flags de descarga en la Base de Datos.";
    public static final String ERROR_SINTAXIS_ID_ORDENES = "La Sintaxis de los ID de las ordenes enviados es incorrecta.";
    public static final String ERROR_CODIGO_DE_CARGA_DE_PACIENTE_INCORRECTO = "El codigo interno de Nobilis para la carga de paciente no fue exitoso, se eleimina la derivacion completa para mantener derivacion"; 
    //DERIVACION SERVICE
    public static final String ERROR_OBTENIENDO_DERIVACIONES = "Error obteniendo derivaciones";
    public static final String ERROR_ELIMINANDO_DERIVACION = "Error Eliminando derivacion";
    public static final String ERROR_OBTENIENDO_DERIVACION = "Error obtenienod derivacion por su nombre";

    //TOKEN SERVICE
    public static final String TOKEN_EXPIRADO = "Token expirado";
    public static final String TOKEN_INVALIDO = "Token inválido"; //o firma no válida
    public static final String NO_ACCESS = "No tiene acceso para consumir este servicio";
    public static final String NO_ACCESS_USERNAME = "Su usuario no esta habilitado en el Sistema";
    public static final String ERROR_ENCRIPTACION_EXTERNA = "No pudo encriptar la informacion";
    public static final String ERROR_ENCRIPTACION_INTERNA = "No pudo encriptar el token";
    public static final String ERROR_DESENCRIPCTACION_INTERNA = "No se pudo desencriptar el token";

    //RESULTADO SERVICE
    public static final String ERROR_CREANDO_RESULTADO = "El sevicio de Reusltado no pudo crear la Orden";

    //TEMPLATE SERVICE
    public static final String FALLO_LOGICA_CARGA_DE_TEMPLATE = "Fallo el guardado o actualizacion del archivo.";
    public static final String FALLO_PEDIDO_ARCHIVO_EXCEL = "Fallo el pedido a la BD del archivo EXCEL.";
    public static final String FALLO_PEDIDO_ARCHIVO_PDF = "Fallo el pedido a la BD del archivo PDF.";

// ----------------------------------------- VALIDATORS:

    public static final String REQUIRED_MESSAGE = "The %s field is required";
    
    public static final String USERNAME_FIELD_NAME = "username";
    public static final String CUIT_FIELD_NAME = "Cuit";
    public static final String ROL_FIELD_NAME = "Rol";
    public static final String CP_FIELD_NAME = "CP";
    public static final String TOKEN_FIELD_NAME = "token";
    public static final String PASSWORD_FIELD_NAME = "Password";
    public static final String TELEFONO_FIELD_NAME = "Telefono";
    public static final String DOMICILIO_MESSAGE = "Domicilio";
    public static final String LOCALIDAD_FIELD_NAME = "Localidad";
    public static final String PROVINCIA_FIELD_NAME = "Provincia";
    public static final String RAZON_SOCIAL_MESSAGE = "Razon Social";
    public static final String ID_ORIGEN_FIELD_NAME = "ID_origen";
    public static final String MAIL_FIELD_NAME = "Mail";
    public static final String RAZON_SOCIAL_FIELD_NAME = "Razon Social";
    public static final String USERNAME_DE_USUARIO_MESSAGE = "Username" ;
    public static final String ESTADO_FIELD_NAME = "Estado";
    public static final String NOMBRE_PACIENTE_FIELD_NAME = "Nombre del Paciente";
    public static final String ID_ORDEN_FIELD_NAME  = "iD de la Orden";
    public static final String APELLIDO_PACIENTE_FIELD_NAME = "Apellido del Paciente";
    public static final String DNI_PACIENTE_FIELD_NAME = "DNI del Paciente";
    public static final String GENERO_PACIENTE_FIELD_NAME = "Genero del Paciente";
    public static final String URGENTE_FIELD_NAME = "Urgencia de la Orden"; 
    public static final String ESTUDIOS_FIELD_NAME = "Estudios del Paciente";
    public static final String FECHA_NACIMIENTO_FIELD_NAME = "Fecha de Nacimiento del Paciente";
    public static final String EXCEL_BYTES = "Bytes del Excel";
    public static final String NOMBRE_DERIVACION_FIELD_NAME = "Nombre de la Derivacion";
}
