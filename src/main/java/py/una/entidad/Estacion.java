package py.una.entidad;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Estacion {

    public Long id_estacion;
    public String ciudad;
    public Long porcentaje_humedad;
    public Long temperatura;
    public Long velocidad_viento;
    public String fecha;
    public String hora;

    public Estacion(Long id_estacion, String ciudad, Long porcentaje_humedad, Long temperatura, Long velocidad_viento,
            String fecha, String hora) {
        this.id_estacion = id_estacion;
        this.ciudad = ciudad;
        this.porcentaje_humedad = porcentaje_humedad;
        this.temperatura = temperatura;
        this.velocidad_viento = velocidad_viento;
        this.fecha = fecha;
        this.hora = hora;
    }

    public static String objToString(Estacion e) {
        final JSONObject obj = new JSONObject();
        obj.put("id_estacion", e.id_estacion);
        obj.put("ciudad", e.ciudad);
        obj.put("porcentaje_humedad", e.porcentaje_humedad);
        obj.put("temperatura", e.temperatura);
        obj.put("velocidad_viento", e.velocidad_viento);
        obj.put("fecha", e.fecha);
        obj.put("hora", e.hora);
        obj.put("tipo", "datosEstacion");
        return obj.toJSONString();
    }

    public static Estacion strToObj(String str) throws ParseException {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(str.trim());
        JSONObject jsonObject = (JSONObject) obj;

        Long id_estacion=(Long) jsonObject.get("id_estacion");
        String ciudad=(String) jsonObject.get("ciudad");
        Long porcentaje_humedad=(Long) jsonObject.get("porcentaje_humedad");
        Long temperatura=(Long) jsonObject.get("temperatura");
        Long velocidad_viento=(Long) jsonObject.get("velocidad_viento");
        String fecha=(String) jsonObject.get("fecha");
        String hora=(String) jsonObject.get("hora");

        Estacion e= new Estacion(id_estacion, ciudad, porcentaje_humedad, temperatura, velocidad_viento, fecha, hora);
        return e;

    }
}