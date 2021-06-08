package taozi.util;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import taozi.exception.GitException;

import java.util.Collection;

public class RefListProvider {

    public static Collection<Ref> getRefList(String url) {
        UsernamePasswordCredentialsProvider credential = CredentialsProvider.getCredential();

        try {
            return Git.lsRemoteRepository().setRemote(url).setCredentialsProvider(credential).call();
        } catch (GitAPIException e) {
            throw new GitException("查询失败");
        }
    }
}
