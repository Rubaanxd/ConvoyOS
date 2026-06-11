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

| Nombre | Tipo | Offset | Tamaño | Conversión | Descripción |
|--------|------|--------|--------|------------|-------------|
| sdkActive | bool | 0 | 1 byte | - | Indica si el SDK está activo |
| paused | bool | 4 | 1 byte | - | Indica si el juego está pausado |
| time | unsigned long long | 8 | 8 bytes | µs → segundos (÷1,000,000) | Timestamp del juego en µs |
| simulatedTime | unsigned long long | 16 | 8 bytes | µs → segundos (÷1,000,000) | Timestamp de simulación en µs |
| renderTime | unsigned long long | 24 | 8 bytes | µs → segundos (÷1,000,000) | Timestamp de renderizado en µs |
| multiplayerTimeOffset | long long | 32 | 8 bytes | minutos → formato HH:MM | Offset de tiempo multiplayer en minutos |

## ZONA 2 (Offset 40-499) - Versiones y Configuraciones (uint)

| Nombre | Tipo | Offset | Tamaño | Conversión | Descripción |
|--------|------|--------|--------|------------|-------------|
| telemetry_plugin_revision | uint | 40 | 4 bytes | - | Revisión del plugin de telemetría |
| version_major | uint | 44 | 4 bytes | - | Versión mayor del SDK |
| version_minor | uint | 48 | 4 bytes | - | Versión menor del SDK |
| game | uint | 52 | 4 bytes | 0=Unknown, 1=ETS2, 2=ATS | ID del juego (ETS2 o ATS) |
| telemetry_version_game_major | uint | 56 | 4 bytes | - | Versión mayor de telemetría del juego |
| telemetry_version_game_minor | uint | 60 | 4 bytes | - | Versión menor de telemetría del juego |
| time_abs | uint | 64 | 4 bytes | minutos → formato HH:MM | Tiempo absoluto del juego en minutos |
| gears | uint | 68 | 4 bytes | - | Número de marchas hacia adelante |
| gears_reverse | uint | 72 | 4 bytes | - | Número de marchas hacia atrás |
| retarderStepCount | uint | 76 | 4 bytes | - | Número de pasos del retardador |
| truckWheelCount | uint | 80 | 4 bytes | - | Número de ruedas del camión |
| selectorCount | uint | 84 | 4 bytes | - | Número de selectores de marchas |
| time_abs_delivery | uint | 88 | 4 bytes | minutos → formato HH:MM | Tiempo absoluto de entrega en minutos |
| maxTrailerCount | uint | 92 | 4 bytes | - | Número máximo de remolques |
| unitCount | uint | 96 | 4 bytes | - | Número de unidades de carga |
| plannedDistanceKm | uint | 100 | 4 bytes | - | Distancia planificada en km |
| shifterSlot | uint | 104 | 4 bytes | - | Slot del shifter |
| retarderBrake | uint | 108 | 4 bytes | - | Estado del freno retardador |
| lightsAuxFront | uint | 112 | 4 bytes | 0=off, 1=dimmed, 2=full | Luces auxiliares frontales |
| lightsAuxRoof | uint | 116 | 4 bytes | 0=off, 1=dimmed, 2=full | Luces auxiliares del techo |
| truck_wheelSubstance[16] | uint[16] | 120 | 64 bytes | - | Sustancia bajo cada rueda |
| hshifterPosition[32] | uint[32] | 184 | 128 bytes | - | Posición del H-shifter |
| hshifterBitmask[32] | uint[32] | 312 | 128 bytes | - | Bitmask del H-shifter |
| jobDeliveredDeliveryTime | uint | 440 | 4 bytes | minutos → formato HH:MM | Tiempo de entrega del trabajo |
| jobStartingTime | uint | 444 | 4 bytes | minutos → formato HH:MM | Tiempo de inicio del trabajo |
| jobFinishedTime | uint | 448 | 4 bytes | minutos → formato HH:MM | Tiempo de finalización del trabajo |

## ZONA 3 (Offset 500-699) - Valores int

