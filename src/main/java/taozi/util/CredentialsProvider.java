package taozi.util;

import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import taozi.config.Configuration;

public class CredentialsProvider {
    private final static String username = Configuration.username;
    private final static String password = Configuration.password;

    private static UsernamePasswordCredentialsProvider pro = new UsernamePasswordCredentialsProvider(username, password);

    public static UsernamePasswordCredentialsProvider getCredential(){
        return pro;
    }
}
