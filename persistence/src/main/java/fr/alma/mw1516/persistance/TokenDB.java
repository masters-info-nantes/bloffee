package fr.alma.mw1516.persistance;

import java.io.Serializable;
import java.util.Date;

/**
 * Created on 12/4/15.
 *
 * @author dralagen
 */
public class TokenDB implements Serializable {
    private String userId;
    private Date expireDate;

    public TokenDB() {
    }

    public TokenDB(String userId, Date expireDate) {
        this.userId = userId;
        this.expireDate = expireDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }
}
