package taozi.verifier;

import taozi.config.Configuration;

public class TimeIntervalVerifier {
    public static long timeInterval = Configuration.timeInterval;

    public static boolean ifNeedWarn(Long release, Long master) {
        return (release - master) > timeInterval;
    }
}
