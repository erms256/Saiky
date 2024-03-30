
package com.mycompany.saiky;

 /*
 * @author Erwin
 */

//import org.geotools.api.data.DataStore;
//import org.geotools.api.data.DataStoreFinder;
//import org.geotools.data.simple.SimpleFeatureCollection;
//import org.geotools.api.data.SimpleFeatureSource;
//import org.geotools.feature.FeatureIterator;

import org.geotools.api.feature.simple.SimpleFeature;
//import org.opengis.geometry.Geometry;
import org.locationtech.jts.geom.Polygon;
//import org.locationtech.jts.triangulate.VoronoiDiagramBuilder;


import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.shapefile.ShapefileDataStoreFactory;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;


//import org.opengis.feature.simple.SimpleFeature;
import org.geotools.api.feature.simple.SimpleFeatureType;

//import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
//import java.io.IOException;
//import java.nio.charset.Charset;
import java.util.ArrayList;


import org.geotools.geometry.jts.JTSFactoryFinder;
import org.locationtech.jts.geom.*;

//import java.io.*;
//import java.net.*;

import java.util.List;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

//import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
//import com.google.api.client.http.ByteArrayContent;
//import com.google.api.client.http.GenericUrl;
//import com.google.api.client.http.HttpRequest;
//import com.google.api.client.http.HttpResponse;
//import com.google.api.client.http.HttpTransport;
//import com.google.api.client.http.javanet.NetHttpTransport;
//import com.google.api.client.json.JsonFactory;
////import com.google.api.client.json.jackson2.JacksonFactory;
//import java.util.Collections;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import com.google.auth.oauth2.GoogleCredentials;
//import com.google.auth.http.HttpCredentialsAdapter;
//import com.google.auth.http.HttpTransportFactory;
//import com.google.api.client.http.HttpTransport;
//import com.google.api.client.http.javanet.NetHttpTransport;
//import com.google.api.client.http.HttpRequest;
//import com.google.api.client.http.HttpRequestFactory;
//import com.google.api.client.http.HttpRequestInitializer;
//import com.google.api.client.http.HttpResponse;
//import com.google.api.client.http.HttpResponseException;
//import com.google.auth.oauth2.ServiceAccountCredentials;
//import com.google.common.collect.ImmutableList;
//import com.google.api.services.storage.StorageScopes;
//import com.google.common.collect.Lists;
//import java.util.Arrays;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

//import com.google.api.services.earthengine.v1.EarthEngine;
//import com.google.api.services.earthengine.v1.model.Image;
//import com.google.api.services.earthengine.v1.model.ImageCollection;
//import com.google.api.services.earthengine.v1.model.GeeRawMap;
//import com.google.api.services.earthengine.v1.model.Transform;


public class Saiky {
    String project = "erwms256gentri";
    
    
        public static void main(String[] args) throws FileNotFoundException, IOException {
            
            
            ArrayList<Cuadrado> cuadrados = new ArrayList<>();   
            String apiKey = "Colocar APIKey";
            int zoom = 19;

            for(int k = 0; k< 50;k++){
                String xd = "C:\\Users\\Erwin\\Desktop\\bv3\\id_"+(k+1)+".shp";
            Poligono poli = new Poligono();     
            try {
               poli = readShapefile(xd);
            } catch (IOException e) {
                e.printStackTrace();
            }

            ArrayList<Cuadrado> rectangles = getRectanglesInsidePolygon(poli.getCoordenadas());
            
            for(int i = 0; i< rectangles.size();i++){
                //rectangles.get(i).setGentrification(poli.getGentrification());
                try {

                        String pathToCredentialsJson = "C:\\Users\\Erwin\\Desktop\\IA Gentrificacion\\erwms256gentri-bc80af1ad4cd.json";              
                        double x = encontrarCentroXy(rectangles.get(i).getCoordenadas().get(0), rectangles.get(i).getCoordenadas().get(1), rectangles.get(i).getCoordenadas().get(2), rectangles.get(i).getCoordenadas().get(3)).getX();
                        double y = encontrarCentroXy(rectangles.get(i).getCoordenadas().get(0), rectangles.get(i).getCoordenadas().get(1), rectangles.get(i).getCoordenadas().get(2), rectangles.get(i).getCoordenadas().get(3)).getY();
                      //  String region = "{\"type\":\"Point\",\"coordinates\":["+x+","+y+"]}"; // Coordenada central en formato GeoJSON
                      //  byte[] imageBytes = getImageFromGoogleEarthAPI(pathToCredentialsJson, x,y);
                      byte[] imageBytes =  getMapImageBytes(y, x, zoom, 300, 300, apiKey);
                      
                      imageBytes = cropImage(imageBytes);
                  // String imageUrl = crearURLImagen(apiKey,encontrarCentroXy(rectangles.get(i).getCoordenadas().get(0), rectangles.get(i).getCoordenadas().get(1), rectangles.get(i).getCoordenadas().get(2), rectangles.get(i).getCoordenadas().get(3)).getY() , encontrarCentroXy(rectangles.get(i).getCoordenadas().get(0), rectangles.get(i).getCoordenadas().get(1), rectangles.get(i).getCoordenadas().get(2), rectangles.get(i).getCoordenadas().get(3)).getX(), zoom);
                   // byte[] imageBytes = obtenerImagenBytes(imageUrl);
                    int s = (int) poli.getGentrification();
                    guardarImagenEnCarpeta(s, ""+i, imageBytes);
                    System.out.println("Imagen guardada correctamente en la carpeta especificada.");
                } catch (IOException e) {
                    e.printStackTrace();
            }

            }
            }
      //String pathToCredentialsJson = "C:\\path\\to\\your\\credentials.json";
        }
        
