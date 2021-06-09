package taozi.ScheduleCheck;

import org.springframework.stereotype.Component;
import taozi.TTApi.TTPost;
import taozi.branch.BranchInformation;
import taozi.branch.MasterInformation;
import taozi.config.DetailConfiguration;
import taozi.verifier.TimeIntervalVerifier;
import java.util.Timer;
import java.util.TimerTask;
import static taozi.config.DetailConfiguration.*;

@Component
public class TimeTask {
    private static final Long taskInterval = DetailConfiguration.taskInterval;
    private static final String url = DetailConfiguration.gitUrl;
    final TTPost ttPost;
    final BranchInformation branchInformation;
    final MasterInformation masterInformation;

    public TimeTask(BranchInformation branchInformation, MasterInformation masterInformation, TTPost ttPost) {
        new Timer("gitSchedule").schedule(new TimerTask() {
            @Override
            public void run() {
                Long brandTime = branchInformation.getTimeStampFromUrl(url);
                Long masterTime = masterInformation.getMasterTime(url);
                System.out.println("brandTime: " + brandTime + "\t" + "masterTime: " + masterTime);
                System.out.println(TimeIntervalVerifier.ifNeedWarn(brandTime, masterTime));
                ttPost.doPost(TTApiUrl);
            }
        }, 0, taskInterval);
        this.branchInformation = branchInformation;
        this.masterInformation = masterInformation;
        this.ttPost = ttPost;
    }


}
