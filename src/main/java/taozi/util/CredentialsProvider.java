package taozi.util;

import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import taozi.config.DetailConfiguration;

public class CredentialsProvider {
    private final static String username = DetailConfiguration.username;
    private final static String password = DetailConfiguration.password;

    private static UsernamePasswordCredentialsProvider pro = new UsernamePasswordCredentialsProvider(username, password);

    public static UsernamePasswordCredentialsProvider getCredential(){
        return pro;
    }
}