    public static byte[] getMapImageBytes(double latitud, double longitud, int zoom, int width, int height, String apiKey) throws IOException {
        // Construir la URL de la imagen estática
        String imageUrl = "https://maps.googleapis.com/maps/api/staticmap?"
                + "center=" + latitud + "," + longitud
                + "&zoom=" + zoom
                + "&size=" + width + "x" + height
                + "&scale=" + "1"
                + "&maptype=" + "satellite"
                + "&key=" + apiKey;

        // Realizar la solicitud HTTP para obtener la imagen
        URL url = new URL(imageUrl);
        try (InputStream in = url.openStream(); ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesRead);
            }
            return baos.toByteArray();
        }
    }
        
        
       
        
        public static byte[] cropImage(byte[] originalImageBytes) throws IOException {
        // Convertir los bytes en un BufferedImage
        ByteArrayInputStream inputStream = new ByteArrayInputStream(originalImageBytes);
        BufferedImage originalImage = ImageIO.read(inputStream);

        // Obtener las dimensiones originales de la imagen
        int originalWidth = originalImage.getWidth();
        int originalHeight = originalImage.getHeight();

        // Calcular las nuevas dimensiones después del recorte de 50 píxeles de cada lado
        int newWidth = originalWidth - 100; // 50 píxeles a la izquierda y 50 píxeles a la derecha
        int newHeight = originalHeight - 100; // 50 píxeles arriba y 50 píxeles abajo

        // Verificar si las nuevas dimensiones son válidas
        if (newWidth <= 0 || newHeight <= 0) {
            throw new IllegalArgumentException("Las nuevas dimensiones después del recorte no son válidas.");
        }

        // Obtener la región recortada utilizando getSubimage
        BufferedImage croppedImage = originalImage.getSubimage(50, 50, newWidth, newHeight);

        // Convertir la imagen recortada a bytes
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(croppedImage, "png", outputStream);

        return outputStream.toByteArray();
    }
        
        
    
    
    
    
    
    
    
    public static Poligono readShapefile(String filePath) throws IOException {
        File file = new File(filePath);
        Poligono poli;
        ArrayList<Coordinate> coordenadas = new ArrayList<>();
        int count = 1;
        String id = "";
        long gentrification = 0;
         
        
        
        // Configurar el acceso al archivo Shapefile
        ShapefileDataStoreFactory dataStoreFactory = new ShapefileDataStoreFactory();
        ShapefileDataStore dataStore = (ShapefileDataStore) dataStoreFactory.createDataStore(file.toURI().toURL());

        // Obtener información sobre el archivo Shapefile
        SimpleFeatureType featureType = dataStore.getSchema();
        String typeName = featureType.getTypeName();

        // Leer atributos y geometrías
        SimpleFeatureCollection featureCollection = dataStore.getFeatureSource(typeName).getFeatures();
        SimpleFeatureIterator iterator = featureCollection.features();

        while (iterator.hasNext()) {
            SimpleFeature feature = iterator.next();
           
            // Acceder a los atributos
            String idd = feature.getAttribute("id").toString();
            long gentrificationn = (long) feature.getAttribute("Gentrifica");
            double xCoordinate = (double) feature.getAttribute("x");
            double yCoordinate = (double) feature.getAttribute("y");
            
             if(count == 1){
                id = idd;
                gentrification = gentrificationn;
                count = 2; 
             }
             coordenadas.add(new Coordinate(xCoordinate, yCoordinate));
            
            
            // Realizar acciones con los atributos obtenidos
            System.out.println("ID: " + id + ", Gentrification: " + gentrification +
                    ", X Coordinate: " + xCoordinate + ", Y Coordinate: " + yCoordinate);
        }

        coordenadas.add(new Coordinate(coordenadas.get(0).getX(),coordenadas.get(0).getY()));
        
                 System.out.println("ID: " +  coordenadas.get(coordenadas.size()-1).x+"  " +coordenadas.get(coordenadas.size()-1).y);
        poli = new Poligono(id, gentrification, coordenadas);
        // Cerrar iterador y datastore
        iterator.close();
        dataStore.dispose();
        
        return poli;
    }
    
    
    
    public static ArrayList<Cuadrado> getRectanglesInsidePolygon(ArrayList<Coordinate> vertices) {
        // Obtener la fábrica de geometría de GeoTools
        GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();

        // Crear un polígono a partir de los vértices recibidos como argumento
        Coordinate[] coordinates = vertices.toArray(new Coordinate[0]);
        LinearRing shell = geometryFactory.createLinearRing(coordinates);
        Polygon polygon = geometryFactory.createPolygon(shell, null);

        // Obtener el límite del área del polígono
        Envelope envelope = polygon.getEnvelopeInternal();

        List<Polygon> rectanglesInside = new ArrayList<>();
         ArrayList<Cuadrado> cuadrados = new ArrayList<>();   
         
int count = 1;
        // Generar rectángulos dentro del área del polígono
        System.out.println("min"+envelope.getMinX() +"max "+envelope.getMaxX());
        for (double x = envelope.getMinX(); x <= envelope.getMaxX(); x += 0.00143) {
           
            for (double y = envelope.getMinY(); y <= envelope.getMaxY(); y += 0.00143) {
          
                System.out.println(count);
                count++;
                // Crear un punto para verificar si está dentro del polígono
                Point point = geometryFactory.createPoint(new Coordinate(x, y));
                
                // Verificar si el punto está dentro del polígono
                if (polygon.contains(point)) {
                    // Crear un rectángulo alrededor del punto
                    Coordinate[] rectangleCoords = {
                            new Coordinate(x, y),
                            new Coordinate(x + 0.00143, y),
                            new Coordinate(x + 0.00143, y + 0.00143),
                            new Coordinate(x, y + 0.00143),
                            new Coordinate(x, y)
                    };
                    ArrayList<Coordinate> coordinadas  = new ArrayList<>();
                    coordinadas.add(new Coordinate(x, y));
                    coordinadas.add(new Coordinate(x + 0.00143, y));
                    coordinadas.add(new Coordinate(x + 0.00143, y + 0.00143));
                    coordinadas.add(new Coordinate(x, y + 0.00143));
                    
                    cuadrados.add(new Cuadrado(coordinadas));
                    LinearRing rectangleRing = geometryFactory.createLinearRing(rectangleCoords);
                    Polygon rectangle = geometryFactory.createPolygon(rectangleRing, null);
                    
                    // Verificar si el rectángulo está completamente dentro del polígono
                    //if (polygon.covers(rectangle)) {
                        rectanglesInside.add(rectangle);
                    //}
                }
            }
        }
        System.out.println(rectanglesInside.size());

        return cuadrados;
    }
   
    
    
    public static double ajustarCincoDecimales(double numero) {
        // Redondear el número a cinco decimales
        double numeroRedondeado = Math.round(numero * 100000.0) / 100000.0;
        return numeroRedondeado;
    }
        
    
   public static Coordinate encontrarCentroXy(Coordinate p1, Coordinate p2, Coordinate p3, Coordinate p4){
       Coordinate centro = new Coordinate();
       centro.setX(ajustarCincoDecimales(encontrarCentroX(p1.getX(), p2.getX(), p3.getX(), p4.getX())));
       centro.setY(ajustarCincoDecimales(encontrarCentroX(p1.getY(), p2.getY(), p3.getY(), p4.getY())));
       return centro;
   }
        
    public static double encontrarCentroX(double x1, double x2, double x3, double x4) {
        double minX = Math.min(Math.min(x1, x2), Math.min(x3, x4));
        double maxX = Math.max(Math.max(x1, x2), Math.max(x3, x4));
        return (minX + maxX) / 2.0;
    }    
        
         public static double encontrarCentroY(double y1, double y2, double y3, double y4) {
        double minY = Math.min(Math.min(y1, y2), Math.min(y3, y4));
        double maxY = Math.max(Math.max(y1, y2), Math.max(y3, y4));
        return (minY + maxY) / 2.0;
    }
         
