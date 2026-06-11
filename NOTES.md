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

# Posiciones Exactas de Memoria - SCS Telemetry

**Nota de conversión**: Todos los datos se leen directamente en little-endian. La mayoría de los campos no requieren conversión matemática. Los campos marcados con notas de conversión tienen unidades específicas que pueden requerir transformación para visualización amigable.

## ZONA 1 (Offset 0-39) - Timestamps y Estado

| Nombre | Tipo | Offset | Tamaño | Conversión |
|--------|------|--------|--------|------------|
| sdkActive | bool | 0 | 1 byte | - |
| paused | bool | 4 | 1 byte | - |
| time | unsigned long long | 8 | 8 bytes | µs → segundos (÷1,000,000) |
| simulatedTime | unsigned long long | 16 | 8 bytes | µs → segundos (÷1,000,000) |
| renderTime | unsigned long long | 24 | 8 bytes | µs → segundos (÷1,000,000) |
| multiplayerTimeOffset | long long | 32 | 8 bytes | minutos → formato HH:MM |

## ZONA 2 (Offset 40-499) - Versiones y Configuraciones (uint)

| Nombre | Tipo | Offset | Tamaño | Conversión |
|--------|------|--------|--------|------------|
| telemetry_plugin_revision | uint | 40 | 4 bytes | - |
| version_major | uint | 44 | 4 bytes | - |
| version_minor | uint | 48 | 4 bytes | - |
| game | uint | 52 | 4 bytes | 0=Unknown, 1=ETS2, 2=ATS |
| telemetry_version_game_major | uint | 56 | 4 bytes | - |
| telemetry_version_game_minor | uint | 60 | 4 bytes | - |
| time_abs | uint | 64 | 4 bytes | minutos → formato HH:MM |
| gears | uint | 68 | 4 bytes | - |
| gears_reverse | uint | 72 | 4 bytes | - |
| retarderStepCount | uint | 76 | 4 bytes | - |
| truckWheelCount | uint | 80 | 4 bytes | - |
| selectorCount | uint | 84 | 4 bytes | - |
| time_abs_delivery | uint | 88 | 4 bytes | minutos → formato HH:MM |
| maxTrailerCount | uint | 92 | 4 bytes | - |
| unitCount | uint | 96 | 4 bytes | - |
| plannedDistanceKm | uint | 100 | 4 bytes | - |
| shifterSlot | uint | 104 | 4 bytes | - |
| retarderBrake | uint | 108 | 4 bytes | - |
| lightsAuxFront | uint | 112 | 4 bytes | 0=off, 1=dimmed, 2=full |
| lightsAuxRoof | uint | 116 | 4 bytes | 0=off, 1=dimmed, 2=full |
| truck_wheelSubstance[16] | uint[16] | 120 | 64 bytes | - |
| hshifterPosition[32] | uint[32] | 184 | 128 bytes | - |
| hshifterBitmask[32] | uint[32] | 312 | 128 bytes | - |
| jobDeliveredDeliveryTime | uint | 440 | 4 bytes | minutos → formato HH:MM |
| jobStartingTime | uint | 444 | 4 bytes | minutos → formato HH:MM |
| jobFinishedTime | uint | 448 | 4 bytes | minutos → formato HH:MM |

## ZONA 3 (Offset 500-699) - Valores int

| Nombre | Tipo | Offset | Tamaño | Conversión |
|--------|------|--------|--------|------------|
| restStop | int | 500 | 4 bytes | minutos → formato HH:MM |
| gear | int | 504 | 4 bytes | - |
| gearDashboard | int | 508 | 4 bytes | - |
| hshifterResulting[32] | int[32] | 512 | 128 bytes | - |
| jobDeliveredEarnedXp | int | 640 | 4 bytes | - |

## ZONA 4 (Offset 700-1499) - Valores float 

