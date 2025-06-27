package org.ivanpatiuk.jsonToInterface;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import com.google.gson.FieldNamingPolicy;

/**
 * This class show how to create interface instance from json using gson with type adapter
 */

public class Main {

    public static void main(String[] args) {
        String json = "{ \"effectiveAsOfDate\": \"2022-02-02\", \"asOfDate\": \"2011-12-03T10:15:30\"}";

        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES) // json field 'fund_list' will be able to be parsed into object field 'fundList'
                .registerTypeAdapterFactory(new InterfaceAdapterFactory<>())
                .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
                .create();

        Holding holding = gson.fromJson(json, Holding.class);

        System.out.println("Effective asOfDate: " + holding.getEffectiveAsOfDate());
        System.out.println("asOfDate: " + holding.getAsOfDate());
    }
}
