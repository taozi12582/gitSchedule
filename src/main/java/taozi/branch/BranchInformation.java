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
    private static Logger log = LoggerFactory.getLogger(BranchInformation.class);

//    public static void getRemoteBranches(String url, String username, String password) {
//        try {
//            Collection<Ref> refList;
//            if (StringUtils.isNotEmpty(username) && StringUtils.isNotEmpty(password)) {
//                UsernamePasswordCredentialsProvider pro = new UsernamePasswordCredentialsProvider(username, password);
//                refList = Git.lsRemoteRepository().setRemote(url).setCredentialsProvider(pro).call();
//            } else {
//                refList = Git.lsRemoteRepository().setRemote(url).call();
//            }
//            List<String> branchnameList = new ArrayList<>(4);
//            for (Ref ref : refList) {
//                String refName = ref.getName();
//                if (refName.startsWith("refs/heads/")) {                       //需要进行筛选
//                    String branchName = refName.replace("refs/heads/", "");
//                    branchnameList.add(branchName);
//                }
//            }
//
//            System.out.println("共用分支" + branchnameList.size() + "个");
//            branchnameList.forEach(System.out::println);
//        } catch (Exception e) {
//            System.out.println("error");
//        }
//    }

//    public static void main(String[] args) {
//        getRemoteBranches("git@github.com:taozi12582/hello.git", "3181452262@qq.com", "Orangejuice135!");
//    }

    public static ImmutablePair<Long, Ref> getRefAndTimeFromUrl(String url){
        return getTimeStampWithRef(getBranchNameWithRefMap(url));
    }

    private static List<ImmutablePair<String, Ref>> getBranchNameWithRefMap(String url) {
        Collection<Ref> refList = RefListProvider.getRefList(url);
        return refList.stream().filter(x -> matchTargetBranch(x.getName()))
                .map(x -> {
                    return new ImmutablePair<String, Ref>(x.getName(), x);
                }).collect(Collectors.toList());
    }

    private static ImmutablePair<Long, Ref> getTimeStampWithRef(List<ImmutablePair<String, Ref>> list) {
        List<ImmutablePair<Long, Ref>> pairList = list.stream().filter(x -> {
            Long timestamp = extractTimestampFromBranchName(x.getLeft());
            return ifNumberISTime(timestamp.toString());
        }).map(x -> {
            Long timeStamp = changeStringToTimeStamp(x.getLeft());
            return new ImmutablePair<Long, Ref>(timeStamp, x.getRight());
        }).collect(Collectors.toList());
        if(pairList.size() == 1){
            return pairList.get(0);
        }else {
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
                time.add(changeStringToTimeStamp(matcher.group()));
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
            System.out.println(timeStampDate.getTime());
            return timeStampDate.getTime();
        } catch (ParseException e) {
            System.out.println(e);
        }
        return null;
    }
}

