package com.deltadc.quizletclone.user;


import com.deltadc.quizletclone.set.Set;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data // sinh ra getter, setter, toString,... tu dong
@Builder // tao doi tuong linh hoat hon (khong can dung vi tri tham so)
@NoArgsConstructor // tao constructor ko doi tu dong
@AllArgsConstructor
@Entity // danh dau la entity trong database
@Table(name = "user") // table voi ten la "user"
public class User {

    // danh dau la id va auto_increment
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;

    private String username;

    private String email;

    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Set> sets;

}
