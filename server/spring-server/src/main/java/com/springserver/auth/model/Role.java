package com.springserver.auth.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "roles")
@IdClass(RoleID.class)
@Builder
public class Role {
    @Id
    @Column(name = "username")
    private String username;

    @Id
    @Column(name = "role")
    private String role;
}
