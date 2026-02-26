package com.vitube.online_learning.entity;

import com.vitube.online_learning.enums.GenderEnum;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private LocalDate dob;

//    @Enumerated(EnumType.STRING)
//    @Column(name = "gender")
//    private GenderEnum gender;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "gender", columnDefinition = "gender_enum")
    private GenderEnum gender;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

//    private String bankName;
//    private String accountNumber;
//    private String accountName;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_name")
    )

    private List<Role> roles;

    @OneToMany(mappedBy = "instructor")
    private Set<Course> courses;

    @OneToMany(mappedBy = "student")
    private Set<Register> registers;

    @OneToMany(mappedBy = "instructor")
    private List<InstructorStatic> instructorStatics;

    @OneToMany(mappedBy = "user")
    private Set<LessonProgress> lessonProgresses;


//    @OneToMany(mappedBy = "student")
//    private Set<Order> orders;

    //    @OneToMany(mappedBy = "instructor")
    //    private Set<InstructorStatic> instructorStatics;
}