| Nombre | Tipo | Offset | Tamaño | Conversión | Descripción |
|--------|------|--------|--------|------------|-------------|
| restStop | int | 500 | 4 bytes | minutos → formato HH:MM | Tiempo de descanso en minutos |
| gear | int | 504 | 4 bytes | - | Marcha actual seleccionada |
| gearDashboard | int | 508 | 4 bytes | - | Marcha mostrada en el tablero |
| hshifterResulting[32] | int[32] | 512 | 128 bytes | - | Marcha resultante del H-shifter |
| jobDeliveredEarnedXp | int | 640 | 4 bytes | - | XP ganada al entregar trabajo |

## ZONA 4 (Offset 700-1499) - Valores float 

| Nombre | Tipo | Offset | Tamaño | Conversión | Descripción |
|--------|------|--------|--------|------------|-------------|
| scale | float | 700 | 4 bytes | - | Escala del juego |
| fuelCapacity | float | 704 | 4 bytes | litros | Capacidad del tanque de combustible |
| fuelWarningFactor | float | 708 | 4 bytes | - | Factor de advertencia de combustible |
| adblueCapacity | float | 712 | 4 bytes | litros | Capacidad del tanque de AdBlue |
| adblueWarningFactor | float | 716 | 4 bytes | - | Factor de advertencia de AdBlue |
| airPressureWarning | float | 720 | 4 bytes | psi | Presión de aire para advertencia |
| airPressurEmergency | float | 724 | 4 bytes | psi | Presión de aire de emergencia |
| oilPressureWarning | float | 728 | 4 bytes | psi | Presión de aceite para advertencia |
| waterTemperatureWarning | float | 732 | 4 bytes | °C | Temperatura de agua para advertencia |
| batteryVoltageWarning | float | 736 | 4 bytes | volts | Voltaje de batería para advertencia |
| engineRpmMax | float | 740 | 4 bytes | RPM | RPM máximo del motor |
| gearDifferential | float | 744 | 4 bytes | - | Diferencial de marcha |
| cargoMass | float | 748 | 4 bytes | kg | Masa de la carga |
| truckWheelRadius[16] | float[16] | 752 | 64 bytes | metros | Radio de cada rueda |
| gearRatiosForward[24] | float[24] | 816 | 96 bytes | - | Relaciones de marchas hacia adelante |
| gearRatiosReverse[8] | float[8] | 912 | 32 bytes | - | Relaciones de marchas hacia atrás |
| unitMass | float | 944 | 4 bytes | kg | Masa de la unidad de carga |
| speed | float | 948 | 4 bytes | m/s → km/h (×3.6) | Velocidad del camión |
| engineRpm | float | 952 | 4 bytes | RPM | RPM del motor |
| userSteer | float | 956 | 4 bytes | -1 a 1 → grados (×90) | Dirección del usuario |
| userThrottle | float | 960 | 4 bytes | 0-1 → porcentaje (×100) | Acelerador del usuario |
| userBrake | float | 964 | 4 bytes | 0-1 → porcentaje (×100) | Freno del usuario |
| userClutch | float | 968 | 4 bytes | 0-1 → porcentaje (×100) | Embrague del usuario |
| gameSteer | float | 972 | 4 bytes | -1 a 1 → grados (×90) | Dirección del juego |
| gameThrottle | float | 976 | 4 bytes | 0-1 → porcentaje (×100) | Acelerador del juego |
| gameBrake | float | 980 | 4 bytes | 0-1 → porcentaje (×100) | Freno del juego |
| gameClutch | float | 984 | 4 bytes | 0-1 → porcentaje (×100) | Embrague del juego |
| cruiseControlSpeed | float | 988 | 4 bytes | m/s → km/h (×3.6) | Velocidad del control de crucero |
| airPressure | float | 992 | 4 bytes | psi | Presión de aire actual |
| brakeTemperature | float | 996 | 4 bytes | °C | Temperatura de los frenos |
| fuel | float | 1000 | 4 bytes | litros | Cantidad de combustible actual |
| fuelAvgConsumption | float | 1004 | 4 bytes | litros/km | Consumo promedio de combustible |
| fuelRange | float | 1008 | 4 bytes | km | Autonomía con combustible actual |
| adblue | float | 1012 | 4 bytes | litros | Cantidad de AdBlue actual |
| oilPressure | float | 1016 | 4 bytes | psi | Presión de aceite actual |
| oilTemperature | float | 1020 | 4 bytes | °C | Temperatura de aceite actual |
| waterTemperature | float | 1024 | 4 bytes | °C | Temperatura de agua actual |
| batteryVoltage | float | 1028 | 4 bytes | volts | Voltaje de batería actual |
| lightsDashboard | float | 1032 | 4 bytes | 0-1 → porcentaje (×100) | Intensidad de luces del tablero |
| wearEngine | float | 1036 | 4 bytes | 0-1 → porcentaje (×100) | Desgaste del motor |
| wearTransmission | float | 1040 | 4 bytes | 0-1 → porcentaje (×100) | Desgaste de la transmisión |
| wearCabin | float | 1044 | 4 bytes | 0-1 → porcentaje (×100) | Desgaste de la cabina |
| wearChassis | float | 1048 | 4 bytes | 0-1 → porcentaje (×100) | Desgaste del chasis |
| wearWheels | float | 1052 | 4 bytes | 0-1 → porcentaje (×100) | Desgaste de las ruedas |
| truckOdometer  | float | 1056 | 4 bytes | km (sin conversión) | Odómetro del camión |
| routeDistance | float | 1060 | 4 bytes | metros → km (÷1000) | Distancia de la ruta |
| routeTime | float | 1064 | 4 bytes | segundos → HH:MM:SS | Tiempo estimado de la ruta |
| speedLimit | float | 1068 | 4 bytes | m/s → km/h (×3.6) | Límite de velocidad |
| truck_wheelSuspDeflection[16] | float[16] | 1072 | 64 bytes | metros | Deflexión de suspensión de ruedas |
| truck_wheelVelocity[16] | float[16] | 1136 | 64 bytes | rotaciones/s → RPM (×60) | Velocidad angular de ruedas |
| truck_wheelSteering[16] | float[16] | 1200 | 64 bytes | rotaciones → grados (×360) | Dirección de ruedas |
| truck_wheelRotation[16] | float[16] | 1264 | 64 bytes | rotaciones → grados (×360) | Rotación de ruedas |
| truck_wheelLift[16] | float[16] | 1328 | 64 bytes | 0-1 → porcentaje (×100) | Estado de elevación de ruedas |
| truck_wheelLiftOffset[16] | float[16] | 1392 | 64 bytes | metros | Offset de elevación de ruedas |
| jobDeliveredCargoDamage | float | 1456 | 4 bytes | 0-1 → porcentaje (×100) | Daño de carga al entregar trabajo |
| jobDeliveredDistanceKm | float | 1460 | 4 bytes | km | Distancia recorrida al entregar trabajo |
| refuelAmount | float | 1464 | 4 bytes | litros | Cantidad de combustible repostado |
| cargoDamage | float | 1468 | 4 bytes | 0-1 → porcentaje (×100) | Daño total de la carga |

