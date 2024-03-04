package org.ivanpatiuk.objectMapper;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * This class show how to process mapping object with generic type fields
 */
public abstract class Main {

    public static void main(String[] args) {
        String json = "{\n" +
                "  \"value\": {\n" +
                "    \"surname\": \"Smith\",\n" +
                "    \"age\": 30\n" +
                "  },\n" +
                "  \"name\": \"ChildName\"\n" +
                "}";

        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(mapper.getSerializationConfig().getDefaultVisibilityChecker()
                .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                .withGetterVisibility(JsonAutoDetect.Visibility.ANY)
                .withSetterVisibility(JsonAutoDetect.Visibility.ANY)
                .withCreatorVisibility(JsonAutoDetect.Visibility.ANY));

        try {
            // Використання TypeReference для вказання параметризованого типу
            Child<B> child = mapper.readValue(json, new TypeReference<>() {
            });

            // Виведення результату
            System.out.println("Name: " + child.name);
            System.out.println("Surname: " + child.value.surname);
            System.out.println("Age: " + child.value.age);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @EqualsAndHashCode
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class Child<T extends A> {
        T value;
        String name;
    }

    @EqualsAndHashCode
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class A {
        String surname;
    }

    @EqualsAndHashCode(callSuper = true)
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class B extends A {
        int age;
    }

    @EqualsAndHashCode(callSuper = true)
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class D extends A {
        double salary;
    }
}
