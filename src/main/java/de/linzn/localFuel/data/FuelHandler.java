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

import de.linzn.localFuel.LocalFuelPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;

public class FuelHandler {

    public static JSONArray getRawTankDataInRange(double lat, double lng, int range, String type) {
        String key = LocalFuelPlugin.localFuelPlugin.getDefaultConfig().getString("tanken.apiKey");
        String apiURL = "https://creativecommons.tankerkoenig.de/json/list.php?lat=" + lat + "&lng=" + lng + "&rad=" + range + "&sort=dist&type=" + type + "&apikey=" + key;
        try {
            return readJsonFromUrl(apiURL).getJSONArray("stations");
        } catch (IOException ignored) {
        }
        return null;
    }

    public static String formatOutput(GasStation gasStation) {
        return "GasStation: " + gasStation.getPrice() + " Euro " + gasStation.getName() + ", " + gasStation.getPlace();
    }

    private static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        try (InputStream is = new URL(url).openStream()) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            return new JSONObject(jsonText);
        }
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }
}
