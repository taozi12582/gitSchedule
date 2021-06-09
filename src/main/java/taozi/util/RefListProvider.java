package taozi.util;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.stereotype.Component;
import taozi.exception.GitException;

import java.util.Collection;


@Component
public class RefListProvider {

    UsernamePasswordCredentialsProvider credential;

    public RefListProvider(UsernamePasswordCredentialsProvider credential) {
        this.credential = credential;
    }

    public Collection<Ref> getRefList(String url) {
        try {
            return Git.lsRemoteRepository().setRemote(url).setCredentialsProvider(credential).call();
        } catch (GitAPIException e) {
            throw new GitException("查询失败");
        }
    }
}
