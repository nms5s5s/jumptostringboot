package com.example.jumptostringboot.user;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class SiteUser {
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE)
        private Long id;

        @Column(unique = true)
        private String username;

        private String nickname;

        private String password;

        @Column(unique = true)
        private String email;

        private String role;

        @PrePersist
        public void setting() {
                this.role = "ROLE_USER";
        }

        public String getRole() {
                return role;
        }
        public void setRole(String role) {
                this.role = role;
        }
}
