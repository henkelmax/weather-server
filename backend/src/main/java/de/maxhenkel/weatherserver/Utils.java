package de.maxhenkel.weatherserver;

public class Utils {

    public static String getEnv(String env, String def) {
        String var = System.getenv(env);
        return var == null ? def : var;
    }

}
