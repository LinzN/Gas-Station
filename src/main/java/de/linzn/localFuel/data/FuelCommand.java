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

package de.linzn.localFuel.data;


import de.azcore.azcoreRuntime.AppLogger;
import de.azcore.azcoreRuntime.modules.commandModule.ICommand;
import de.linzn.localFuel.LocalFuelPlugin;

import java.util.ArrayList;

public class FuelCommand implements ICommand {
    @Override
    public boolean executeTerminal(String[] strings) {
        GasStation gasStation = LocalFuelPlugin.localFuelPlugin.fuelData.getCheapestGasStation();
        if (gasStation == null) {
            AppLogger.logger("No data yet", false);
            return true;
        }

        if (strings.length > 0) {
            double range = Double.parseDouble(strings[0]);
            ArrayList<GasStation> gasStations = LocalFuelPlugin.localFuelPlugin.fuelData.getGasStationsInRange(range);
            gasStations.forEach(gasStation1 -> AppLogger.logger(FuelHandler.formatOutput(gasStation1), false));
        } else {
            AppLogger.logger(FuelHandler.formatOutput(gasStation), false);
        }
        return true;
    }
}
