# ConvoyOS

ConvoyOS es una aplicación desarrollada en Java para American Truck Simulator (ATS) y Euro Truck Simulator 2 (ETS2) que consume la telemetría expuesta por el plugin SCS SDK mediante memoria compartida.

El objetivo del proyecto es proporcionar una plataforma modular para monitoreo, estadísticas, eventos e integraciones basadas en la telemetría del simulador.

---

## 🚛 Características Actuales

### Telemetría

* Conexión a memoria compartida (`Local\SCSTelemetry`)
* Lectura de telemetría en tiempo real
* Parsing de datos en formato Little Endian
* Soporte para valores numéricos, booleanos y cadenas de texto

### Datos disponibles

#### Juego

* Game ID

#### Camión

* Marca
* Modelo
* Velocidad
* Odómetro

#### Trabajo

* Carga actual
* Ciudad origen
* Ciudad destino
* Estado del trabajo

### Dashboard

* Dashboard de consola en tiempo real

---

## 📋 Requisitos

### Software

* Java 17+
* Maven 3.9+
* Windows 10 / Windows 11

### Juegos compatibles

* American Truck Simulator
* Euro Truck Simulator 2

### Plugin requerido

RenCloud SCS SDK Plugin v1.12.1

---

## 🏗️ Arquitectura

```text
ATS / ETS2
    │
    ▼
RenCloud Plugin
    │
    ▼
Shared Memory
    │
    ▼
ConvoyOS
```

---

## 📁 Estructura del Proyecto

```text
src/main/java/com/convoyos

├── memory
│   ├── SharedMemoryReader.java
│   ├── TelemetryParser.java
│   └── TelemetryOffsets.java
│
├── model
│   └── TelemetryData.java
│
├── ui
│   └── ConsoleDashboard.java
│
└── ConvoyOS.java
```

---

## 🚀 Roadmap

### Fase 1 - Lectura de Telemetría

* [x] Conexión a memoria compartida
* [x] Lectura de enteros
* [x] Lectura de floats
* [x] Lectura de booleanos
* [x] Lectura de strings
* [x] Dashboard de consola

### Fase 2 - Información del Vehículo

* [ ] RPM
* [ ] Combustible
* [ ] Límite de velocidad
* [ ] Daños

### Fase 3 - Eventos

* [ ] Inicio de trabajo
* [ ] Finalización de trabajo
* [ ] Cancelación de trabajo
* [ ] Repostajes
* [ ] Multas

### Fase 4 - Estadísticas

* [ ] Distancia recorrida
* [ ] Historial de viajes
* [ ] Tiempo de conducción
* [ ] Consumo de combustible

### Fase 5 - Integraciones

* [ ] Discord Rich Presence
* [ ] API REST
* [ ] Sincronización en la nube

### Fase 6 - Interfaz Gráfica

* [ ] Dashboard Desktop
* [ ] Widgets
* [ ] Configuración visual

---

## 📚 Documentación

La información técnica del proyecto se encuentra en:

* `NOTES.md`

Incluye:

* Offsets validados
* Conversión de unidades
* Encoding de strings
* Validaciones realizadas
* Decisiones técnicas
* Hallazgos durante el desarrollo

---

## 📄 Licencia

GNU General Public License v3.0 (GPL-3.0)