public static void guardarImagenEnCarpeta(int gentrifica, String fileName, byte[] imageBytes) throws IOException {
        String folderPath;
        switch (gentrifica) {
            case -3:
                folderPath = "C:\\Users\\Erwin\\Desktop\\proyectoGentri\\data\\entrenamiento\\menos_tres";
                break;
            case -2:
                folderPath = "C:\\Users\\Erwin\\Desktop\\proyectoGentri\\data\\entrenamiento\\menos_dos";
                break;
            case -1:
                folderPath = "C:\\Users\\Erwin\\Desktop\\proyectoGentri\\data\\entrenamiento\\menos_uno";
                break;
            case 0:
                folderPath = "C:\\Users\\Erwin\\Desktop\\proyectoGentri\\data\\entrenamiento\\cero";
                break;
            case 1:
                folderPath = "C:\\Users\\Erwin\\Desktop\\proyectoGentri\\data\\entrenamiento\\uno";
                break;
            case 2:
                folderPath = "C:\\Users\\Erwin\\Desktop\\proyectoGentri\\data\\entrenamiento\\dos";
                break;
            case 3:
                folderPath = "C:\\Users\\Erwin\\Desktop\\proyectoGentri\\data\\entrenamiento\\tres";
                break;
            default:
                throw new IllegalArgumentException("El número debe estar entre -3 y 3");
        }

        File folder = new File(folderPath);

        // Verificar si la carpeta existe (en este caso, no es necesario crearla)
        if (!folder.exists()) {
            throw new FileNotFoundException("La carpeta no existe en la ruta especificada.");
        }

        File imageFile = new File(folder, fileName + ".png"); 
        try (FileOutputStream outputStream = new FileOutputStream(imageFile)) {
            outputStream.write(imageBytes);
        }
    }  












































