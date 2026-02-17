package com.example.EmployeeManagement.entity.product;

import com.example.EmployeeManagement.entity.Auditable;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Entity
@Table(name = "product_categories")
@SQLDelete(sql = "UPDATE product_categories SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")
public class ProductCategory extends Auditable {

    @Column(nullable = false, unique = true)
    private String name; // Furniture, Wood, Metal, Paint

    @Column(length = 1000)
    private String description; // optional, category haqida qo‘shimcha info

    @OneToMany(mappedBy = "category")
    private List<Product> products;
}
