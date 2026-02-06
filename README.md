# 📍 Localizador - Aplicación Android con Google Maps

Una aplicación móvil Android que permite visualizar mapas interactivos, buscar direcciones y marcar ubicaciones utilizando Google Maps API.

## 📱 Características

- ✅ Visualización de mapas interactivos con Google Maps
- ✅ Búsqueda de direcciones y ubicaciones
- ✅ Marcadores personalizados al tocar el mapa
- ✅ Geocodificación inversa (obtener dirección desde coordenadas)
- ✅ Detección de ubicación actual del dispositivo
- ✅ Interfaz moderna con Jetpack Compose y Material Design 3

<img width="325" height="730" alt="Captura de pantalla 2026-02-06 203904" src="https://github.com/user-attachments/assets/e716fc0e-10c5-457b-8152-8024acff298c" />
Pantalla Principal

Video demostrativo 
https://youtube.com/shorts/usceHrI_L5k




## 🛠️ Tecnologías Utilizadas

- **Lenguaje:** Kotlin
- **Framework UI:** Jetpack Compose
- **Arquitectura:** Activity + Composables
- **Mapas:** Google Maps SDK para Android
- **Ubicación:** Google Play Services Location
- **Maps Compose:** Biblioteca oficial de Google Maps para Compose
- **Gradle:** Kotlin DSL

## 📋 Requisitos Previos

- Android Studio Hedgehog o superior
- JDK 17 o superior
- Android SDK API Level 24 (Android 7.0) como mínimo
- Google Play Services instalado en el dispositivo/emulador
- Cuenta de Google Cloud con Google Maps API habilitada

## 🔑 Configuración de Google Maps API

### 1. Crear proyecto en Google Cloud Console

