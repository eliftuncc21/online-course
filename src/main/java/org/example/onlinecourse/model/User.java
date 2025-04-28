package org.example.onlinecourse.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Table(name = "app_users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "telephone_number", nullable = false,length = 11)
    private String telephoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "is_active", nullable = false)
    private ActiveStatus isActive= ActiveStatus.ACTIVE;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<Admin> admins;

    @OneToMany(mappedBy = "user")
    private List<Instructor> instructors;

    @OneToMany(mappedBy = "user")
    private List<Student> students;

    @Override
    public String getUsername() {
        return "";
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }    

}
