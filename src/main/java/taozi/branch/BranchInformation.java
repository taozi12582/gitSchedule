package taozi.branch;

import org.eclipse.jgit.lib.Ref;
import org.springframework.stereotype.Component;
import taozi.config.DetailConfiguration;
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

@Component
public class BranchInformation {

    RefListProvider refListProvider;

    public BranchInformation(RefListProvider refListProvider) {
        this.refListProvider = refListProvider;
    }

    public Long getTimeStampFromUrl(String url) {
        return getTimeStamp(getBranchName(url));
    }

    private List<String> getBranchName(String url) {
        Collection<Ref> refList = refListProvider.getRefList(url);
        return refList.stream().map(Ref::getName)
                .filter(this::matchTargetBranch).collect(Collectors.toList());
    }

    private Long getTimeStamp(List<String> list) {
        List<Long> collect = list.stream()
                .filter(x -> {
                    Long timestamp = extractTimestampFromBranchName(x);
                    return ifNumberISTime(timestamp.toString());
                })
                .map(x -> {
                    Long timestamp = extractTimestampFromBranchName(x);
                    return changeStringToTimeStamp(timestamp.toString());
                })
                .collect(Collectors.toList());
        if (collect.size() == 1) {
            return collect.get(0);
        } else {
            throw new GitException("查找数量不匹配！");
        }
    }

    private boolean matchTargetBranch(String branchName) {
        String pattern = "(\\D*)(\\d*)";
        Pattern r = Pattern.compile(pattern);
        Matcher matcher = r.matcher(branchName);
        boolean res1 = matcher.matches();
        boolean res2 = branchName.contains(DetailConfiguration.branchName);
        return res1 && res2;
    }

    private boolean ifNumberISTime(String numberString) {
        String pattern = "(\\d{8})";
        Pattern r = Pattern.compile(pattern);
        Matcher matcher = r.matcher(numberString);
        return matcher.matches();
    }

    private Long extractTimestampFromBranchName(String branchName) {
        String pattern = "(\\d*)";
        Pattern r = Pattern.compile(pattern);
        Matcher matcher = r.matcher(branchName);
        List<Long> time = new ArrayList<>();
        while (matcher.find()) {
            if (matcher.group() != null && ifNumberISTime(matcher.group()) ) {
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

    private Long changeStringToTimeStamp(String str) {
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

