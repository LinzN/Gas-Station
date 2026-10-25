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

import org.json.JSONObject;

public class GasStation {


    private final JSONObject jsonObject;

    public GasStation(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public int getId() {
        return jsonObject.getInt("id");
    }

    public String getName() {
        return jsonObject.optString("name", "");
    }

    public String getBrand() {
        return jsonObject.optString("brand", "");
    }

    public String getStreet() {
        return jsonObject.optString("street", "");
    }

    public String getPlace() {
        return jsonObject.optString("place", "");
    }

    public double getLat() {
        return jsonObject.getDouble("lat");
    }

    public double getLng() {
        return jsonObject.getDouble("lng");
    }

    public double getDist() {
        return jsonObject.optDouble("dist", Double.MAX_VALUE);
    }

    /**
     * @return true if the station reports a price (closed stations do not)
     */
    public boolean hasPrice() {
        return jsonObject.opt("price") instanceof Number;
    }

    public double getPrice() {
        return jsonObject.getDouble("price");
    }

    public boolean isOpen() {
        return jsonObject.optBoolean("isOpen", false);
    }

    public int getHouseNumber() {
        /* the API delivers house numbers as text, e.g. "12a" */
        return parseLeadingInt(jsonObject.optString("houseNumber", ""));
    }

    public int getPostCode() {
        return parseLeadingInt(jsonObject.optString("postCode", ""));
    }

    private static int parseLeadingInt(String value) {
        StringBuilder digits = new StringBuilder();
        for (char c : value.toCharArray()) {
            if (!Character.isDigit(c)) {
                break;
            }
            digits.append(c);
        }
        if (digits.isEmpty()) {
            return 0;
        }
        try {
            return Integer.parseInt(digits.toString());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public JSONObject toJSON() {
        return jsonObject;
    }
}