| Nombre | Tipo | Offset | Tamaño | Conversión |
|--------|------|--------|--------|------------|
| scale | float | 700 | 4 bytes | - |
| fuelCapacity | float | 704 | 4 bytes | litros |
| fuelWarningFactor | float | 708 | 4 bytes | - |
| adblueCapacity | float | 712 | 4 bytes | litros |
| adblueWarningFactor | float | 716 | 4 bytes | - |
| airPressureWarning | float | 720 | 4 bytes | psi |
| airPressurEmergency | float | 724 | 4 bytes | psi |
| oilPressureWarning | float | 728 | 4 bytes | psi |
| waterTemperatureWarning | float | 732 | 4 bytes | °C |
| batteryVoltageWarning | float | 736 | 4 bytes | volts |
| engineRpmMax | float | 740 | 4 bytes | RPM |
| gearDifferential | float | 744 | 4 bytes | - |
| cargoMass | float | 748 | 4 bytes | kg |
| truckWheelRadius[16] | float[16] | 752 | 64 bytes | metros |
| gearRatiosForward[24] | float[24] | 816 | 96 bytes | - |
| gearRatiosReverse[8] | float[8] | 912 | 32 bytes | - |
| unitMass | float | 944 | 4 bytes | kg |
| speed | float | 948 | 4 bytes | m/s → km/h (×3.6) |
| engineRpm | float | 952 | 4 bytes | RPM |
| userSteer | float | 956 | 4 bytes | -1 a 1 → grados (×90) |
| userThrottle | float | 960 | 4 bytes | 0-1 → porcentaje (×100) |
| userBrake | float | 964 | 4 bytes | 0-1 → porcentaje (×100) |
| userClutch | float | 968 | 4 bytes | 0-1 → porcentaje (×100) |
| gameSteer | float | 972 | 4 bytes | -1 a 1 → grados (×90) |
| gameThrottle | float | 976 | 4 bytes | 0-1 → porcentaje (×100) |
| gameBrake | float | 980 | 4 bytes | 0-1 → porcentaje (×100) |
| gameClutch | float | 984 | 4 bytes | 0-1 → porcentaje (×100) |
| cruiseControlSpeed | float | 988 | 4 bytes | m/s → km/h (×3.6) |
| airPressure | float | 992 | 4 bytes | psi |
| brakeTemperature | float | 996 | 4 bytes | °C |
| fuel | float | 1000 | 4 bytes | litros |
| fuelAvgConsumption | float | 1004 | 4 bytes | litros/km |
| fuelRange | float | 1008 | 4 bytes | km |
| adblue | float | 1012 | 4 bytes | litros |
| oilPressure | float | 1016 | 4 bytes | psi |
| oilTemperature | float | 1020 | 4 bytes | °C |
| waterTemperature | float | 1024 | 4 bytes | °C |
| batteryVoltage | float | 1028 | 4 bytes | volts |
| lightsDashboard | float | 1032 | 4 bytes | 0-1 → porcentaje (×100) |
| wearEngine | float | 1036 | 4 bytes | 0-1 → porcentaje (×100) |
| wearTransmission | float | 1040 | 4 bytes | 0-1 → porcentaje (×100) |
| wearCabin | float | 1044 | 4 bytes | 0-1 → porcentaje (×100) |
| wearChassis | float | 1048 | 4 bytes | 0-1 → porcentaje (×100) |
| wearWheels | float | 1052 | 4 bytes | 0-1 → porcentaje (×100) |
| truckOdometer  | float | 1056 | 4 bytes | km (sin conversión) |
| routeDistance | float | 1060 | 4 bytes | metros → km (÷1000) |
| routeTime | float | 1064 | 4 bytes | segundos → HH:MM:SS |
| speedLimit | float | 1068 | 4 bytes | m/s → km/h (×3.6) |
| truck_wheelSuspDeflection[16] | float[16] | 1072 | 64 bytes | metros |
| truck_wheelVelocity[16] | float[16] | 1136 | 64 bytes | rotaciones/s → RPM (×60) |
| truck_wheelSteering[16] | float[16] | 1200 | 64 bytes | rotaciones → grados (×360) |
| truck_wheelRotation[16] | float[16] | 1264 | 64 bytes | rotaciones → grados (×360) |
| truck_wheelLift[16] | float[16] | 1328 | 64 bytes | 0-1 → porcentaje (×100) |
| truck_wheelLiftOffset[16] | float[16] | 1392 | 64 bytes | metros |
| jobDeliveredCargoDamage | float | 1456 | 4 bytes | 0-1 → porcentaje (×100) |
| jobDeliveredDistanceKm | float | 1460 | 4 bytes | km |
| refuelAmount | float | 1464 | 4 bytes | litros |
| cargoDamage | float | 1468 | 4 bytes | 0-1 → porcentaje (×100) |

