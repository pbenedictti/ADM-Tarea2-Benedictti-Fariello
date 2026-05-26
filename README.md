# APLICACIONES PARA DISPOSITIVOS MÓVILES  Tarea 2

Aplicación Android desarrollada como parte del curso de Desarrollo de Aplicaciones Móviles de la carrera Tecnólogo en Informática . 
## 1. Objetivo del proyecto
El objetivo principal es proporcionar una herramienta intuitiva para el descubrimiento de obras literarias, permitiendo búsquedas por título, autor o ISBN, y ofreciendo persistencia de datos para los libros de interés del usuario.

## 2. Descripción general
### Funcionalidades principales:
*   **Búsqueda avanzada:** Filtrado de libros por diversos criterios (Título, Autor, ISBN) consumiendo datos en tiempo real de OpenLibrary.
*   **Gestión de Favoritos:** Persistencia local de libros seleccionados mediante una base de datos interna.
*   **Visualización de detalles:** Listado de resultados con portadas, autores y años de publicación.
*   **Personalización:** Soporte para modo claro y oscuro.
*   **Notificaciones internas:** Uso de BroadcastReceivers para informar acciones al usuario.
*   **Componentes del sistema:** Implementación de Service para procesos en segundo plano y ContentProvider para exposición de datos.

## 3. Tecnologías utilizadas
*   **Lenguaje:** Kotlin 2.2.10
*   **Framework UI:** Jetpack Compose con Material Design 3
*   **Arquitectura:** MVVM (Model-View-ViewModel)
*   **Persistencia de datos:** Room Database 2.8.4
*   **Consumo de API:** Retrofit 3.0.0 con Gson Converter
*   **Carga de imágenes:** Coil 3.4.0
*   **Navegación:** Navigation Compose 2.9.8
*   **Inyección de Dependencias / Herramientas:** 
    *   Gradle 9.2.1 (AGP)
    *   KSP (Kotlin Symbol Processing)
    *   OkHttp Logging Interceptor
    *   AndroidX Core SplashScreen 1.0.1

## 4. Arquitectura del proyecto
El proyecto sigue el patrón de arquitectura **MVVM**, garantizando una separación clara de responsabilidades:

*   **`data/`**: Capa de datos.
    *   `remote/`: Definiciones de API (Retrofit) y DTOs para la comunicación con el servidor.
    *   `local/`: Configuración de Room (Entidades, DAO, Database) para el almacenamiento persistente.
    *   `repository/`: Clase mediadora que decide si obtener datos de la red o de la base de datos local.
*   **`ui/`**: Capa de presentación.
    *   `screens/`: Componibles que representan las pantallas completas de la app.
    *   `components/`: Elementos de UI reutilizables (BookItem, etc.).
    *   `theme/`: Definiciones de colores, tipografía y temas (Light/Dark).
    *   `BookViewModel.kt`: Centraliza la lógica de negocio y el estado de la UI.
*   **`components/`**: Integración con componentes fundamentales de Android.
    *   `BookService`: Servicio para loguear búsquedas.
    *   `BookProvider`: Proveedor de contenido para consultar favoritos desde otros procesos.
    *   `FavoriteReceiver`: Receptor de notificaciones personalizadas.

## 5. Clases y componentes principales
*   **`MainActivity.kt`**: Punto de entrada; configura el tema, `NavHost` y la barra de navegación inferior.
*   **`BookViewModel.kt`**: Gestiona el flujo de datos entre el repositorio y las pantallas. Mantiene estados reactivos usando `StateFlow`.
*   **`BookRepository.kt`**: Abstrae las fuentes de datos (API y DB) del resto de la aplicación.
*   **`OpenLibraryApi.kt`**: Define los endpoints para la búsqueda de libros.
*   **`BookDatabase.kt`**: Punto de acceso principal para la base de datos persistente de favoritos.
*   **`Screens.kt`**: Define la estructura de rutas y los iconos asociados a la navegación.

## 6. Flujo de navegación / funcionamiento
La navegación se gestiona mediante una `BottomNavigationBar` con las siguientes pantallas:

