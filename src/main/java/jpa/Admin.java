package jpa;

import jakarta.persistence.*;

@Entity
@Table(name = "ADMIN")
@DiscriminatorValue("ADMIN")
public class Admin extends User {

    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
