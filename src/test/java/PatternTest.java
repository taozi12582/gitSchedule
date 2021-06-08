
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternTest {
//    public static void main(String[] args) {
//        String pattern = "(\\D*)(\\d*)";
//        String branchName = "branch/release/20200716s";
//        Pattern r = Pattern.compile(pattern);
//        Matcher matcher = r.matcher(branchName);
////        System.out.println(matcher.matches());
////        System.out.println(branchName.contains("release"));
//        String pattern1 = "(\\d*)";
//        Pattern r1 = Pattern.compile(pattern1);
//        Matcher matcher1 = r1.matcher(branchName);
//        while (matcher1.find()){
//            System.out.println(matcher1.group()+"*****");
//        }
//    }

    private List<Long> extractTimestampFromBranchName(String branchName) {
        String pattern = "(\\d*)";
        Pattern r = Pattern.compile(pattern);
        Matcher matcher = r.matcher(branchName);
        List<Long> time = new ArrayList<>();
        while (matcher.find()) {
            if (ifNumberISTime(matcher.group()) && matcher.group() != null) {
                time.add(Long.valueOf(matcher.group()));
            }
        }
        return time;

    }

    private static Long changeStringToTimeStamp(String str){
        Timestamp timeStampDate = null;
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            Date date = dateFormat.parse(str);
            timeStampDate = new Timestamp(date.getTime());
            System.out.println(timeStampDate.getTime());
        } catch (ParseException e) {
            System.out.println(e);
        }
        assert timeStampDate != null;
        return timeStampDate.getTime();
    }

    private boolean ifNumberISTime(String numberString) {
        String pattern = "(\\d{8})";
        Pattern r = Pattern.compile(pattern);
        Matcher matcher = r.matcher(numberString);
        return matcher.matches();
    }

    public static void main(String[] args) {
//        changeStringToTimeStamp("20200716");
        System.out.println(matchTargetBranch("orange/haha/asdasdasd"));
    }

    private static boolean matchTargetBranch(String name) {
        Pattern r = Pattern.compile("orange/haha");
        Matcher matcher = r.matcher(name);
        return matcher.matches();
    }
}
