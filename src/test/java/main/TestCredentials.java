package main;

public class TestCredentials {
    public static String getCredential(String key) {
        String value = System.getProperty(key);

        if (value == null || value.isEmpty()) {
            String envVar = key.toUpperCase().replace('.', '_');
            value = System.getenv(envVar);
        }

        if (value == null || value.isEmpty()) {
            throw new IllegalStateException(key + " not configured. " +
                    "Set via -D" + key + "=value or " +
                    key.toUpperCase().replace('.', '_') + " environment variable");
        }
        return value;
    }

    public static String getLoginPassword() {
        return getCredential("testLoginPassword");
    }
}
