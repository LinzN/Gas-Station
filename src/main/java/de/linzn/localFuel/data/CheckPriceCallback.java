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
import de.azcore.azcoreRuntime.taskManagment.AbstractCallback;
import de.azcore.azcoreRuntime.taskManagment.CallbackTime;
import de.azcore.azcoreRuntime.taskManagment.operations.OperationOutput;
import de.azcore.azcoreRuntime.utils.Color;

import java.util.concurrent.TimeUnit;

public class CheckPriceCallback extends AbstractCallback {
    @Override
    public void operation() {
        FuelOperation fuelOperation = new FuelOperation();
        addOperationData(fuelOperation);
    }

    @Override
    public void callback(OperationOutput operationOutput) {
        AppLogger.debug(Color.GREEN + "Fuel database update complete");
    }

    @Override
    public CallbackTime getTime() {
        return new CallbackTime(1, 15, TimeUnit.MINUTES);
    }

}
