package taozi.commond;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Repository;
import taozi.util.RepositoryProvider;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class CommonCommand {
    public static void main(String[] args) {

        RepositoryProvider repoProvider = new RepositoryProvider("D:\\ExampleCode\\hiworld\\.git");

        try (Repository repo = repoProvider.get();
             Git git = new Git(repo)) {
            createFileFromGitRoot(repo, "hello3.txt", "hello3");
            git.add()
                    .addFilepattern("hello3.txt")
                    .call();
            git.commit()
                    .setMessage("hello3")
                    .call();
            git.push()
                    .call();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createFileFromGitRoot(Repository repo, String filename, String content) throws FileNotFoundException {
        File hello3 = new File(repo.getDirectory().getParent(), filename);
        try (PrintWriter out = new PrintWriter(hello3)) {
            out.println(content);
        }
    }
}