// public static byte[] obtenerImagenBytes(String imageUrl) throws IOException {
//        URL url = new URL(imageUrl);
//        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//        conn.setRequestMethod("GET");
//
//        int responseCode = conn.getResponseCode();
//        if (responseCode == HttpURLConnection.HTTP_OK) {
//            try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
//                StringBuilder response = new StringBuilder();
//                String inputLine;
//                while ((inputLine = in.readLine()) != null) {
//                    response.append(inputLine);
//                }
//                return response.toString().getBytes();
//            }
//        } else {
//            throw new IOException("Error al obtener la imagen. Código de respuesta: " + responseCode);
//        }
//    }
//    
//            public static String crearURLImagen(String apiKey, double lat, double lng, int zoom) {
//        return "https://maps.googleapis.com/maps/api/staticmap?" +
//                "center=" + lat + "," + lng +
//                "&zoom=" + zoom +
//                "&size=200x200" +
//                "&key=" + apiKey;
//    }
            
    
//        public static byte[] getImageFromGoogleEarthAPI(String pathToCredentialsJson, double x, double y) throws IOException {
//        List<String> scopes = Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform");
//        GoogleCredentials credentials = GoogleCredentials
//                .fromStream(new FileInputStream(pathToCredentialsJson))
//                .createScoped(scopes);
//
//        HttpTransport httpTransport = new NetHttpTransport();
//        HttpCredentialsAdapter credentialsAdapter = new HttpCredentialsAdapter(credentials);
//        HttpRequestFactory requestFactory = httpTransport.createRequestFactory(credentialsAdapter);

        
        


//// Escala en metros por píxel en ambas direcciones (X e Y)
//double escalaEnMetrosX = 0.795; // Escala en metros por píxel en la dirección X (longitud)
//double escalaEnMetrosY = 0.795; // Escala en metros por píxel en la dirección Y (latitud)
//
//// Conversión de escala en metros a escala en grados para la dirección X (longitud)
//double longitudGradoLongitud = 111.32 * Math.cos(Math.toRadians(x)); // Longitud de un grado de longitud en km
//double escalaEnGradosX = escalaEnMetrosX / longitudGradoLongitud; // Escala en grados en la dirección X
//
//// Conversión de escala en metros a escala en grados para la dirección Y (latitud)
//double latitudGradoLatitud = 111.32; // Latitud de un grado de latitud en km (aproximación general)
//double escalaEnGradosY = escalaEnMetrosY / latitudGradoLatitud; // Escala en grados en la dirección Y
        
