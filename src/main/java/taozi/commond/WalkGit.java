package taozi.commond;

import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import taozi.util.RepositoryProvider;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class WalkGit {

    private static RepositoryProvider repoProvider = new RepositoryProvider("D:\\ExampleCode\\hiworld\\.git");

    public static void main(String[] args) {
        try (Repository repo = repoProvider.get()) {
            System.out.println(repo.getBranch());
            Ref ref = repo.getAllRefs().get(Constants.HEAD);
            try (RevWalk revWalk = new RevWalk(repo)) {
                RevCommit lastCommit = revWalk.parseCommit(ref.getObjectId());
                int commitTime = lastCommit.getCommitTime();
                System.out.println(LocalDateTime.ofEpochSecond(commitTime, 0, ZoneOffset.UTC));
//                revWalk.markStart(lastCommit);
//                revWalk.forEach(c -> {
//                    System.out.println("commit " + c.getName());
//                    System.out.printf("Author: %s <%s>\n", c.getAuthorIdent().getName(), c.getAuthorIdent().getEmailAddress());
//                    System.out.println("Date: " + LocalDateTime.ofEpochSecond(c.getCommitTime(), 0, ZoneOffset.UTC));
//                    System.out.println("\t" + c.getShortMessage() + "\n");
//                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
