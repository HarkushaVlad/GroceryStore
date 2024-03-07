package com.vhark.grocerystore.database.constants;

import com.vhark.grocerystore.util.PropertiesLoader;

public enum DbConfiguration {
    HOST(PropertiesLoader.get("db.host")),
    PORT(PropertiesLoader.get("db.port")),
    NAME(PropertiesLoader.get("db.name"));

    private final String value;

    DbConfiguration(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