## ZONA 5 (Offset 1500-1639) - Valores bool

| Nombre | Tipo | Offset | Tamaño | Conversión | Descripción |
|--------|------|--------|--------|------------|-------------|
| truckWheelSteerable[16] | bool[16] | 1500 | 16 bytes | - | Si cada rueda es direccionable |
| truckWheelSimulated[16] | bool[16] | 1516 | 16 bytes | - | Si cada rueda es simulada |
| truckWheelPowered[16] | bool[16] | 1532 | 16 bytes | - | Si cada rueda es motriz |
| truckWheelLiftable[16] | bool[16] | 1548 | 16 bytes | - | Si cada rueda es levantable |
| isCargoLoaded | bool | 1564 | 1 byte | - | Si la carga está cargada |
| specialJob | bool | 1565 | 1 byte | - | Si es un trabajo especial |
| parkBrake | bool | 1566 | 1 byte | - | Freno de estacionamiento activado |
| motorBrake | bool | 1567 | 1 byte | - | Freno motor activado |
| airPressureWarning | bool | 1568 | 1 byte | - | Advertencia de presión de aire |
| airPressureEmergency | bool | 1569 | 1 byte | - | Emergencia de presión de aire |
| fuelWarning | bool | 1570 | 1 byte | - | Advertencia de combustible |
| adblueWarning | bool | 1571 | 1 byte | - | Advertencia de AdBlue |
| oilPressureWarning | bool | 1572 | 1 byte | - | Advertencia de presión de aceite |
| waterTemperatureWarning | bool | 1573 | 1 byte | - | Advertencia de temperatura de agua |
| batteryVoltageWarning | bool | 1574 | 1 byte | - | Advertencia de voltaje de batería |
| electricEnabled | bool | 1575 | 1 byte | - | Eléctrico habilitado |
| engineEnabled | bool | 1576 | 1 byte | - | Motor habilitado |
| wipers | bool | 1577 | 1 byte | - | Limpiaparabrisas activado |
| blinkerLeftActive | bool | 1578 | 1 byte | - | Intermitente izquierdo activo |
| blinkerRightActive | bool | 1579 | 1 byte | - | Intermitente derecho activo |
| blinkerLeftOn | bool | 1580 | 1 byte | - | Intermitente izquierdo encendido |
| blinkerRightOn | bool | 1581 | 1 byte | - | Intermitente derecho encendido |
| lightsParking | bool | 1582 | 1 byte | - | Luces de estacionamiento |
| lightsBeamLow | bool | 1583 | 1 byte | - | Luces bajas |
| lightsBeamHigh | bool | 1584 | 1 byte | - | Luces altas |
| lightsBeacon | bool | 1585 | 1 byte | - | Luces baliza |
| lightsBrake | bool | 1586 | 1 byte | - | Luces de freno |
| lightsReverse | bool | 1587 | 1 byte | - | Luces de marcha atrás |
| lightsHazard | bool | 1588 | 1 byte | - | Luces de emergencia |
| cruiseControl | bool | 1589 | 1 byte | - | Control de crucero activado |
| truck_wheelOnGround[16] | bool[16] | 1590 | 16 bytes | - | Si cada rueda está en el suelo |
| shifterToggle[2] | bool[2] | 1606 | 2 bytes | - | Toggle del shifter |
| differentialLock | bool | 1608 | 1 byte | - | Bloqueo de diferencial |
| liftAxle | bool | 1609 | 1 byte | - | Eje levantado |
| liftAxleIndicator | bool | 1610 | 1 byte | - | Indicador de eje levantado |
| trailerLiftAxle | bool | 1611 | 1 byte | - | Eje de remolque levantado |
| trailerLiftAxleIndicator | bool | 1612 | 1 byte | - | Indicador de eje de remolque levantado |
| jobDeliveredAutoparkUsed | bool | 1613 | 1 byte | - | Autopark usado al entregar trabajo |
| jobDeliveredAutoloadUsed | bool | 1614 | 1 byte | - | Autoload usado al entregar trabajo |

