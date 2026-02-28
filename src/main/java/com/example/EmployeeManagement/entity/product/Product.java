package com.example.EmployeeManagement.entity.product;

import com.example.EmployeeManagement.entity.Auditable;
import com.example.EmployeeManagement.entity.Document;
import com.example.EmployeeManagement.entity.work.Work;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@Entity
@Table(name = "products")
@SQLDelete(sql = "UPDATE products SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")
public class Product extends Auditable {

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
