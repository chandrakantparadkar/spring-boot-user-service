package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false, length = 255)
    private String password; // plain text as requested

    @Column(length = 255)
    private String email;

    @Column(nullable = false)
    private Boolean enabled = true;


    @Column(name = "roles", length = 1024)
    private String roles; // e.g., "admin,report-viewer"

    @Column(name = "groups", length = 1024)
    private String groups; // e.g., "sales,india"

    @Column(name = "permissions", length = 2048)
    private String permissions; // e.g., "user.read,user.write"

}
