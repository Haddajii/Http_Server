package com.haddaji.httpserver.http;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum HttpVersion {
    HTTP_1_1("HTTP/1.1", 1, 1), ;

    public final String LITERAL;
    public final int MAJOR ;
    public final int MINOR ;

    HttpVersion(String LITERAL, int MAJOR, int MINOR) {
        this.LITERAL = LITERAL;
        this.MAJOR = MAJOR;
        this.MINOR = MINOR;
    }

    public static final Pattern pattern = Pattern.compile("^HTTP/(?<major>\\d+)\\.(?<minor>\\d+)" );

    public static HttpVersion getBestCompatibleVersion(String LiteralVersion) throws BadHttpVersionException {
        Matcher matcher = pattern.matcher(LiteralVersion);
        if(!matcher.find()){
            throw new BadHttpVersionException();
        }
        int major = Integer.parseInt(matcher.group("major"));
        int minor = Integer.parseInt(matcher.group("minor"));

        HttpVersion bestVersion = null;

        for(HttpVersion version : HttpVersion.values()){
            if(version.LITERAL.equals(LiteralVersion)){
                return version;
            }
            else{
                if(version.MAJOR == major){
                    if(version.MINOR <= minor){
                        bestVersion = version;
                    }
                }
            }
        }
        return bestVersion;
    }
}
