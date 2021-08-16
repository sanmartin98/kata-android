package com.cornershop.android.kata.cornerbook.data.commons;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateDeserializer implements JsonDeserializer<Date> {

    @Override
    public Date deserialize(JsonElement element, Type arg1, JsonDeserializationContext arg2) throws JsonParseException {
        String date = element.getAsString();

        String[] formats = {
                "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", //2015-12-15T20:32:23.000Z
                "yyyy-MM-dd'T'HH:mm:ss'Z'", //2015-12-16T23:11:43Z
                "yyyy-MM-dd'T'HH:mm:ss.SSSSSSZZZZZ" //2017-11-10T19:06:31.594002+00:00
        };

        for (String format : formats) {
            SimpleDateFormat formatter = new SimpleDateFormat(format);
            formatter.setTimeZone(TimeZone.getTimeZone("UTC"));

            try {
                return formatter.parse(date);
            } catch (ParseException ignored) {
            }
        }

        return null;
    }
}
