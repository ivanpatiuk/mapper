package org.ivanpatiuk.jsonToInterface;

import com.google.gson.*;

/**
 * This class show how to create interface instance from json using gson with type adapter
 */

public class Main {
    public static void main(String[] args) {
        String json = "{ \"ticker\": \"ZXC\", \"value\": 2.45}";

        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(new InterfaceAdapterFactory<>())
                .create();

        Holding holding = gson.fromJson(json, Holding.class);

        System.out.println("Ticker: " + holding.getTicker());
        System.out.println("Value: " + holding.getValue());
    }
}