package com.keycloak.UserIdBasedAuthentication.helper;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CookieParser {
    private static final Pattern COOKIE_PATTERN = Pattern.compile("([^=]+)=([^;]*)");

    public static List<CookieData> parseCookies(List<String> cookieStrings) {
        return cookieStrings.stream()
                .map(CookieParser::parseCookie)
                .collect(Collectors.toList());
    }

    private static CookieData parseCookie(String cookieString) {
        CookieData cookie = new CookieData();
        Matcher matcher = COOKIE_PATTERN.matcher(cookieString);

        while (matcher.find()) {
            String key = matcher.group(1).trim();
            String value = matcher.group(2).trim();

            switch (key.toLowerCase()) {
                case "path":
                    cookie.setPath(value);
                    break;
                case "domain":
                    cookie.setDomain(value);
                    break;
                case "max-age":
                    cookie.setMaxAge(Integer.parseInt(value));
                    break;
                case "secure":
                    cookie.setSecure(true);
                    break;
                case "httponly":
                    cookie.setHttpOnly(true);
                    break;
                case "samesite":
                    cookie.setSameSite(value);
                    break;
                default:
                    if (cookie.getName() == null) {
                        cookie.setName(key);
                        cookie.setValue(value);
                    }
                    break;
            }
        }

        return cookie;
    }
}
