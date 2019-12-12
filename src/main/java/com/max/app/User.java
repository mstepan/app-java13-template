package com.max.app;

import com.github.davidmoten.guavamini.Objects;

public class User {

    private final String username;

    public User(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        User other = (User) obj;

        return Objects.equal(other, other.username);
    }

    @Override
    public int hashCode() {
        return 133; //Objects.hashCode(username);
    }
}
