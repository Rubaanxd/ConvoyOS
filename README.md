# ConvoyOS

ConvoyOS es una aplicación desarrollada en Java para American Truck Simulator (ATS) y Euro Truck Simulator 2 (ETS2) que obtiene información directamente de la memoria compartida de SCS Software y la transforma en métricas útiles para conductores, convoyes y futuras integraciones web.

Actualmente el proyecto permite leer datos de telemetría en tiempo real y generar estadísticas de sesión.

## Características

### Telemetría en tiempo real

* Detección del juego (ATS / ETS2)
* Marca y modelo del camión
* Velocidad actual
* RPM del motor
* Odómetro
* Combustible actual
* Capacidad de combustible
* Autonomía estimada
* Límite de velocidad

### Información de trabajo

* Estado del conductor
* Carga transportada
* Ciudad de origen
* Ciudad de destino
* Ingreso del trabajo

### Estadísticas de sesión

* Fecha y hora de inicio
* Última lectura recibida
* Distancia recorrida durante la sesión
* Duración total de la sesión
* Tiempo efectivo de conducción
* Velocidad promedio de conducción

## Arquitectura

```text
com.convoyos
├── memory
│   ├── SharedMemoryReader
│   ├── TelemetryOffsets
│   └── TelemetryParser
│
├── model
│   └── TelemetryData
│
├── telemetry
│   └── TelemetryService
│
├── stats
│   └── SessionStats
│
├── ui
│   └── ConsoleDashboard
│
└── ConvoyOS
```

## Tecnologías

* Java 17
* Maven
* JNA
* Shared Memory SDK de SCS Software

## Estado actual

Versión actual: v0.2.0

Implementado:

* Lectura de memoria compartida
* Parseo de telemetría
* Dashboard de consola
* Información de trabajos
* Estadísticas de sesión

En desarrollo:

* Persistencia de sesiones
* Base de datos local
* Dashboard web
* Ranking de conductores
* Sistema de convoyes

## Ejemplo de salida

```text
================================
          ConvoyOS
================================

Juego: ATS

Camión: Kenworth W900
Velocidad: 78 km/h
RPM: 1450
Combustible: 395.5 / 454.3 L (87.1%)
Autonomía: 1,236 km
Odómetro: 104 km

Sesión:
Inicio: 104.2 km
Actual: 122.0 km
Recorridos: 17.8 km
Duración: 00:35:22
Velocidad Promedio: 68.4 km/h
Inicio sesión: 11/06/2026 18:57:50
Última lectura: 11/06/2026 19:33:12
Tiempo conduciendo: 00:28:47

Estado: En ruta

Carga: Centeno
Origen: Basaseachi
Destino: Nuevo Casas Grandes
```

## Notas

Las posiciones de memoria, conversiones de datos y observaciones técnicas se documentan en el archivo:

```text
NOTES.md
```

Este archivo contiene la referencia utilizada durante el desarrollo para interpretar correctamente la memoria compartida de ATS y ETS2.

---

## Roadmap

### v0.3.0
- Persistencia de sesiones
- Base de datos SQLite
- Historial de conducción

### v0.4.0
- API REST
- Dashboard web

### v0.5.0
- Ranking de conductores
- Estadísticas globales
- Sistema de convoyes