// 
// Decompiled by Procyon v0.6.0
// 

package ru.korusconsulting.dmservice.integration.impl.connections;

import org.apache.http.message.BufferedHeader;
import org.apache.http.util.EncodingUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.HttpRequest;
import org.apache.http.auth.Credentials;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.CharArrayBuffer;
import org.apache.http.FormattedHeader;
import org.apache.http.auth.MalformedChallengeException;
import org.apache.http.util.Args;
import org.apache.http.Header;
import org.apache.http.Consts;
import org.apache.http.auth.ChallengeState;
import java.nio.charset.Charset;
import org.apache.http.impl.auth.RFC2617Scheme;

public class BasicForNtlmScheme extends RFC2617Scheme
{
    private static final long serialVersionUID = -1931571557597830536L;
    private boolean complete;
    
    public BasicForNtlmScheme(final Charset credentialsCharset) {
        super(credentialsCharset);
        this.complete = false;
    }
    
    @Deprecated
    public BasicForNtlmScheme(final ChallengeState challengeState) {
        super(challengeState);
    }
    
    public BasicForNtlmScheme() {
        this(Consts.ASCII);
    }
    
    public String getSchemeName() {
        return "basic";
    }
    
    public void processChallenge(final Header header) throws MalformedChallengeException {
        Args.notNull((Object)header, "Header");
        final String authheader = header.getName();
        if (authheader.equalsIgnoreCase("WWW-Authenticate")) {
            this.challengeState = ChallengeState.TARGET;
        }
        else {
            if (!authheader.equalsIgnoreCase("Proxy-Authenticate")) {
                throw new MalformedChallengeException("Unexpected header name: " + authheader);
            }
            this.challengeState = ChallengeState.PROXY;
        }
        CharArrayBuffer buffer;
        int pos;
        if (header instanceof final FormattedHeader formattedHeader) {
            buffer = formattedHeader.getBuffer();
            pos = ((FormattedHeader)header).getValuePos();
        }
        else {
            final String s = header.getValue();
            if (s == null) {
                throw new MalformedChallengeException("Header value is null");
            }
            buffer = new CharArrayBuffer(s.length());
            buffer.append(s);
            pos = 0;
        }
        while (pos < buffer.length() && HTTP.isWhitespace(buffer.charAt(pos))) {
            ++pos;
        }
        final int beginIndex = pos;
        while (pos < buffer.length() && !HTTP.isWhitespace(buffer.charAt(pos))) {
            ++pos;
        }
        final int endIndex = pos;
        this.parseChallenge(buffer, pos, buffer.length());
        this.complete = true;
    }
    
    public boolean isComplete() {
        return this.complete;
    }
    
    public boolean isConnectionBased() {
        return false;
    }
    
    @Deprecated
    public Header authenticate(final Credentials credentials, final HttpRequest request) throws AuthenticationException {
        return this.authenticate(credentials, request, (HttpContext)new BasicHttpContext());
    }
    
    String getCredentialsCharset(final HttpRequest request) {
        String charset = (String)request.getParams().getParameter("http.auth.credential-charset");
        if (charset == null) {
            charset = this.getCredentialsCharset().name();
        }
        return charset;
    }
    
    public Header authenticate(final Credentials credentials, final HttpRequest request, final HttpContext context) throws AuthenticationException {
        Args.notNull((Object)credentials, "Credentials");
        Args.notNull((Object)request, "HTTP request");
        final StringBuilder tmp = new StringBuilder();
        tmp.append(credentials.getUserPrincipal().getName());
        tmp.append(":");
        tmp.append((credentials.getPassword() == null) ? "null" : credentials.getPassword());
        final Base64 base64codec = new Base64(0);
        final byte[] base64password = base64codec.encode(EncodingUtils.getBytes(tmp.toString(), this.getCredentialsCharset(request)));
        final CharArrayBuffer buffer = new CharArrayBuffer(32);
        if (this.isProxy()) {
            buffer.append("Proxy-Authorization");
        }
        else {
            buffer.append("Authorization");
        }
        buffer.append(": Basic ");
        buffer.append(base64password, 0, base64password.length);
        return (Header)new BufferedHeader(buffer);
    }
    
    @Deprecated
    public static Header authenticate(final Credentials credentials, final String charset, final boolean proxy) {
        Args.notNull((Object)credentials, "Credentials");
        Args.notNull((Object)charset, "charset");
        final StringBuilder tmp = new StringBuilder();
        tmp.append(credentials.getUserPrincipal().getName());
        tmp.append(":");
        tmp.append((credentials.getPassword() == null) ? "null" : credentials.getPassword());
        final byte[] base64password = Base64.encodeBase64(EncodingUtils.getBytes(tmp.toString(), charset), false);
        final CharArrayBuffer buffer = new CharArrayBuffer(32);
        if (proxy) {
            buffer.append("Proxy-Authorization");
        }
        else {
            buffer.append("Authorization");
        }
        buffer.append(": Basic ");
        buffer.append(base64password, 0, base64password.length);
        return (Header)new BufferedHeader(buffer);
    }
    
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("BASIC [complete=").append(this.complete).append("]");
        return builder.toString();
    }
}
