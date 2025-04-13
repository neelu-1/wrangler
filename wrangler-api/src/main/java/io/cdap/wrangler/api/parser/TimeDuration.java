package io.cdap.wrangler.api.parser;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public class TimeDuration implements Token {
    private final String original;
    private final long millis;

    public TimeDuration(String value) {
        this.original = value;
        this.millis = parseTime(value);
    }

    private long parseTime(String value) {
        value = value.trim().toLowerCase();

        if (value.endsWith("ms")) {
            return (long)Double.parseDouble(value.replace("ms", ""));
        } else if (value.endsWith("sec") || value.endsWith("s")) {
            return (long)(Double.parseDouble(value.replaceAll("(sec|s)$", "")) * 1000);
        } else if (value.endsWith("min") || value.endsWith("m")) {
            return (long)(Double.parseDouble(value.replaceAll("(min|m)$", "")) * 60 * 1000);
        } else {
            throw new IllegalArgumentException("Invalid time duration: " + value);
        }
    }

    public long getMillis() {
        return millis;
    }

    @Override
    public Object value() {
        return millis;
    }

    @Override
    public TokenType type() {
        return TokenType.TIME_DURATION;
    }

    @Override
    public JsonElement toJson() {
        return new JsonPrimitive(millis);
    }
}
