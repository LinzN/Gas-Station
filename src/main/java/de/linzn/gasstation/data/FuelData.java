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
import org.json.JSONArray;

import java.util.ArrayList;

public class FuelData {
    private GasStation cheapestGasStation = null;
    private GasStation pastCheapestGasStation = null;
    private JSONArray rawData = null;

    public GasStation updateData() {
        double lat = GasStationPlugin.gasStationPlugin.getDefaultConfig().getDouble("tanken.data.lat");
        double lng = GasStationPlugin.gasStationPlugin.getDefaultConfig().getDouble("tanken.data.lng");
        int range = GasStationPlugin.gasStationPlugin.getDefaultConfig().getInt("tanken.data.range");
        String type = GasStationPlugin.gasStationPlugin.getDefaultConfig().getString("tanken.data.type");

        rawData = FuelHandler.getRawTankDataInRange(lat, lng, range, type);
        GasStation tempCheapestGasStation = new GasStation(rawData.getJSONObject(0));
        for (int i = 1; i < rawData.length(); i++) {
            GasStation gasStation = new GasStation(rawData.getJSONObject(i));
            if (gasStation.getPrice() < tempCheapestGasStation.getPrice()) {
                tempCheapestGasStation = gasStation;
            }
        }
        if (cheapestGasStation != null) {
            if (pastCheapestGasStation != null) {
                pastCheapestGasStation = cheapestGasStation;
            } else {
                pastCheapestGasStation = tempCheapestGasStation;
            }
        }
        cheapestGasStation = tempCheapestGasStation;
        return cheapestGasStation;
    }

    public GasStation getPastCheapestGasStation() {
        return this.pastCheapestGasStation;
    }

    public GasStation getCheapestGasStation() {
        return this.cheapestGasStation;
    }

    public ArrayList<GasStation> getGasStationsInRange(double range) {
        ArrayList<GasStation> gasStations = new ArrayList<>();
        for (int i = 0; i < rawData.length(); i++) {
            GasStation gasStation = new GasStation(rawData.getJSONObject(i));
            if (gasStation.getDist() <= range) {
                gasStations.add(gasStation);
            }
        }
        return gasStations;
    }
}
