package com.imdbapp.datamodels.databasemodel;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UsersFriendsId implements Serializable {
    private Long requesterId;
    private Long accepterId;


    public Long getRequesterId() {
        return requesterId;
    }

    public void setRequesterId(Long requesterId) {
        this.requesterId = requesterId;
    }

    public Long getAccepterId() {
        return accepterId;
    }

    public void setAccepterId(Long accepterId) {
        this.accepterId = accepterId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UsersFriendsId)) return false;
        UsersFriendsId that = (UsersFriendsId) o;
        return getRequesterId().equals(that.getRequesterId()) &&
                getAccepterId().equals(that.getAccepterId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRequesterId(), getAccepterId());
    }
}
