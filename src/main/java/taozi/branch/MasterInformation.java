package taozi.branch;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.springframework.stereotype.Component;
import taozi.exception.GitException;
import taozi.util.RefListProvider;
import taozi.util.RepositoryProvider;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static taozi.config.DetailConfiguration.*;


@Component
public class MasterInformation {

    RepositoryProvider repoProvider;
    RefListProvider refListProvider;

    public MasterInformation(RepositoryProvider repoProvider, RefListProvider refListProvider) {
        this.repoProvider = repoProvider;
        this.refListProvider = refListProvider;
    }

    public Long getMasterTime(String url) {
        Ref ref = getBranchNameWithRefMap(url);
        try {
            Repository repository = repoProvider.get();
            Git git = new Git(repository);
            repository = git.reset().setRef(masterName).getRepository();
            RevWalk revWalk = new RevWalk(repository);
            RevCommit lastCommit = revWalk.parseCommit(ref.getObjectId());
            int commitTime = lastCommit.getCommitTime();
            return (long) commitTime;
        } catch (Exception e) {
            e.printStackTrace();
            throw new GitException("读取master分支错误");
        }
    }

    private Ref getBranchNameWithRefMap(String url) {
        Collection<Ref> refList = refListProvider.getRefList(url);
        List<Ref> collect = refList.stream().filter(x -> matchTargetBranch(x.getName()))
                .collect(Collectors.toList());
        if (collect.size() == 1) {
            return collect.get(0);
        }
        if (collect.isEmpty()) {
            throw new GitException("没有找到名为" + masterName + "的分支");
        }
        throw new GitException("找到多个名为" + masterName + "的分支");
    }

    private  boolean matchTargetBranch(String name) {
        return name.contains(masterName);
    }


}
