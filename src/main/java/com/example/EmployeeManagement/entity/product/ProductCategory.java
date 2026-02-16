package com.example.EmployeeManagement.entity.product;

import com.example.EmployeeManagement.entity.Auditable;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Table(name = "product_categories")
public class ProductCategory extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name; // Furniture, Wood, Metal, Paint

    @Column(length = 1000)
    private String description; // optional, category haqida qo‘shimcha info

    @OneToMany(mappedBy = "category")
    private List<Product> products;
}
