package taozi.ScheduleCheck;

import taozi.branch.BranchInformation;
import taozi.branch.MasterInformation;
import taozi.verifier.TimeIntervalVerifier;

import java.util.Timer;
import java.util.TimerTask;

public class TimeTask {
    private static final Long taskInterval = 5000L;
    private static final String url = "git@github.com:taozi12582/hello.git";

    public static void main(String[] args) {
        new Timer("gitSchedule").schedule(new TimerTask() {
            @Override
            public void run() {
                Long brandTime = BranchInformation.getTimeStampFromUrl(url);
                Long masterTime = MasterInformation.getMasterTime(url);
                System.out.println("brandTime: " + brandTime + "\t" + "masterTime: " + masterTime);
                System.out.println(TimeIntervalVerifier.ifNeedWarn(brandTime, masterTime));
            }
        }, 0, taskInterval);

    }

}