## ZONA 6 (Offset 1640-1999) - Vectores float 

| Nombre | Tipo | Offset | Tamaño | Conversión | Descripción |
|--------|------|--------|--------|------------|-------------|
| cabinPositionX | float | 1640 | 4 bytes | metros | Posición X de la cabina |
| cabinPositionY | float | 1644 | 4 bytes | metros | Posición Y de la cabina |
| cabinPositionZ | float | 1648 | 4 bytes | metros | Posición Z de la cabina |
| headPositionX | float | 1652 | 4 bytes | metros | Posición X de la cabeza |
| headPositionY | float | 1656 | 4 bytes | metros | Posición Y de la cabeza |
| headPositionZ | float | 1660 | 4 bytes | metros | Posición Z de la cabeza |
| truckHookPositionX | float | 1664 | 4 bytes | metros | Posición X del enganche |
| truckHookPositionY | float | 1668 | 4 bytes | metros | Posición Y del enganche |
| truckHookPositionZ | float | 1672 | 4 bytes | metros | Posición Z del enganche |
| truckWheelPositionX[16] | float[16] | 1676 | 64 bytes | metros | Posición X de cada rueda |
| truckWheelPositionY[16] | float[16] | 1740 | 64 bytes | metros | Posición Y de cada rueda |
| truckWheelPositionZ[16] | float[16] | 1804 | 64 bytes | metros | Posición Z de cada rueda |
| lv_accelerationX  | float | 1868 | 4 bytes | m/s → km/h (×3.6) | Velocidad lineal X del camión |
| lv_accelerationY  | float | 1872 | 4 bytes | m/s → km/h (×3.6) | Velocidad lineal Y del camión |
| lv_accelerationZ  | float | 1876 | 4 bytes | m/s → km/h (×3.6) | Velocidad lineal Z del camión |
| av_accelerationX | float | 1880 | 4 bytes | rotaciones/s → RPM (×60) | Velocidad angular X del camión |
| av_accelerationY | float | 1884 | 4 bytes | rotaciones/s → RPM (×60) | Velocidad angular Y del camión |
| av_accelerationZ | float | 1888 | 4 bytes | rotaciones/s → RPM (×60) | Velocidad angular Z del camión |
| accelerationX  | float | 1892 | 4 bytes | m/s² (mantener) | Aceleración lineal X del camión |
| accelerationY  | float | 1896 | 4 bytes | m/s² (mantener) | Aceleración lineal Y del camión |
| accelerationZ  | float | 1900 | 4 bytes | m/s² (mantener) | Aceleración lineal Z del camión |
| aa_accelerationX | float | 1904 | 4 bytes | rotaciones/s² (mantener) | Aceleración angular X del camión |
| aa_accelerationY | float | 1908 | 4 bytes | rotaciones/s² (mantener) | Aceleración angular Y del camión |
| aa_accelerationZ | float | 1912 | 4 bytes | rotaciones/s² (mantener) | Aceleración angular Z del camión |
| cabinAVX | float | 1916 | 4 bytes | rotaciones/s → RPM (×60) | Velocidad angular X de la cabina |
| cabinAVY | float | 1920 | 4 bytes | rotaciones/s → RPM (×60) | Velocidad angular Y de la cabina |
| cabinAVZ | float | 1924 | 4 bytes | rotaciones/s → RPM (×60) | Velocidad angular Z de la cabina |
| cabinAAX | float | 1928 | 4 bytes | rotaciones/s² (mantener) | Aceleración angular X de la cabina |
| cabinAAY | float | 1932 | 4 bytes | rotaciones/s² (mantener) | Aceleración angular Y de la cabina |
| cabinAAZ | float | 1936 | 4 bytes | rotaciones/s² (mantener) | Aceleración angular Z de la cabina |

