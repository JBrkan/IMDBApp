package com.imdbapp.datamodels.databasemodel;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class UsersFriends implements Serializable {
    @EmbeddedId
    private UsersFriendsId usersFriendsId;
    @ManyToOne
    @JoinColumn(name = "requester")
    @MapsId("requesterId")
    private Users requester;
    @ManyToOne
    @JoinColumn(name = "accepter")
    @MapsId("accepterId")
    private Users accepter;
    private String relationship;
    private Date dateBefriended;

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public Date getDateBefriended() {
        return dateBefriended;
    }

    public void setDateBefriended(Date dateBefriended) {
        this.dateBefriended = dateBefriended;
    }

    public UsersFriendsId getUsersFriendsId() {
        return usersFriendsId;
    }

    public void setUsersFriendsId(UsersFriendsId usersFriendsId) {
        this.usersFriendsId = usersFriendsId;
    }

    public Users getRequester() {
        return requester;
    }

    public void setRequester(Users requester) {
        this.requester = requester;
    }

    public Users getAccepter() {
        return accepter;
    }

    public void setAccepter(Users accepter) {
        this.accepter = accepter;
    }
}
