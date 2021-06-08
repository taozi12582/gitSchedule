import org.apache.commons.lang3.tuple.ImmutablePair;
import org.eclipse.jgit.lib.Ref;
import taozi.branch.BranchInformation;
import taozi.branch.MasterInformation;

public class TimeIntervalTest {
    public static void main(String[] args) {
        ImmutablePair<Long, Ref> refAndTimeFromUrl = BranchInformation.getRefAndTimeFromUrl("git@github.com:taozi12582/hello.git");
        Long left = refAndTimeFromUrl.getLeft();
//        Long masterTime = MasterInformation.getMasterTime("git@github.com:taozi12582/hello.git");
        System.out.println(left);
//        System.out.println(masterTime);
    }
}