## ZONA 5 (Offset 1500-1639) - Valores bool

| Nombre | Tipo | Offset | Tamaño | Conversión |
|--------|------|--------|--------|------------|
| truckWheelSteerable[16] | bool[16] | 1500 | 16 bytes | - |
| truckWheelSimulated[16] | bool[16] | 1516 | 16 bytes | - |
| truckWheelPowered[16] | bool[16] | 1532 | 16 bytes | - |
| truckWheelLiftable[16] | bool[16] | 1548 | 16 bytes | - |
| isCargoLoaded | bool | 1564 | 1 byte | - |
| specialJob | bool | 1565 | 1 byte | - |
| parkBrake | bool | 1566 | 1 byte | - |
| motorBrake | bool | 1567 | 1 byte | - |
| airPressureWarning | bool | 1568 | 1 byte | - |
| airPressureEmergency | bool | 1569 | 1 byte | - |
| fuelWarning | bool | 1570 | 1 byte | - |
| adblueWarning | bool | 1571 | 1 byte | - |
| oilPressureWarning | bool | 1572 | 1 byte | - |
| waterTemperatureWarning | bool | 1573 | 1 byte | - |
| batteryVoltageWarning | bool | 1574 | 1 byte | - |
| electricEnabled | bool | 1575 | 1 byte | - |
| engineEnabled | bool | 1576 | 1 byte | - |
| wipers | bool | 1577 | 1 byte | - |
| blinkerLeftActive | bool | 1578 | 1 byte | - |
| blinkerRightActive | bool | 1579 | 1 byte | - |
| blinkerLeftOn | bool | 1580 | 1 byte | - |
| blinkerRightOn | bool | 1581 | 1 byte | - |
| lightsParking | bool | 1582 | 1 byte | - |
| lightsBeamLow | bool | 1583 | 1 byte | - |
| lightsBeamHigh | bool | 1584 | 1 byte | - |
| lightsBeacon | bool | 1585 | 1 byte | - |
| lightsBrake | bool | 1586 | 1 byte | - |
| lightsReverse | bool | 1587 | 1 byte | - |
| lightsHazard | bool | 1588 | 1 byte | - |
| cruiseControl | bool | 1589 | 1 byte | - |
| truck_wheelOnGround[16] | bool[16] | 1590 | 16 bytes | - |
| shifterToggle[2] | bool[2] | 1606 | 2 bytes | - |
| differentialLock | bool | 1608 | 1 byte | - |
| liftAxle | bool | 1609 | 1 byte | - |
| liftAxleIndicator | bool | 1610 | 1 byte | - |
| trailerLiftAxle | bool | 1611 | 1 byte | - |
| trailerLiftAxleIndicator | bool | 1612 | 1 byte | - |
| jobDeliveredAutoparkUsed | bool | 1613 | 1 byte | - |
| jobDeliveredAutoloadUsed | bool | 1614 | 1 byte | - |

## ZONA 6 (Offset 1640-1999) - Vectores float 

