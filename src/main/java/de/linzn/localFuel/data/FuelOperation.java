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

import de.azcore.azcoreRuntime.taskManagment.operations.AbstractOperation;
import de.azcore.azcoreRuntime.taskManagment.operations.OperationOutput;
import de.linzn.localFuel.LocalFuelPlugin;


public class FuelOperation extends AbstractOperation {
    @Override
    public OperationOutput runOperation() {
        OperationOutput operationOutput = new OperationOutput(this);
        LocalFuelPlugin.localFuelPlugin.fuelData.updateData();
        operationOutput.setExit(0);
        return operationOutput;
    }
}
