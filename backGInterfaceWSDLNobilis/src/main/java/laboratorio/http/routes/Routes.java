package laboratorio.http.routes;

import java.util.Map;
import java.util.TreeMap;

import lombok.Getter;

public class Routes {

    public void init() {

        this.api = define(Uri.ROOT, "",
    // -------------------------------------------------------------------------------- auth 
        define(Uri.LOGIN, "/login"),
        define(Uri.REGISTER, "/register"), 
    // -------------------------------------------------------------------------------- admin
        define(Uri.ORIGENES_SIMPLIFICADOS, "/origenesSimplificados"),
        define(Uri.ORIGEN, "/origenIndividual"),
        define(Uri.MYORIGEN, "/myOrigen"),
        define(Uri.UPDATE, "/origenUpdate"),
        define(Uri.UPDATEMY, "/myOrigenUpdate"),
        define(Uri.CHANGE_STATE, "/origenChangeState"),

    // -------------------------------------------------------------------------------- ordenes
        define(Uri.ORDEN_INDIVIDUAL, "/orden"),
        define(Uri.ORDENES_EXCEL, "/ordenes"),
        define(Uri.DERIVACIONES, "/derivaciones"),
        define(Uri.ORDEN_DERIVACION, "/ordenDerivacion"),
        define(Uri.ACTUALIZACION_FLAG_DECARGA_ORDEN, "/actualizarFlagDescargaOrden"),

    // -------------------------------------------------------------------------------- resultado

        define(Uri.RESULTADO, "/resultadoByOrden"),
        
    // -------------------------------------------------------------------------------- templates   
        
        define(Uri.PDF_TEMPLATE_INSTRUCTIVO_LOAD, "/loadPDFInstructive"),
        define(Uri.EXCEL_TEMPLATE_DERIVACION_LOTE_LOAD, "/loadExcelTemplate"),
        define(Uri.PDF_TEMPLATE_INSTRUCTIVO_GET, "/getPDFInstructive"),
        define(Uri.EXCEL_TEMPLATE_DERIVACION_LOTE_GET, "/getExcelTemplate")); 
        
        
        
        paths = api.branches();
    }

    private Route define(Uri uri, String path, Route... children) {
        return new Route(uri, path, children);
    }

    private Route api;
    @Getter private Map<Uri, String> paths = new TreeMap<>();

    private static Routes instance;

    public static Routes getInstance() {
        if (instance == null) {
            instance = new Routes();
            instance.init();
        }

        return instance;
    }
}
