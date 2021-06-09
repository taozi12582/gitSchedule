package taozi.ScheduleCheck;

import taozi.TTApi.TTPost;
import taozi.branch.BranchInformation;
import taozi.branch.MasterInformation;
import taozi.config.DetailConfiguration;
import taozi.verifier.TimeIntervalVerifier;
import java.util.Timer;
import java.util.TimerTask;

import static taozi.config.DetailConfiguration.*;

public class TimeTask {
    private static final Long taskInterval = DetailConfiguration.taskInterval;
    private static final String url = DetailConfiguration.gitUrl;

    public static void main(String[] args) {
        new Timer("gitSchedule").schedule(new TimerTask() {
            @Override
            public void run() {
                Long brandTime = BranchInformation.getTimeStampFromUrl(url);
                Long masterTime = MasterInformation.getMasterTime(url);
                System.out.println("brandTime: " + brandTime + "\t" + "masterTime: " + masterTime);
                System.out.println(TimeIntervalVerifier.ifNeedWarn(brandTime, masterTime));
                TTPost.doPost(TTApiUrl);
            }
        }, 0, taskInterval);

    }




}