## ZONA 7 (Offset 2000-2199) - Placement float

| Nombre | Tipo | Offset | Tamaño | Conversión | Descripción |
|--------|------|--------|--------|------------|-------------|
| cabinOffsetX | float | 2000 | 4 bytes | metros | Offset X de la cabina |
| cabinOffsetY | float | 2004 | 4 bytes | metros | Offset Y de la cabina |
| cabinOffsetZ | float | 2008 | 4 bytes | metros | Offset Z de la cabina |
| cabinOffsetrotationX | float | 2012 | 4 bytes | rad → grados (×57.2958) | Rotación X de la cabina |
| cabinOffsetrotationY | float | 2016 | 4 bytes | rad → grados (×57.2958) | Rotación Y de la cabina |
| cabinOffsetrotationZ | float | 2020 | 4 bytes | rad → grados (×57.2958) | Rotación Z de la cabina |
| headOffsetX | float | 2024 | 4 bytes | metros | Offset X de la cabeza |
| headOffsetY | float | 2028 | 4 bytes | metros | Offset Y de la cabeza |
| headOffsetZ | float | 2032 | 4 bytes | metros | Offset Z de la cabeza |
| headOffsetrotationX | float | 2036 | 4 bytes | rad → grados (×57.2958) | Rotación X de la cabeza |
| headOffsetrotationY | float | 2040 | 4 bytes | rad → grados (×57.2958) | Rotación Y de la cabeza |
| headOffsetrotationZ | float | 2044 | 4 bytes | rad → grados (×57.2958) | Rotación Z de la cabeza |

## ZONA 8 (Offset 2200-2299) - Placement double

