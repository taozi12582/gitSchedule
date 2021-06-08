package taozi.verifier;

public class TimeIntervalVerifier {
    public static long timeInterval = 100000;

    public static boolean ifNeedWarn(Long release, Long master) {
        return (release - master) > timeInterval;
    }
}