| Nombre | Tipo | Offset | Tamaño | Conversión |
|--------|------|--------|--------|------------|
| cabinPositionX | float | 1640 | 4 bytes | metros |
| cabinPositionY | float | 1644 | 4 bytes | metros |
| cabinPositionZ | float | 1648 | 4 bytes | metros |
| headPositionX | float | 1652 | 4 bytes | metros |
| headPositionY | float | 1656 | 4 bytes | metros |
| headPositionZ | float | 1660 | 4 bytes | metros |
| truckHookPositionX | float | 1664 | 4 bytes | metros |
| truckHookPositionY | float | 1668 | 4 bytes | metros |
| truckHookPositionZ | float | 1672 | 4 bytes | metros |
| truckWheelPositionX[16] | float[16] | 1676 | 64 bytes | metros |
| truckWheelPositionY[16] | float[16] | 1740 | 64 bytes | metros |
| truckWheelPositionZ[16] | float[16] | 1804 | 64 bytes | metros |
| lv_accelerationX  | float | 1868 | 4 bytes | m/s → km/h (×3.6) |
| lv_accelerationY  | float | 1872 | 4 bytes | m/s → km/h (×3.6) |
| lv_accelerationZ  | float | 1876 | 4 bytes | m/s → km/h (×3.6) |
| av_accelerationX | float | 1880 | 4 bytes | rotaciones/s → RPM (×60) |
| av_accelerationY | float | 1884 | 4 bytes | rotaciones/s → RPM (×60) |
| av_accelerationZ | float | 1888 | 4 bytes | rotaciones/s → RPM (×60) |
| accelerationX  | float | 1892 | 4 bytes | m/s² (mantener) |
| accelerationY  | float | 1896 | 4 bytes | m/s² (mantener) |
| accelerationZ  | float | 1900 | 4 bytes | m/s² (mantener) |
| aa_accelerationX | float | 1904 | 4 bytes | rotaciones/s² (mantener) |
| aa_accelerationY | float | 1908 | 4 bytes | rotaciones/s² (mantener) |
| aa_accelerationZ | float | 1912 | 4 bytes | rotaciones/s² (mantener) |
| cabinAVX | float | 1916 | 4 bytes | rotaciones/s → RPM (×60) |
| cabinAVY | float | 1920 | 4 bytes | rotaciones/s → RPM (×60) |
| cabinAVZ | float | 1924 | 4 bytes | rotaciones/s → RPM (×60) |
| cabinAAX | float | 1928 | 4 bytes | rotaciones/s² (mantener) |
| cabinAAY | float | 1932 | 4 bytes | rotaciones/s² (mantener) |
| cabinAAZ | float | 1936 | 4 bytes | rotaciones/s² (mantener) |

## ZONA 7 (Offset 2000-2199) - Placement float

| Nombre | Tipo | Offset | Tamaño | Conversión |
|--------|------|--------|--------|------------|
| cabinOffsetX | float | 2000 | 4 bytes | metros |
| cabinOffsetY | float | 2004 | 4 bytes | metros |
| cabinOffsetZ | float | 2008 | 4 bytes | metros |
| cabinOffsetrotationX | float | 2012 | 4 bytes | rad → grados (×57.2958) |
| cabinOffsetrotationY | float | 2016 | 4 bytes | rad → grados (×57.2958) |
| cabinOffsetrotationZ | float | 2020 | 4 bytes | rad → grados (×57.2958) |
| headOffsetX | float | 2024 | 4 bytes | metros |
| headOffsetY | float | 2028 | 4 bytes | metros |
| headOffsetZ | float | 2032 | 4 bytes | metros |
| headOffsetrotationX | float | 2036 | 4 bytes | rad → grados (×57.2958) |
| headOffsetrotationY | float | 2040 | 4 bytes | rad → grados (×57.2958) |
| headOffsetrotationZ | float | 2044 | 4 bytes | rad → grados (×57.2958) |

## ZONA 8 (Offset 2200-2299) - Placement double

| Nombre | Tipo | Offset | Tamaño | Conversión |
|--------|------|--------|--------|------------|
| coordinateX | double | 2200 | 8 bytes | metros |
| coordinateY | double | 2208 | 8 bytes | metros |
| coordinateZ | double | 2216 | 8 bytes | metros |
| rotationX | double | 2224 | 8 bytes | rad → grados (×57.2958) |
| rotationY | double | 2232 | 8 bytes | rad → grados (×57.2958) |
| rotationZ | double | 2240 | 8 bytes | rad → grados (×57.2958) |

## ZONA 9 (Offset 2300-3999) - Strings 

