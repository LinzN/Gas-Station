![MirraNET Logo](https://raw.githubusercontent.com/LinzN/Stem/master/stem-core/src/main/resources/header-logo.png)

# gas-station

> Fuel prices for [STEM](https://github.com/LinzN/Stem) from the Tankerkönig API.

gas-station loads the current fuel prices of all gas stations around a configured location every
15 minutes and keeps the cheapest open station. The data is available on the STEM console and to other
plugins.

---

## Features

- Price update every 15 minutes from the [Tankerkönig API](https://creativecommons.tankerkoenig.de/)
- Cheapest open station and the previous cheapest station
- All stations within a given distance
- Failed requests keep the last known data; the API key is never written to the log

---

## Requirements

| Requirement | Version                     |
|-------------|-----------------------------|
| STEM        | 1.2.0 or newer (`stem-api`) |
| Plugins     | none                        |
| API key     | free key from [Tankerkönig](https://creativecommons.tankerkoenig.de/) |

---

## Installation

1. Build the plugin with `mvn clean package` (or take the build from Jenkins).
2. Copy `target/gas-station.jar` into the `plugins` folder of STEM.
3. Start STEM once to create `plugins/gas-station/config.yml`.
4. Enter your API key and location, then restart STEM (plugins are only loaded at startup).

---

## Configuration (`config.yml`)

| Key                 | Default           | Description                                         |
|---------------------|-------------------|-----------------------------------------------------|
| `tanken.apiKey`     | `xxxxxxxxxxxxxxx` | Tankerkönig API key                                 |
| `tanken.data.lat`   | `1.1`             | Latitude of the search center                       |
| `tanken.data.lng`   | `1.1`             | Longitude of the search center                      |
| `tanken.data.range` | `10`              | Search radius in km (the API allows up to 25)       |
| `tanken.data.type`  | `e5`              | Fuel type: `e5`, `e10` or `diesel`                  |

---

## Commands

| Command          | Description                                            |
|------------------|--------------------------------------------------------|
| `fuel`           | Shows the cheapest open station                        |
| `fuel <range>`   | Lists all stations within `<range>` km (e.g. `fuel 5`) |

---

## API for other plugins

```java
FuelData fuelData = GasStationPlugin.gasStationPlugin.fuelData;
GasStation cheapest = fuelData.getCheapestGasStation();      // null until the first successful update
List<GasStation> near = fuelData.getGasStationsInRange(5.0);
```

`GasStation` offers name, brand, address, coordinates, distance, `isOpen()`, `hasPrice()` and `getPrice()`.
Closed stations have no price, so check `hasPrice()` before `getPrice()`.

---

## License

Copyright (c) MirraNET, Niklas Linz. Licensed under the
[GNU Lesser General Public License v3.0](https://www.gnu.org/licenses/lgpl-3.0.html).