//String body = "{" +
//        "\"fileFormat\": \"PNG\"," +
//        "\"bandIds\": [\"B4\", \"B3\", \"B2\"]," +
//        "\"grid\": {" +
//            "\"dimensions\": {" +
//                "\"width\": 200," +
//                "\"height\": 200" +
//            "}," +
//            "\"affineTransform\": {" +
//                "\"scaleX\": -0.04495437509323407," +
//                "\"shearX\": 0," +
//                "\"translateX\": -99.14089," +
//                "\"shearY\": 0," +
//                "\"scaleY\": 0.007141573841178585," +
//                "\"translateY\": 19.51979" +
//            "}," +
//            "\"crsCode\": \"EPSG:4326\"" +
//        "}," +
//        "\"visualizationOptions\": {" +
//            "\"ranges\": [{" +
//                "\"min\": 0," +
//                "\"max\": 3000" +
//            "}]" +
//        "}" +
//    "}";
//
//        
//       //"https://earthengine.googleapis.com/v1alpha/projects/earthengine-public/assets/COPERNICUS/S2:getPixels"
//        
//       HttpRequest request = requestFactory.buildPostRequest(
//                new GenericUrl("https://earthengine.googleapis.com/v1beta/projects/erwms256gentri/image:computePixels"),
//                ByteArrayContent.fromString(null, body)
//        );
//       
//
//        HttpResponse response = request.execute();
//        return response.parseAs(byte[].class);
//    }

//    public static byte[] getImageFromGoogleEarthAPI(String pathToCredentialsJson, String region) throws IOException {
//        // Inicializar el servicio de Earth Engine (debes agregar tu propia inicialización aquí)
//        EarthEngine earthEngineService = ...; // Inicializa tu servicio de Earth Engine
//
//        // Crear una proyección para EPSG:4326 (WGS84)
//        GeeRawMap projection = new GeeRawMap();
//        projection.setCrs("EPSG:4326"); // Establece la proyección a WGS84
//
//        // Simular la funcionalidad 'atScale(10)' en Java con una transformación
//        Transform transform = new Transform();
//        transform.setScaleX(10.0); // Escala X a 10 metros
//        transform.setScaleY(10.0); // Escala Y a 10 metros
//
//        // Agregar la transformación a la proyección
//        projection.setTransform(transform);
//
//        // Crear una solicitud para obtener la imagen con las configuraciones deseadas
//        ComputePixelsRequest imageRequest = new ComputePixelsRequest();
//        imageRequest.setExpression(projection); // Configurar la proyección en la solicitud
//        imageRequest.setFileFormat("PNG");
//        imageRequest.setBandIds(Arrays.asList("B4", "B3", "B2"));
//        imageRequest.setGrid(new PixelGrid()
//                .setDimensions(new GridDimensions().setWidth(640).setHeight(640))
//                .setAffineTransform(new AffineTransform()
//                        .setScaleX(transform.getScaleX())
//                        .setScaleY(transform.getScaleY())
//                        .setTranslateX(coords[0])
//                        .setTranslateY(coords[1])
//                        .setShearX(0)
//                        .setShearY(0)))
//                .setCrsCode("EPSG:4326")
//        );
//        imageRequest.setVisualizationOptions(new VisualizationOptions()
//                .setRanges(Collections.singletonList(new VisualizationRange()
//                        .setMin(0)
//                        .setMax(3000)))
//        );
//
//        // Ejecutar la solicitud para obtener la imagen como byte[]
//        ComputePixelsResponse imageResponse = earthEngineService
//                .projects()
//                .image()
//                .computePixels("projectId", imageRequest)
//                .execute();
//
//        // Procesar la respuesta y obtener la imagen como byte[]
//        // Asegúrate de manejar adecuadamente la estructura real de la respuesta proporcionada por la API de Earth Engine para Java
//        // En este ejemplo, se asume que la respuesta está en formato de bytes, se devuelve como tal
//        return imageResponse.getContent();
//    }
         
}

