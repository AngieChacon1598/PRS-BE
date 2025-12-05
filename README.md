# Configuration Service

Microservicio de configuración disponible como imagen Docker.


## 📦 Descargar la imagen

```bash
docker pull jossuetorres/vg-ms-configuration-service
```

## 🚀 Ejecutar el contenedor

```bash
docker run -d -p 5004:5004 --name configurationservice jossuetorres/vg-ms-configuration-service
```

- El servicio estará disponible en `http://localhost:5004` después de iniciar el contenedor