1. Ve a [Google Cloud Console](https://console.cloud.google.com/)
2. Crea un nuevo proyecto o selecciona uno existente
3. Navega a **APIs & Services → Library**
4. Busca y habilita **Maps SDK for Android**

### 2. Generar API Key

1. Ve a **APIs & Services → Credentials**
2. Haz clic en **+ CREATE CREDENTIALS**
3. Selecciona **API Key**
4. Copia la API Key generada
5. (Opcional) Restringe la key a aplicaciones Android

### 3. Configurar la API Key en el proyecto

Abre el archivo `app/src/main/AndroidManifest.xml` y reemplaza `TU_API_KEY_AQUI` con tu API Key real:

```xml
<meta-data
    android:name="com.google.android.geo.API_KEY"
    android:value="AIzaSy..." />
```

⚠️ **IMPORTANTE:** No subas tu API Key real a repositorios públicos. Considera usar variables de entorno o archivos locales no versionados.

## 📦 Instalación y Ejecución

### 1. Clonar el repositorio

```bash
git clone https://github.com/tu-usuario/localizador-android.git
cd localizador-android
```

### 2. Abrir en Android Studio

1. Abre Android Studio
2. Selecciona **File → Open**
3. Navega a la carpeta del proyecto y selecciónala
4. Espera a que Gradle sincronice el proyecto

### 3. Configurar tu API Key

Edita `app/src/main/AndroidManifest.xml` y añade tu API Key de Google Maps.

### 4. Ejecutar en emulador o dispositivo físico

#### Opción A: Emulador

1. Ve a **Device Manager** (ícono de teléfono en la barra lateral)
2. Crea un nuevo dispositivo virtual:
   - Modelo: **Pixel 6** o superior
   - System Image: **API 33 (Tiramisu)** o superior con **Google Play**
3. Haz clic en el botón **Run** (▶️)

#### Opción B: Dispositivo físico

1. Habilita **Opciones de desarrollador** en tu dispositivo Android
2. Activa **Depuración USB**
3. Conecta el dispositivo a tu computadora
4. Haz clic en el botón **Run** (▶️)

## 🎯 Uso de la Aplicación

### Buscar una dirección

1. Escribe una dirección o nombre de lugar en el campo de búsqueda
2. Presiona el botón **"Buscar"**
3. El mapa se centrará en la ubicación encontrada y mostrará un marcador

**Ejemplos de búsqueda:**
- `Madrid, España`
- `Torre Eiffel, París`
- `Times Square, New York`

### Marcar una ubicación

1. Toca cualquier punto del mapa
2. Aparecerá un marcador rojo
3. Toca el marcador para ver la dirección de esa ubicación

### Permisos de ubicación

La primera vez que abras la app, solicitará permisos de ubicación:
- Selecciona **"Permitir"** para centrar el mapa en tu ubicación actual
- Selecciona **"Denegar"** si prefieres navegar manualmente

## 📁 Estructura del Proyecto

```
Localizador/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/ejemplo/localizador/
│   │   │   │   └── MainActivity.kt          # Activity principal con lógica de mapas
│   │   │   ├── res/
│   │   │   │   ├── values/
│   │   │   │   │   ├── strings.xml         # Strings de la app
│   │   │   │   │   └── themes.xml          # Tema Material 3
│   │   │   │   └── xml/
│   │   │   │       ├── backup_rules.xml
│   │   │   │       └── data_extraction_rules.xml
│   │   │   └── AndroidManifest.xml          # Configuración, permisos y API Key
│   │   └── build.gradle.kts                 # Dependencias del módulo
│   └── build.gradle.kts                     # Configuración del proyecto
└── README.md
```

## 🔧 Dependencias Principales

```kotlin
// Google Maps
implementation("com.google.android.gms:play-services-maps:18.2.0")
implementation("com.google.android.gms:play-services-location:21.1.0")
implementation("com.google.maps.android:maps-compose:4.3.0")

// Jetpack Compose
implementation(platform("androidx.compose:compose-bom:2024.01.00"))
implementation("androidx.compose.ui:ui")
implementation("androidx.compose.material3:material3")
implementation("androidx.activity:activity-compose")
```

## 🐛 Solución de Problemas

### La app se cierra al abrirse

**Causa:** API Key incorrecta o no configurada.

**Solución:** Verifica que hayas agregado tu API Key en `AndroidManifest.xml` y que esté habilitada en Google Cloud Console.

### El mapa no se muestra

**Causa 1:** El emulador no tiene Google Play Services.

**Solución:** Crea un nuevo emulador con una imagen del sistema que incluya **Google Play Store**.

**Causa 2:** API Key restringida incorrectamente.

**Solución:** En Google Cloud Console, verifica las restricciones de tu API Key.

### "ClassNotFoundException: MainActivity"

**Causa:** Inconsistencia en el nombre del paquete.

**Solución:** Asegúrate de que el nombre del paquete en `AndroidManifest.xml` coincida con la estructura de carpetas en `java/`.

### No se detecta la ubicación actual

**Causa:** Permisos no otorgados o emulador sin ubicación configurada.

**Solución:** 
- Otorga permisos de ubicación cuando la app lo solicite
- En el emulador, configura una ubicación manualmente desde los ajustes extendidos (ícono **...**)

## 📄 Licencia

Este proyecto está bajo la Licencia MIT. Ver el archivo [LICENSE](LICENSE) para más detalles.

## 👨‍💻 Autor

Desarrollado como proyecto educativo para demostrar el uso de Google Maps API en Android con Jetpack Compose.

## 🤝 Contribuciones

Las contribuciones son bienvenidas. Por favor:

1. Haz fork del proyecto
2. Crea una rama para tu feature (`git checkout -b feature/nueva-funcionalidad`)
3. Commit tus cambios (`git commit -am 'Agrega nueva funcionalidad'`)
4. Push a la rama (`git push origin feature/nueva-funcionalidad`)
5. Abre un Pull Request

## 📞 Contacto

Si tienes preguntas o sugerencias, no dudes en abrir un **Issue** en GitHub.

---

⭐ Si este proyecto te fue útil, considera darle una estrella en GitHub.
