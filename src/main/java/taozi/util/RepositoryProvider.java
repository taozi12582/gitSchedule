package taozi.util;

import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Repository;
import taozi.exception.GitException;

public class RepositoryProvider {

    String clientPath;

    public RepositoryProvider(String clientPath) {
        this.clientPath = clientPath;
    }


    public Repository get() throws Exception {
        try {
            Repository repo = new FileRepository(clientPath);
            return repo;
        }catch (Exception e){
            throw new GitException("仓库获取失败");
        }
    }
}
