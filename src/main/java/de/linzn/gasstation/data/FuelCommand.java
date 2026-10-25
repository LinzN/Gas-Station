/*
 * Copyright (C) 2020. Niklas Linz - All Rights Reserved
 * You may use, distribute and modify this code under the
 * terms of the LGPLv3 license, which unfortunately won't be
 * written for another century.
 *
 * You should have received a copy of the LGPLv3 license with
 * this file. If not, please write to: niklas.linz@enigmar.de
 *
 */

package de.linzn.gasstation.data;

import de.linzn.gasstation.GasStationPlugin;
import de.linzn.stem.api.StemLogger;
import de.linzn.stem.modules.commandModule.ICommand;

import java.util.List;

public class FuelCommand implements ICommand {

    @Override
    public boolean executeTerminal(String[] strings) {
        StemLogger logger = GasStationPlugin.gasStationPlugin.getPluginLogger();
        FuelData fuelData = GasStationPlugin.gasStationPlugin.fuelData;
        GasStation gasStation = fuelData.getCheapestGasStation();
        if (gasStation == null) {
            logger.info("No data yet");
            return true;
        }
        if (strings.length > 0) {
            double range;
            try {
                range = Double.parseDouble(strings[0].replace(',', '.'));
            } catch (NumberFormatException e) {
                logger.info("Usage: fuel [range in km]");
                return true;
            }
            List<GasStation> gasStations = fuelData.getGasStationsInRange(range);
            gasStations.forEach(station -> logger.info(FuelHandler.formatOutput(station)));
        } else {
            logger.info(FuelHandler.formatOutput(gasStation));
        }
        return true;
    }
}
