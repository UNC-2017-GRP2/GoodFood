package com.netcracker.config;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.math.BigInteger;

public class BigIntegerAdapter extends TypeAdapter<BigInteger> {

    @Override
    public void write(JsonWriter jsonWriter, BigInteger bigInteger) throws IOException {
        if (bigInteger == null) {
            jsonWriter.value("");
        } else {
            jsonWriter.value(bigInteger.toString());
        }
    }

    @Override
    public BigInteger read(JsonReader jsonReader) throws IOException {
        jsonReader.beginObject();
        jsonReader.nextName();

        BigInteger bigInteger;
        try {
            bigInteger = new BigInteger(String.valueOf((jsonReader.nextDouble())));
        } catch (IllegalStateException jse) {
            // We have to consume JSON document fully.
            jsonReader.nextBoolean();
            bigInteger = null;
        }

        jsonReader.endObject();

        return bigInteger;
    }
}

