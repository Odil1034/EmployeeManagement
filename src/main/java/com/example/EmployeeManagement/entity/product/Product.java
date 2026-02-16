package com.example.EmployeeManagement.entity.product;

import com.example.EmployeeManagement.entity.Auditable;
import com.example.EmployeeManagement.entity.Document;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
@SuperBuilder(toBuilder = true)
public class Product extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; // Stol, Kursi, Cabinet, etc

    @OneToMany(mappedBy = "product")
    private List<Document> images; // Product rasmi (S3 URL orqali)

    @ManyToOne
    @JoinColumn(name = "category_id")
    private ProductCategory category; // optional, agar bir nechta product category bo‘lsa

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductVariant> variants;
}
