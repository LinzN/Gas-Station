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
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Latest fuel prices. Updated by the price callback, read by commands and other plugins.
 * The station list is replaced as a whole, so readers always see a consistent snapshot.
 */
public class FuelData {
    private volatile GasStation cheapestGasStation = null;
    private volatile GasStation pastCheapestGasStation = null;
    private volatile List<GasStation> stations = List.of();

    /**
     * Load current prices.
     *
     * @return the cheapest open station, or the previous value if the update failed
     */
    public synchronized GasStation updateData() {
        double lat = GasStationPlugin.gasStationPlugin.getDefaultConfig().getDouble("tanken.data.lat");
        double lng = GasStationPlugin.gasStationPlugin.getDefaultConfig().getDouble("tanken.data.lng");
        int range = GasStationPlugin.gasStationPlugin.getDefaultConfig().getInt("tanken.data.range");
        String type = GasStationPlugin.gasStationPlugin.getDefaultConfig().getString("tanken.data.type");

        JSONArray rawData = FuelHandler.getRawTankDataInRange(lat, lng, range, type);
        if (rawData == null) {
            throw new IllegalStateException("No fuel data received");
        }

        List<GasStation> newStations = new ArrayList<>();
        GasStation tempCheapestGasStation = null;
        for (int i = 0; i < rawData.length(); i++) {
            JSONObject entry = rawData.optJSONObject(i);
            if (entry == null) {
                continue;
            }
            GasStation gasStation = new GasStation(entry);
            newStations.add(gasStation);
            /* closed stations have no price */
            if (!gasStation.hasPrice()) {
                continue;
            }
            if (tempCheapestGasStation == null || gasStation.getPrice() < tempCheapestGasStation.getPrice()) {
                tempCheapestGasStation = gasStation;
            }
        }
        this.stations = List.copyOf(newStations);
        if (tempCheapestGasStation == null) {
            return this.cheapestGasStation;
        }
        if (this.cheapestGasStation != null) {
            this.pastCheapestGasStation = this.cheapestGasStation;
        }
        this.cheapestGasStation = tempCheapestGasStation;
        return tempCheapestGasStation;
    }

    public GasStation getPastCheapestGasStation() {
        return this.pastCheapestGasStation;
    }

    public GasStation getCheapestGasStation() {
        return this.cheapestGasStation;
    }

    public ArrayList<GasStation> getGasStationsInRange(double range) {
        ArrayList<GasStation> gasStations = new ArrayList<>();
        for (GasStation gasStation : this.stations) {
            if (gasStation.getDist() <= range) {
                gasStations.add(gasStation);
            }
        }
        return gasStations;
    }
}
