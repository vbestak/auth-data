package hr.vbestak.authclient.util;

public class AuthUtil {
    public static final String TOKEN_TYPE = "Bearer ";

    public static String getToken(String token) {
        if(token.startsWith(TOKEN_TYPE)) {
            return token.substring(TOKEN_TYPE.length());
        }

        return "";
    }
}
