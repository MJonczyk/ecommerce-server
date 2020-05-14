package com.ecommerceapp.ecommerceserver.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ConfirmationToken {
    public ConfirmationToken(User user) {
        this.confirmationToken = UUID.randomUUID().toString();
        this.creationDate = new Date();
        this.user = user;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "token_id")
    private Long id;

    private String confirmationToken;

    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;
}
