package com.imdbapp.datamodels.databasemodel;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class UsersFriendsId implements Serializable {
    private String requesterName;
    private String accepterName;


    public String getRequesterName() {
        return requesterName;
    }

    public void setRequesterName(String requesterName) {
        this.requesterName = requesterName;
    }

    public String getAccepterName() {
        return accepterName;
    }

    public void setAccepterName(String accepterName) {
        this.accepterName = accepterName;
    }
}
