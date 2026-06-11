# Development Notes

Este documento almacena descubrimientos técnicos, decisiones de implementación y referencias importantes para el desarrollo de ConvoyOS.

---

# Telemetría

## Plugin utilizado

* RenCloud SCS SDK Plugin
* Versión validada: v1.12.1

Repositorio:

https://github.com/RenCloud/scs-sdk-plugin

---

## Memoria Compartida

Nombre confirmado:

```text
Local\SCSTelemetry
```

La conexión se realiza mediante:

* OpenFileMapping
* MapViewOfFile

Implementación actual:

* JNA 5.14.0
* jna-platform 5.14.0

---

# Formato de Datos

## Endianness

Todos los datos se leen utilizando:

```java
ByteOrder.LITTLE_ENDIAN
```

---

## Strings

Los strings publicados por el plugin utilizan:

```text
Windows-1252
```

No utilizan UTF-8.

Implementación:

```java
Charset.forName("Windows-1252")
```

### Ejemplo

Incorrecto:

```text
D�mper
```

Correcto:

```text
Dúmper
```

---

# Conversiones

## Velocidad

Entrada:

```text
m/s
```

Salida:

```text
km/h
```

Conversión:

```java
kmh = speed * 3.6f
```

---

## Distancia de Ruta

Entrada:

```text
metros
```

Salida:

```text
kilómetros
```

Conversión:

```java
km = meters / 1000f
```

---

## Timestamps

Entrada:

```text
microsegundos
```

Salida:

```text
segundos
```

Conversión:

```java
seconds = microseconds / 1_000_000
```

---

## Valores Monetarios

Entrada:

```text
centésimas
```

Salida:

```text
moneda real
```

Conversión:

```java
money = value / 100
```

---

## Porcentajes

Entrada:

```text
0.0 - 1.0
```

Salida:

```text
0% - 100%
```

Conversión:

```java
percent = value * 100
```

---

# Offsets Validados

## Juego

| Campo   | Offset |
| ------- | -----: |
| Game ID |     52 |

---

## Camión

| Campo          | Offset |
| -------------- | -----: |
| Speed          |    948 |
| Truck Odometer |   1056 |
| Truck Brand    |   2364 |
| Truck Name     |   2492 |

---

## Trabajo

| Campo            | Offset |
| ---------------- | -----: |
| Cargo            |   2620 |
| Destination City |   2748 |
| Source City      |   3004 |
| On Job           |   4300 |

---

# Validaciones Realizadas

## ATS

Verificado directamente contra el juego.

Campos validados:

* Game ID
* Velocidad
* Odómetro
* Marca del camión
* Modelo del camión
* Carga
* Ciudad origen
* Ciudad destino

---

## SimHub

Comparación realizada:

```text
Truck Odometer
```

Resultado:

```text
ConvoyOS == SimHub
```

Offset validado:

```text
1056
```

---

# Arquitectura Actual

```text
ATS / ETS2
    │
    ▼
RenCloud Plugin
    │
    ▼
Local\SCSTelemetry
    │
    ▼
SharedMemoryReader
    │
    ▼
TelemetryParser
    │
    ▼
TelemetryData
    │
    ▼
ConsoleDashboard
```

---

# Decisiones Técnicas

## Java

Versión objetivo:

```text
Java 17
```

---

## Build Tool

```text
Maven
```

---

## UI

Implementación actual:

```text
ConsoleDashboard
```

Implementaciones futuras:

* Swing
* JavaFX
* Web Dashboard

---

# Pendientes

## Telemetría

* Engine RPM
* Fuel
* Speed Limit
* Job Income
* Damage

## Eventos

* Job Finished
* Job Delivered
* Job Cancelled
* Refuel
* Fine

## Estadísticas

* Distancia recorrida
* Historial de viajes
* Tiempo de conducción
* Consumo de combustible