| Nombre | Tipo | Offset | Tamaño | Conversión | Descripción |
|--------|------|--------|--------|------------|-------------|
| coordinateX | double | 2200 | 8 bytes | metros | Coordenada X del camión |
| coordinateY | double | 2208 | 8 bytes | metros | Coordenada Y del camión |
| coordinateZ | double | 2216 | 8 bytes | metros | Coordenada Z del camión |
| rotationX | double | 2224 | 8 bytes | rad → grados (×57.2958) | Rotación X del camión |
| rotationY | double | 2232 | 8 bytes | rad → grados (×57.2958) | Rotación Y del camión |
| rotationZ | double | 2240 | 8 bytes | rad → grados (×57.2958) | Rotación Z del camión |

## ZONA 9 (Offset 2300-3999) - Strings 

| Nombre | Tipo | Offset | Tamaño | Conversión | Descripción |
|--------|------|--------|--------|------------|-------------|
| truckBrandId  | char[64] | 2300 | 64 bytes | Reemplazar \u0000 | ID de la marca del camión |
| truckBrand  | char[64] | 2364 | 64 bytes | Reemplazar \u0000 | Marca del camión |
| truckId  | char[64] | 2428 | 64 bytes | Reemplazar \u0000 | ID del camión |
| truckName  | char[64] | 2492 | 64 bytes | Reemplazar \u0000 | Nombre del camión |
| cargoId  | char[64] | 2556 | 64 bytes | Reemplazar \u0000 | ID de la carga |
| cargo  | char[64] | 2620 | 64 bytes | Reemplazar \u0000 | Nombre de la carga |
| cityDstId  | char[64] | 2684 | 64 bytes | Reemplazar \u0000 | ID de ciudad destino |
| cityDst  | char[64] | 2748 | 64 bytes | Reemplazar \u0000 | Ciudad destino |
| compDstId  | char[64] | 2812 | 64 bytes | Reemplazar \u0000 | ID de compañía destino |
| compDst  | char[64] | 2876 | 64 bytes | Reemplazar \u0000 | Compañía destino |
| citySrcId  | char[64] | 2940 | 64 bytes | Reemplazar \u0000 | ID de ciudad origen |
| citySrc  | char[64] | 3004 | 64 bytes | Reemplazar \u0000 | Ciudad origen |
| compSrcId  | char[64] | 3068 | 64 bytes | Reemplazar \u0000 | ID de compañía origen |
| compSrc  | char[64] | 3132 | 64 bytes | Reemplazar \u0000 | Compañía origen |
| shifterType | char[16] | 3196 | 16 bytes | Reemplazar \u0000 | Tipo de shifter |
| truckLicensePlate  | char[64] | 3212 | 64 bytes | Reemplazar \u0000 | Placa del camión |
| truckLicensePlateCountryId  | char[64] | 3276 | 64 bytes | Reemplazar \u0000 | ID de país de placa |
| truckLicensePlateCountry  | char[64] | 3340 | 64 bytes | Reemplazar \u0000 | País de placa |
| jobMarket | char[32] | 3404 | 32 bytes | Reemplazar \u0000 | Mercado de trabajo |
| fineOffence | char[32] | 3436 | 32 bytes | Reemplazar \u0000 | Tipo de multa |
| ferrySourceName | char[64] | 3468 | 64 bytes | Reemplazar \u0000 | Nombre origen ferry |
| ferryTargetName | char[64] | 3532 | 64 bytes | Reemplazar \u0000 | Nombre destino ferry |
| ferrySourceId | char[64] | 3596 | 64 bytes | Reemplazar \u0000 | ID origen ferry |
| ferryTargetId | char[64] | 3660 | 64 bytes | Reemplazar \u0000 | ID destino ferry |
| trainSourceName | char[64] | 3724 | 64 bytes | Reemplazar \u0000 | Nombre origen tren |
| trainTargetName | char[64] | 3788 | 64 bytes | Reemplazar \u0000 | Nombre destino tren |
| trainSourceId | char[64] | 3852 | 64 bytes | Reemplazar \u0000 | ID origen tren |
| trainTargetId | char[64] | 3916 | 64 bytes | Reemplazar \u0000 | ID destino tren |

## ZONA 10 (Offset 4000-4199) - Unsigned long long

