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
import de.linzn.stem.STEMApp;
import de.linzn.stem.modules.commandModule.ICommand;

import java.util.ArrayList;

public class FuelCommand implements ICommand {
    @Override
    public boolean executeTerminal(String[] strings) {
        GasStation gasStation = GasStationPlugin.gasStationPlugin.fuelData.getCheapestGasStation();
        if (gasStation == null) {
            STEMApp.LOGGER.LIVE("No data yet");
            return true;
        }

        if (strings.length > 0) {
            double range = Double.parseDouble(strings[0]);
            ArrayList<GasStation> gasStations = GasStationPlugin.gasStationPlugin.fuelData.getGasStationsInRange(range);
            gasStations.forEach(gasStation1 -> STEMApp.LOGGER.LIVE(FuelHandler.formatOutput(gasStation1)));
        } else {
            STEMApp.LOGGER.LIVE(FuelHandler.formatOutput(gasStation));
        }
        return true;
    }
}
