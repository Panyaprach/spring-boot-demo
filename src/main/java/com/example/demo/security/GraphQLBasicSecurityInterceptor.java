package com.example.demo.security;

import org.springframework.graphql.server.WebGraphQlInterceptor;
import org.springframework.graphql.server.WebGraphQlRequest;
import org.springframework.graphql.server.WebGraphQlResponse;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;

public class GraphQLBasicSecurityInterceptor implements WebGraphQlInterceptor {

    protected final AuthenticationProvider authenticator;
    protected final Base64.Decoder decoder;

    public GraphQLBasicSecurityInterceptor(UserDetailsService service, PasswordEncoder encoder) {
        BasicAuthenticationProvider authenticator = new BasicAuthenticationProvider();
        authenticator.setUserDetailsService(service);
        authenticator.setPasswordEncoder(encoder);
        this.authenticator = authenticator;
        this.decoder = Base64.getDecoder();
    }

    @Override
    public Mono<WebGraphQlResponse> intercept(WebGraphQlRequest request, Chain chain) {
        String header = getAuthorizationHeader(request);

        if (!StringUtils.startsWithIgnoreCase(header, "Basic")) {

            return chain.next(request);
        } else if (header.equalsIgnoreCase("Basic")) {

            throw new BadCredentialsException("Empty basic authentication token");
        } else {

            byte[] base64Token = header.substring(6).getBytes(StandardCharsets.UTF_8);
            byte[] decoded = decoder.decode(base64Token);
            String token = new String(decoded, StandardCharsets.UTF_8);
            int delim = token.indexOf(":");

            if (delim == -1) {

                throw new BadCredentialsException("Invalid basic authentication token");
            } else {

                UsernamePasswordAuthenticationToken result = UsernamePasswordAuthenticationToken
                        .unauthenticated(token.substring(0, delim), token.substring(delim + 1));
                authenticator.authenticate(result);
            }

            return chain.next(request);
        }
    }

    protected String getAuthorizationHeader(WebGraphQlRequest request) {
        return Optional.ofNullable(request.getHeaders().get("Authorization"))
                .stream().map(i -> i.get(0))
                .map(String::trim)
                .findAny()
                .orElse(null);
    }

}
