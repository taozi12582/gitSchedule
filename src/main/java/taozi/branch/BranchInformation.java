package taozi.branch;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.eclipse.jgit.lib.Ref;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import taozi.exception.GitException;
import taozi.util.RefListProvider;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class BranchInformation {

    private static final String branchName = "release";
//    private static Logger log = LoggerFactory.getLogger(BranchInformation.class);

    public static Long getTimeStampFromUrl(String url) {
        return getTimeStamp(getBranchName(url));
    }

    private static List<String> getBranchName(String url) {
        Collection<Ref> refList = RefListProvider.getRefList(url);
        return refList.stream().map(Ref::getName)
                .filter(BranchInformation::matchTargetBranch).collect(Collectors.toList());
    }

    private static Long getTimeStamp(List<String> list) {
        List<Long> collect = list.stream()
                .filter(x -> {
                    Long timestamp = extractTimestampFromBranchName(x);
                    return ifNumberISTime(timestamp.toString());
                })
                .map(x -> {
                    Long timestamp = extractTimestampFromBranchName(x);
                    return BranchInformation.changeStringToTimeStamp(timestamp.toString());
                })
                .collect(Collectors.toList());
        if (collect.size() == 1) {
            return collect.get(0);
        } else {
            throw new GitException("查找数量不匹配！");
        }
    }

    private static boolean matchTargetBranch(String branchName) {
        String pattern = "(\\D*)(\\d*)";
        Pattern r = Pattern.compile(pattern);
        Matcher matcher = r.matcher(branchName);
        boolean res1 = matcher.matches();
        boolean res2 = branchName.contains("release");
        return res1 && res2;
    }

    private static boolean ifNumberISTime(String numberString) {
        String pattern = "(\\d{8})";
        Pattern r = Pattern.compile(pattern);
        Matcher matcher = r.matcher(numberString);
        return matcher.matches();
    }

    private static Long extractTimestampFromBranchName(String branchName) {
        String pattern = "(\\d*)";
        Pattern r = Pattern.compile(pattern);
        Matcher matcher = r.matcher(branchName);
        List<Long> time = new ArrayList<>();
        while (matcher.find()) {
            if (ifNumberISTime(matcher.group()) && matcher.group() != null) {
                time.add(Long.valueOf(matcher.group()));
            }
        }
        if (time.size() == 1) {
            return time.get(0);
        }
        if (time.isEmpty()) {
            throw new GitException("没有找到含有时间串与" + branchName + "的分支");
        } else {
            throw new GitException("找到多个时间串");
        }
    }

    private static Long changeStringToTimeStamp(String str) {
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            Date date = dateFormat.parse(str);
            Timestamp timeStampDate = new Timestamp(date.getTime());
            return timeStampDate.getTime() / 1000;
        } catch (ParseException e) {
            throw new GitException("时间转换错误");
        }
    }
}

