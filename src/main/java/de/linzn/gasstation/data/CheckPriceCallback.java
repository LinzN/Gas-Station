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
import de.linzn.stem.taskManagment.AbstractCallback;
import de.linzn.stem.taskManagment.CallbackTime;
import de.linzn.stem.taskManagment.operations.OperationOutput;

import java.util.concurrent.TimeUnit;

public class CheckPriceCallback extends AbstractCallback {

    @Override
    public void operation() {
        addOperationData(new FuelOperation());
    }

    @Override
    public void callback(OperationOutput operationOutput) {
        if (operationOutput.getExit() == 0) {
            GasStationPlugin.gasStationPlugin.getPluginLogger().debug("Fuel database update complete");
        } else {
            GasStationPlugin.gasStationPlugin.getPluginLogger().warning("Fuel database update failed");
        }
    }

    @Override
    public CallbackTime getTime() {
        return new CallbackTime(1, 15, TimeUnit.MINUTES);
    }
}