| Nombre | Tipo | Offset | Tamaño | Conversión |
|--------|------|--------|--------|------------|
| truckBrandId  | char[64] | 2300 | 64 bytes | Reemplazar \u0000 |
| truckBrand  | char[64] | 2364 | 64 bytes | Reemplazar \u0000 |
| truckId  | char[64] | 2428 | 64 bytes | Reemplazar \u0000 |
| truckName  | char[64] | 2492 | 64 bytes | Reemplazar \u0000 |
| cargoId  | char[64] | 2556 | 64 bytes | Reemplazar \u0000 |
| cargo  | char[64] | 2620 | 64 bytes | Reemplazar \u0000 |
| cityDstId  | char[64] | 2684 | 64 bytes | Reemplazar \u0000 |
| cityDst  | char[64] | 2748 | 64 bytes | Reemplazar \u0000 |
| compDstId  | char[64] | 2812 | 64 bytes | Reemplazar \u0000 |
| compDst  | char[64] | 2876 | 64 bytes | Reemplazar \u0000 |
| citySrcId  | char[64] | 2940 | 64 bytes | Reemplazar \u0000 |
| citySrc  | char[64] | 3004 | 64 bytes | Reemplazar \u0000 |
| compSrcId  | char[64] | 3068 | 64 bytes | Reemplazar \u0000 |
| compSrc  | char[64] | 3132 | 64 bytes | Reemplazar \u0000 |
| shifterType | char[16] | 3196 | 16 bytes | Reemplazar \u0000 |
| truckLicensePlate  | char[64] | 3212 | 64 bytes | Reemplazar \u0000 |
| truckLicensePlateCountryId  | char[64] | 3276 | 64 bytes | Reemplazar \u0000 |
| truckLicensePlateCountry  | char[64] | 3340 | 64 bytes | Reemplazar \u0000 |
| jobMarket | char[32] | 3404 | 32 bytes | Reemplazar \u0000 |
| fineOffence | char[32] | 3436 | 32 bytes | Reemplazar \u0000 |
| ferrySourceName | char[64] | 3468 | 64 bytes | Reemplazar \u0000 |
| ferryTargetName | char[64] | 3532 | 64 bytes | Reemplazar \u0000 |
| ferrySourceId | char[64] | 3596 | 64 bytes | Reemplazar \u0000 |
| ferryTargetId | char[64] | 3660 | 64 bytes | Reemplazar \u0000 |
| trainSourceName | char[64] | 3724 | 64 bytes | Reemplazar \u0000 |
| trainTargetName | char[64] | 3788 | 64 bytes | Reemplazar \u0000 |
| trainSourceId | char[64] | 3852 | 64 bytes | Reemplazar \u0000 |
| trainTargetId | char[64] | 3916 | 64 bytes | Reemplazar \u0000 |

## ZONA 10 (Offset 4000-4199) - Unsigned long long

| Nombre | Tipo | Offset | Tamaño | Conversión |
|--------|------|--------|--------|------------|
| jobIncome  | unsigned long long | 4000 | 8 bytes | centésimas → moneda (÷100) |

## ZONA 11 (Offset 4200-4299) - Long long

| Nombre | Tipo | Offset | Tamaño | Conversión |
|--------|------|--------|--------|------------|
| jobCancelledPenalty | long long | 4200 | 8 bytes | centésimas → moneda (÷100) |
| jobDeliveredRevenue | long long | 4208 | 8 bytes | centésimas → moneda (÷100) |
| fineAmount | long long | 4216 | 8 bytes | centésimas → moneda (÷100) |
| tollgatePayAmount | long long | 4224 | 8 bytes | centésimas → moneda (÷100) |
| ferryPayAmount | long long | 4232 | 8 bytes | centésimas → moneda (÷100) |
| trainPayAmount | long long | 4240 | 8 bytes | centésimas → moneda (÷100) |

## ZONA 12 (Offset 4300-4399) - Eventos especiales

| Nombre | Tipo | Offset | Tamaño | Conversión |
|--------|------|--------|--------|------------|
| onJob | bool | 4300 | 1 byte | - |
| jobFinished | bool | 4301 | 1 byte | - |
| jobCancelled | bool | 4302 | 1 byte | - |
| jobDelivered | bool | 4303 | 1 byte | - |
| fined | bool | 4304 | 1 byte | - |
| tollgate | bool | 4305 | 1 byte | - |
| ferry | bool | 4306 | 1 byte | - |
| train | bool | 4307 | 1 byte | - |
| refuel | bool | 4308 | 1 byte | - |
| refuelPayed | bool | 4309 | 1 byte | - |

## ZONA 13 (Offset 4400-5999) - Sustancias

| Nombre | Tipo | Offset | Tamaño | Conversión |
|--------|------|--------|--------|------------|
| substance[25][64] | char[25][64] | 4400 | 1600 bytes | Reemplazar \u0000 c/u |

## ZONA 14 (Offset 6000-21619) - Trailers

| Nombre | Tipo | Offset | Tamaño | Conversión |
|--------|------|--------|--------|------------|
| trailer[10] | struct[10] | 6000 | 15600 bytes | Ver estructura individual (1560 bytes c/u) |


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
