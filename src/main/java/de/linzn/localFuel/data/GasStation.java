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

import org.json.JSONObject;

public class GasStation {


    private JSONObject jsonObject;

    public GasStation(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public int getId() {
        return jsonObject.getInt("id");
    }

    public String getName() {
        return jsonObject.getString("name");
    }

    public String getBrand() {
        return jsonObject.getString("brand");
    }

    public String getStreet() {
        return jsonObject.getString("street");
    }

    public String getPlace() {
        return jsonObject.getString("place");
    }

    public double getLat() {
        return jsonObject.getDouble("lat");
    }

    public double getLng() {
        return jsonObject.getDouble("lng");
    }

    public double getDist() {
        return jsonObject.getDouble("dist");
    }

    public double getPrice() {
        return jsonObject.getDouble("price");
    }

    public boolean isOpen() {
        return jsonObject.getBoolean("isOpen");
    }

    public int getHouseNumber() {
        return jsonObject.getInt("houseNumber");
    }

    public int getPostCode() {
        return jsonObject.getInt("postCode");
    }

    public JSONObject toJSON() {
        return jsonObject;
    }
}
