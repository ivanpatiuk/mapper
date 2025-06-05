package org.ivanpatiuk.jsonToInterface;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.lang.reflect.*;

public class InterfaceAdapterFactory<T> implements TypeAdapterFactory {
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        if (!type.getRawType().isInterface()) {
            return null;
        }
        return new TypeAdapter<>() {
            @Override
            public void write(JsonWriter out, T value) throws IOException {
                gson.getAdapter(type).write(out, value);
            }

            @Override
            @SuppressWarnings("unchecked")
            public T read(JsonReader in) throws IOException {

                return (T) Proxy.newProxyInstance(
                        type.getRawType().getClassLoader(), new Class<?>[]{type.getRawType()}, new InvocationHandler() {
                            private JsonObject object = gson.getAdapter(TypeToken.get(JsonObject.class)).read(in);

                            @Override
                            public Object invoke(Object proxy1, Method method, Object[] args) {
                                String jsonProperty = method.getName().substring(3);
                                jsonProperty = Character.toLowerCase(jsonProperty.charAt(0)) + jsonProperty.substring(1);

                                @Override
                                public Object invoke(Object proxy1, Method method, Object[] args) {
                                    String jsonProperty = method.getName().substring(3);
                                    if (!object.has(jsonProperty)) {
                                        jsonProperty = Character.toLowerCase(jsonProperty.charAt(0))
                                                + jsonProperty.substring(1);
                                    }
                                    return gson.fromJson(object.get(jsonProperty), method.getReturnType());
                                }
                        });
            }
        };
    }
}
