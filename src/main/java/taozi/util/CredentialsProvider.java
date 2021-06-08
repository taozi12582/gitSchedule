package taozi.util;

import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

public class CredentialsProvider {
    private final static String username = "3181452262@qq.com";
    private final static String password = "Orangejuice135!";

    private static UsernamePasswordCredentialsProvider pro = new UsernamePasswordCredentialsProvider(username, password);

    public static UsernamePasswordCredentialsProvider getCredential(){
        return pro;
    }
}
