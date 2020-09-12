package py.una.entidad;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class CotizacionJSON {
    public static String objToString(Cotizacion c) {
        final JSONObject obj = new JSONObject();
        obj.put("tipo", c.tipo);
        obj.put("compra", c.compra);
        obj.put("venta", c.venta);
        return obj.toJSONString();
    }

    public static Cotizacion strToObj(String str) throws ParseException {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(str.trim());
        JSONObject jsonObject = (JSONObject) obj;
        
        Long venta = (Long) jsonObject.get("venta");
        Long compra = (Long) jsonObject.get("compra");
        String tipo = (String) jsonObject.get("tipo");
        
        Cotizacion c = new Cotizacion(tipo,compra,venta);
        return c;

    }
}