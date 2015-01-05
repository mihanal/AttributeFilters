package main.java.com.wildcard;

/**
 * Created by Mihan.Liyanage on 12/27/2014.
 */
public class WildCardMatcher {

    public String wildcardToRegex(String wildcard){

        StringBuffer stringBuffer = new StringBuffer(wildcard.length());
        stringBuffer.append('^');

        for (int i = 0, is = wildcard.length(); i < is; i++) {
            char c = wildcard.charAt(i);
            switch(c) {
                case '*':
                    stringBuffer.append(".*");
                    break;
                case '?':
                    stringBuffer.append(".");
                    break;
                // escape special regexp-characters
                case '(': case ')': case '[': case ']': case '$':
                case '^': case '.': case '{': case '}': case '|':
                case '\\':
                    stringBuffer.append("\\");
                    stringBuffer.append(c);
                    break;
                default:
                    stringBuffer.append(c);
                    break;
            }
        }
        stringBuffer.append('$');
        return(stringBuffer.toString());
    }
}
