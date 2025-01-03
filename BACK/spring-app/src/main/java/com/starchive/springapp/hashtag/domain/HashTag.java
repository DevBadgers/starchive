package com.starchive.springapp.hashtag.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "HashTags")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class HashTag {
    @Id
    @GeneratedValue
    @Column(name = "hashTagId")
    private Long id;

    @Column(length = 32)
    private String name;

    public HashTag(String name) {
        this.name = name;
    }

    public void changeName(String name) {
        this.name = name;
    }
}
