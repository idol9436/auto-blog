package com.sangsang.autoblog.adapter.out.persistence.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_history")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "origin_user_id", nullable = false)
    private UserOriginEntity user;

    @Column(name = "action", nullable = false)
    private String action;

    @Column(name = "action_at", nullable = false)
    private LocalDateTime actionAt;

    public UserHistoryEntity(UserOriginEntity user, String action) {
        this.user = user;
        this.action = action;
        this.actionAt = LocalDateTime.now();
    }

    // TODO public static UserHistoryEntity fromDomain(User user){}
}
