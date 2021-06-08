package taozi.util;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

public class RepositoryProvider {

    private String clientPath;

    public RepositoryProvider(String clientPath) {
        this.clientPath = clientPath;
    }

    public RepositoryProvider(){}

    public Repository get() throws Exception {
        try (Repository repo = new FileRepository(clientPath)) {
            return repo;
        }
    }

    public Repository getByUrl(String url){
        UsernamePasswordCredentialsProvider credential = CredentialsProvider.getCredential();
        return Git.lsRemoteRepository().setRemote(url).setCredentialsProvider(credential).getRepository();
    }
}
