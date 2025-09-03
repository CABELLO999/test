# DevSecOps Practices

Este proyecto aplica los siguientes principios y recomendaciones DevSecOps:

## Practicas implementadas
- **Integracion continua (CI):** Pipelines automaticos de build y pruebas.
- **Pruebas automatizadas:** Unitarias e integracion ejecutadas en CI.
- **Contenedores:** Docker y manifiestos Kubernetes para despliegue reproducible.
- **Seguridad basica:** Spring Security protege los endpoints y reenvio de header de autenticacion.
- **Observabilidad:** Logging de headers y health checks con Actuator.
- **Automatizacion de despliegue:** Manifiestos de Kubernetes y pipeline CI/CD.


## Consideración de actualización

**Recomendación:** Considera actualizar a Spring Boot 3.x para aprovechar al máximo las capacidades de Java 17, mejorar el rendimiento, la seguridad y la compatibilidad con nuevas herramientas de nube y Kubernetes. Spring Boot 3 es la versión recomendada para proyectos modernos y escalables.



Para aplicar los manifiestos de Kubernetes:

```sh
kubectl apply -f employee-service/k8s-employee-service.yaml
kubectl apply -f api-gateway/k8s-api-gateway.yaml
```

Verifica los recursos desplegados:

```sh
kubectl get deployments
kubectl get services
```

Para exponer el API Gateway fuera del cluster, crea un recurso Ingress o cambia el tipo de Service a LoadBalancer.
# Employee Microservices System

## Descripcion
Backend resiliente, seguro y escalable para la gestion de empleados, arquitectura de microservicios, DevSecOps, Spring Boot 2.7.x, Java 17.

### Servicios
- **employee-service**: CRUD de empleados, seguridad, logging, health check, Swagger, CI/CD, pruebas unitarias.
- **api-gateway**: Enrutamiento de peticiones, endpoint `/employees`.

## Modelo de datos (Empleado)
- Primer nombre
- Segundo nombre
- Apellido paterno
- Apellido materno
- Edad
- Sexo
- Fecha de nacimiento (dd-MM-yyyy)
- Puesto
- Fecha de alta (timestamp automatico)
- Estado activo/inactivo

## Endpoints principales
- `/employees` (CRUD)
- `/actuator/health` (health check)
- `/v3/api-docs`, `/swagger-ui` (documentacion)

## Seguridad
Spring Security con autenticacion basica.

## CI/CD
Pipeline basico con GitHub Actions.

## Pruebas
JUnit y pruebas de integracion.


## Ejecucion

### Opcion 1: Maven (local)
1. Compila cada microservicio con Maven:
   ```shell
   mvn clean package
   ```
2. Ejecuta los servicios:
   ```shell
   cd employee-service && mvn spring-boot:run
   cd api-gateway && mvn spring-boot:run
   ```

### Opcion 2: Docker
1. Construye los jars de ambos microservicios:
   ```shell
   cd employee-service && mvn clean package
   cd ../api-gateway && mvn clean package
   cd ..
   ```
2. Construye y levanta los contenedores:
   ```shell
   docker-compose up --build
   ```
3. Accede a los servicios:
   - Employee Service: http://localhost:8080/employees
   - API Gateway: http://localhost:8081/employees

## Documentacion
- Swagger disponible en `/swagger-ui`.
