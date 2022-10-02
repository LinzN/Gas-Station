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


import de.stem.stemSystem.STEMSystemApp;
import de.stem.stemSystem.taskManagment.AbstractCallback;
import de.stem.stemSystem.taskManagment.CallbackTime;
import de.stem.stemSystem.taskManagment.operations.OperationOutput;

import java.util.concurrent.TimeUnit;

public class CheckPriceCallback extends AbstractCallback {
    @Override
    public void operation() {
        FuelOperation fuelOperation = new FuelOperation();
        addOperationData(fuelOperation);
    }

    @Override
    public void callback(OperationOutput operationOutput) {
        STEMSystemApp.LOGGER.DEBUG("Fuel database update complete");
    }

    @Override
    public CallbackTime getTime() {
        return new CallbackTime(1, 15, TimeUnit.MINUTES);
    }

}