1.  **Splash Screen:** Pantalla de carga animada al iniciar la app.
2.  **Home (Búsqueda):** El usuario ingresa una consulta, selecciona el tipo de búsqueda (Título, Autor, ISBN) y presiona "Buscar".
3.  **Resultados:** Lista de libros encontrados. Cada item permite marcar/desmarcar como favorito.
4.  **Favoritos:** Listado de libros guardados localmente. Permite la eliminación de favoritos.
5.  **Configuración:** Permite activar/desactivar el Modo Oscuro y ver información sobre el proyecto.

## 7. Pasos para compilar y ejecutar
### Requisitos previos
*   Android Studio Ladybug (o superior).
*   JDK 11 o superior.
*   Conexión a Internet (para descargar dependencias y consultar la API).

### Instrucciones
1.  **Clonar el repositorio:**
    ```bash
    git clone git@github.com:pbenedictti/ADM-Tarea2-Benedictti-Fariello.git
    ```
2.  **Abrir el proyecto:** Iniciar Android Studio y seleccionar la carpeta del proyecto.
3.  **Sincronizar Gradle:** Presionar "Sync Project with Gradle Files" y esperar a que finalice.
4.  **Ejecutar:**
    *   Seleccionar un emulador o dispositivo físico con Android 8.0 (API 26) o superior.
    *   Presionar el botón **Run** (ícono de "Play").


## 8. Manual de usuario
1.  **Búsqueda:** En la pestaña principal, escribe el nombre de un libro (ej: "KING"). Asegúrate de seleccionar el criterio correcto en el desplegable antes de buscar.
2.  **Resultados:** Explora la lista. Toca el ícono del corazón para guardar un libro. Recibirás un aviso visual confirmando la acción.
3.  **Favoritos:** Accede desde el menú inferior para ver tu colección guardada, incluso sin conexión a internet.
4.  **Ajustes:** Ve a la pestaña de configuración para activar el Modo Oscuro si prefieres una interfaz con colores profundos.

## 9. Reportes de seguridad y calidad
El proyecto integra herramientas automatizadas para asegurar la integridad del código:

*   **OWASP Dependency Check:** Analiza las librerías utilizadas en busca de vulnerabilidades conocidas (CVEs).
    *   *Ubicación:* `InformesCalidad/dependency-check-SinErrores.html`
    *   *Generar:* Ejecutar `./gradlew dependencyCheckAnalyze` en la terminal.
*   **SonarQube / SonarCloud:** Evalúa la calidad del código, detectando "code smells", bugs potenciales y vulnerabilidades técnicas.
    *   *Ubicación:* `InformesCalidad/Informe SAST.pdf`.
    *   *Generar:* Ejecutar `./gradlew sonar`.

## 10. Estructura del repositorio
```text
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/misitioweb/admTarea2/
│   │   │   │   ├── components/       # Service, Provider, Receiver
│   │   │   │   ├── data/             # Capa de datos (Local, Remote, Repo)
│   │   │   │   ├── ui/               # Capa de UI (Screens, Theme, VM)
│   │   │   │   └── MainActivity.kt
│   │   │   └── res/                  # Recursos (Layouts, Drawables, Values)
│   ├── build.gradle.kts              # Configuración del módulo app
│   └── suppressions.xml              # Supresiones de falsos positivos en seguridad
├── gradle/
│   └── libs.versions.toml            # Gestión centralizada de versiones
├── build.gradle.kts                  # Configuración del proyecto raíz
└── README.md
```

## 11. Decisiones técnicas
*   **Material 3:** Se eligió para garantizar una estética moderna y aprovechar el sistema de colores dinámicos de Android.
*   **StateFlow:** Utilizado para manejar el estado de la UI de forma reactiva y segura frente a ciclos de vida.
*   **Retrofit:** Seleccionado por su robustez y facilidad para integrar interceptores de logs y manejo de errores.
*   **Corrutinas:** Implementadas para todas las tareas asíncronas (búsquedas y acceso a BD) para mantener la fluidez de la interfaz.

## 13. Autores
*   **Integrante 1:** Paola Benedictti
*   **Integrante 2:** Bibiana Fariello
*   **Curso:** ADM  
*   **Año:** 2026


