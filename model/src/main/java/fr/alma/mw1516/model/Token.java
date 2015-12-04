package fr.alma.mw1516.model;

import java.util.Date;

public class Token {
    private String token;
    private Date expired;

    public Token() {
    }

    public Token(String tokenId, Date expireDate) {
        token = tokenId;
        expired = expireDate;
    }

    public Token(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpired() {
        return expired;
    }

    public void setExpired(Date expired) {
        this.expired = expired;
    }
}