| Nombre | Tipo | Offset | Tamaño | Conversión | Descripción |
|--------|------|--------|--------|------------|-------------|
| jobIncome  | unsigned long long | 4000 | 8 bytes | centésimas → moneda (÷100) | Ingreso estimado del trabajo |

## ZONA 11 (Offset 4200-4299) - Long long

| Nombre | Tipo | Offset | Tamaño | Conversión | Descripción |
|--------|------|--------|--------|------------|-------------|
| jobCancelledPenalty | long long | 4200 | 8 bytes | centésimas → moneda (÷100) | Penalización por cancelación de trabajo |
| jobDeliveredRevenue | long long | 4208 | 8 bytes | centésimas → moneda (÷100) | Ingreso al entregar trabajo |
| fineAmount | long long | 4216 | 8 bytes | centésimas → moneda (÷100) | Monto de multa |
| tollgatePayAmount | long long | 4224 | 8 bytes | centésimas → moneda (÷100) | Monto pagado en peaje |
| ferryPayAmount | long long | 4232 | 8 bytes | centésimas → moneda (÷100) | Monto pagado en ferry |
| trainPayAmount | long long | 4240 | 8 bytes | centésimas → moneda (÷100) | Monto pagado en tren |

## ZONA 12 (Offset 4300-4399) - Eventos especiales

| Nombre | Tipo | Offset | Tamaño | Conversión | Descripción |
|--------|------|--------|--------|------------|-------------|
| onJob | bool | 4300 | 1 byte | - | Hay trabajo activo |
| jobFinished | bool | 4301 | 1 byte | - | Trabajo finalizado |
| jobCancelled | bool | 4302 | 1 byte | - | Trabajo cancelado |
| jobDelivered | bool | 4303 | 1 byte | - | Trabajo entregado |
| fined | bool | 4304 | 1 byte | - | Multa aplicada |
| tollgate | bool | 4305 | 1 byte | - | Peaje pasado |
| ferry | bool | 4306 | 1 byte | - | Ferry tomado |
| train | bool | 4307 | 1 byte | - | Tren tomado |
| refuel | bool | 4308 | 1 byte | - | Repostaje realizado |
| refuelPayed | bool | 4309 | 1 byte | - | Repostaje pagado |

## ZONA 13 (Offset 4400-5999) - Sustancias

| Nombre | Tipo | Offset | Tamaño | Conversión | Descripción |
|--------|------|--------|--------|------------|-------------|
| substance[25][64] | char[25][64] | 4400 | 1600 bytes | Reemplazar \u0000 c/u | Nombres de sustancias bajo ruedas |

## ZONA 14 (Offset 6000-21619) - Trailers

| Nombre | Tipo | Offset | Tamaño | Conversión | Descripción |
|--------|------|--------|--------|------------|-------------|
| trailer[10] | struct[10] | 6000 | 15600 bytes | Ver estructura individual (1560 bytes c/u) | Datos de hasta 10 remolques |


**Notas importantes**:
- Todos los datos se leen directamente en little-endian (formato de Windows)
- Los campos marcados con "-" en la columna Conversión no requieren transformación matemática
- Los strings requieren reemplazar caracteres null (\u0000) al final del string
- Los valores monetarios están en centésimas de moneda (dividir por 100 para obtener el valor real)
- Los timestamps están en microsegundos (dividir por 1,000,000 para segundos)
- Los tiempos de juego están en minutos (convertir a formato HH:MM si es necesario)

---

## Sistema de Unidades

El campo `game` (offset 52) identifica el simulador:

- 1 = ETS2
- 2 = ATS

No debe utilizarse para convertir unidades físicas.

Los datos en memoria compartida ya se encuentran en unidades estándar:

| Tipo | Unidad |
|--------|--------|
| Velocidad | m/s |
| Distancia | km o metros |
| Combustible | litros |
| Consumo | litros/km |
| Masa | kg |
| Temperatura | °C |
| Presión | PSI |
| Voltaje | volts |

La única excepción es la moneda:

- ATS → USD ($)
- ETS2 → EUR (€)

ConvoyOS utilizará internamente el sistema métrico para todas las conversiones y visualizaciones.


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
