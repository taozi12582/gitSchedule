package taozi.verifier;

import taozi.config.DetailConfiguration;

public class TimeIntervalVerifier {
    public static long timeInterval = DetailConfiguration.timeInterval;

    public static boolean ifNeedWarn(Long release, Long master) {
        return (release - master) > timeInterval;
    }
}
