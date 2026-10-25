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

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Locale;

public class FuelHandler {
    private static final HttpClient HTTP_CLIENT = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .followRedirects(HttpClient.Redirect.NORMAL)
            .build();

    /**
     * Query the Tankerkoenig API.
     *
     * @return stations or null if the request failed
     */
    public static JSONArray getRawTankDataInRange(double lat, double lng, int range, String type) {
        String key = GasStationPlugin.gasStationPlugin.getDefaultConfig().getString("tanken.apiKey");
        String apiURL = String.format(Locale.ROOT,
                "https://creativecommons.tankerkoenig.de/json/list.php?lat=%f&lng=%f&rad=%d&sort=dist&type=%s&apikey=%s",
                lat, lng, range, URLEncoder.encode(type, StandardCharsets.UTF_8), URLEncoder.encode(key, StandardCharsets.UTF_8));
        try {
            HttpRequest request = HttpRequest.newBuilder(URI.create(apiURL)).timeout(Duration.ofSeconds(20)).GET().build();
            HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
            if (response.statusCode() != 200) {
                GasStationPlugin.gasStationPlugin.getPluginLogger().warning("Tankerkoenig API returned HTTP " + response.statusCode());
                return null;
            }
            JSONObject json = new JSONObject(response.body());
            if (!json.optBoolean("ok", false)) {
                GasStationPlugin.gasStationPlugin.getPluginLogger().warning("Tankerkoenig API error: " + json.optString("message", "unknown"));
                return null;
            }
            return json.optJSONArray("stations");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            /* the URL contains the API key, so only the error is logged */
            GasStationPlugin.gasStationPlugin.getPluginLogger().warning("Tankerkoenig request failed: " + e);
        }
        return null;
    }

    public static String formatOutput(GasStation gasStation) {
        String price = gasStation.hasPrice() ? gasStation.getPrice() + " Euro" : "closed";
        return "GasStation: " + price + " " + gasStation.getName() + ", " + gasStation.getPlace();
    }
}
