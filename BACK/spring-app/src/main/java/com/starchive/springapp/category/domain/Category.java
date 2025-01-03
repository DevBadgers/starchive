package com.starchive.springapp.category.domain;

import static jakarta.persistence.FetchType.LAZY;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "Categories")
public class Category {
    @Id
    @GeneratedValue
    @Column(name = "categoryId")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parentId")
    private Category parent;

    @Column(length = 100)
    String name;

    @OneToMany(mappedBy = "parent", fetch = LAZY)
    private List<Category> children = new ArrayList<>();

    public Category(String name, Category parent) {
        this.name = name;
        this.parent = parent;
        if (parent != null) {
            changeParent(parent);
        }
    }

    private void changeParent(Category parent) {
        this.parent = parent;
        parent.getChildren().add(this);
    }

}
