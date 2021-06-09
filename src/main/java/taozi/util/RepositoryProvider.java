package taozi.util;

import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Repository;

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
}
