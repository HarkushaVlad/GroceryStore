package com.vhark.grocerystore.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class PropertiesLoader {

  private PropertiesLoader() {}
  ;

  private static final Properties PROPERTIES = new Properties();

  static {
    loadProperties();
  }

  public static String get(String key) {
    return PROPERTIES.getProperty(key);
  }

  private static void loadProperties() {
    try (InputStream inputStream =
        PropertiesLoader.class.getClassLoader().getResourceAsStream("application.properties")) {
      PROPERTIES.load(inputStream);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
