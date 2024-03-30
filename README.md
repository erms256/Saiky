<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>README - Saiky</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            line-height: 1.6;
            color: #333;
        }
        .container {
            max-width: 800px;
            margin: auto;
            padding: 20px;
        }
        h1, h2 {
            color: #333;
        }
        p {
            margin-bottom: 15px;
        }
        ul {
            list-style-type: none;
            padding: 0;
        }
        ul li {
            margin-bottom: 10px;
        }
        ul li:before {
            content: '•';
            color: #007bff;
            display: inline-block;
            width: 1em;
            margin-left: -1em;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Proyecto Saiky</h1>
        <p><strong>Descripción:</strong> Saiky es un programa desarrollado para la solicitud y ordenamiento de datos para entrenamiento de la red neuronal "Gentrificación CNN Prototype". Su función principal es extraer datos de archivos en formato Shape-file y procesarlos para realizar solicitudes a la API de Google Maps y obtener imágenes satelitales. Este programa se ha desarrollado en Java y utiliza la librería GeoTools y la API de Google Maps para llevar a cabo estas tareas.</p>
        
        <h2>Tecnologías utilizadas:</h2>
        <ul>
            <li>Lenguaje de programación: Java</li>
            <li>Librerías/Frameworks:</li>
            <ul>
                <li>GeoTools: Utilizada para la manipulación de datos geoespaciales, incluida la extracción de datos de archivos en formato Shape-file.</li>
                <li>API de Google Maps: Utilizada para realizar solicitudes y obtener imágenes satelitales para áreas específicas.</li>
            </ul>
        </ul>
        
        <h2>Funcionalidad del proyecto:</h2>
        <p>Saiky automatiza el proceso de extracción de datos geoespaciales de archivos en formato Shape-file y la solicitud de imágenes satelitales a través de la API de Google Maps. Estas imágenes satelitales se utilizan luego como datos de entrada para entrenar la red neuronal "Gentrificación CNN Prototype". El programa proporciona una interfaz para especificar las áreas de interés y los parámetros de solicitud, y luego maneja el proceso de solicitud y ordenamiento de los datos para su uso en el entrenamiento de la red neuronal.</p>
    </div>
</body>
</html>
