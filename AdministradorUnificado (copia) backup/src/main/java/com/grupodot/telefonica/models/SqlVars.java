package com.grupodot.telefonica.models;

import com.grupodot.telefonica.controllers.CambioDePlanController;
import com.grupodot.telefonica.controllers.Clic2CallController;
import com.grupodot.telefonica.controllers.EncuestasMovistarController;
import com.grupodot.telefonica.controllers.GenericController;
import com.grupodot.telefonica.controllers.MovistarPlayController;
import com.grupodot.telefonica.controllers.SuperAdminController;
import com.grupodot.telefonica.controllers.TarjetaPreferencialController;
 
public class SqlVars {

	public static GenericController assigntableJsName(String tableJs) {
		GenericController genCont = null;
		switch(tableJs) {
			case "formulario_tic":
			case "formulario_cliente_preferencial":
			case "formulario_tic_streaming":
			case "formulario_contacto_tienda":
			case "formulario_s9":
			case "formulario_tic_avianca":
			case "formulario_cliente_preferencial_test":
			case "formulario_factura_electronica":
			case "formulario_movistar_play":
			case "formulario_repetidor":
				genCont = new GenericController("formularios_telefonica", tableJs, "");
				break;
			case "pos_pos_formulario_tarifa":
				genCont = new Clic2CallController("tarifas", tableJs, "[\"id\",\"productos\",\"fecha\",\"telefono\",\"campania\",\"origen\",\"mail\",\"WS\"]");
				break;
			case "registros":
				genCont = new GenericController("104.198.142.65,pruebas-migracion", "movistar_champions", tableJs, "root", "Dot2018$","[\"fecha_registro\",\"numero_celular\",\"email\",\"servicio_mas_consumido\",\"valor_recarga_mensual\",\"estado\"]");
				break;
			case "formulario_cambio_plan_pre_pos":
				genCont = new CambioDePlanController("173.194.107.21", "movis25_tiendadb", "formulario_cambio_plan_pre_pos", "root", "root","[\"id\",\"fecha registro\",\"numero_celular\",\"email\",\"servicio_mas_consumido\",\"valor_recarga_mensual\"]");
				break;
			case "formulario_cambio_plan_pos_pos":
				genCont = new CambioDePlanController("173.194.107.21", "movis25_tiendadb", "formulario_cambio_plan_pos_pos", "root", "root","[\"id\",\"fecha registro\",\"numero_celular\",\"cedula\",\"email\"]");
				break;
			case "formulario_the_son_personas":
				genCont = new MovistarPlayController("formularios_telefonica", tableJs, "[\"Cedula\",\"NOMBRE\",\"Respuesta 1\",\"Respuesta 2\",\"Respuesta 3\",\"Respuesta 4\",\"Respuesta 5\",\"Respuesta 6\",\"Tiempo\"]");
				break;
			case "usuario":
				genCont = new SuperAdminController("formularios_telefonica", tableJs, "[\"ID\",\"NOMBRE\",\"USERNAME\",\"TIPO USUARIO\"]");
				break;
			case "usuario_rol":
				genCont = new SuperAdminController("formularios_telefonica", tableJs, "[\"ID\",\"NOMBRE\",\"Descripcion del ROL\",\"Descripcion\"]");
				break;
			case "rol":
				genCont = new SuperAdminController("formularios_telefonica", tableJs, "[\"ID\",\"Nombre\",\"Descripcion\"]");
				break;
			case "redes":
				genCont = new GenericController("formularios_telefonica", "formulario_redes", "");
				break;
			case "salidaweb":
				genCont = new GenericController("formularios_telefonica", "formulario_salida_web", "");
				break;
			case "encuestasatisfaccion":
				genCont = new GenericController("formularios_telefonica", "encuesta_satisfaccion", "");
				break;
			case "cdfpreferencial":
				genCont = new GenericController("formularios_telefonica", "formulario_cdf_preferencial", "");
				break;
			case "cartera":
				genCont = new GenericController("formularios_telefonica", "formulario_cartera", "");
				break;
			case "sl_formulario_empresa":
				genCont = new GenericController("movis25_movteldb", "sl_formulario_empresa", "");
				break;
			case "sl_formulario_individual":
				genCont = new GenericController("movis25_movteldb", "sl_formulario_individual", "");
				break;
			case "form_fact_electro_fijo":
				genCont = new GenericController("movis25_movteldb", "form_fac_electro_fijo", "");
				break;
			case"formulario_entrega_camiseta":
				genCont = new GenericController("formularios_telefonica", "formulario_entrega_camiseta", "[\"id\",\"fecha\",\"empresa\",\"nit\",\"representanteLegal\",\"documento\",\"celular\",\"fijo\",\"direccion\",\"direccionCaja5\",\"barrio\",\"deparatamento\",\"ciudad\",\"fechaCalculadaEntrega\",\"fechaEntrega\"]");
				break;
			case "prepagoCard":
				genCont = new TarjetaPreferencialController("formularios_telefonica", tableJs, "[\"cod\",\"nombre\",\"cedula\",\"celular\",\"fecha\"]");
				break;
			case "formulario_regulatorio":
				genCont = new GenericController("movis25_movteldb", "regulatorioformato", "");
				break;
			case "encuestas_movistar.SOPORTE_MOVIL":
			case "encuestas_movistar.SOPORTE_FIJO":
			case "encuestas_movistar.ATENCION_MASIVA":
			case "encuestas_movistar.SATISFACCION_PYMES":
			case "encuestas_movistar.SATISFACCION_CORPORACIONES":
				String[] tableSplit = tableJs.split("\\.");
				String nombreTabla = tableSplit[0];
				String tipoEncuesta = tableSplit[1];
				genCont = new EncuestasMovistarController("formularios_telefonica", nombreTabla, "[\"id\",\"número_asociado\",\"satisfacción\",\"tiempo\",\"resuelta\",\"amabilidad\",\"comentarios\",\"fecha\"]", tipoEncuesta);
				break;
			default:
				return null;
		}
		return genCont;
	}
}