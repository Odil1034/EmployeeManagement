package com.example.EmployeeManagement.entity.product;

import com.example.EmployeeManagement.entity.Auditable;
import com.example.EmployeeManagement.entity.Document;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Table(name = "product_variants")
public class ProductVariant extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Embedded
    private Size size;

    private double weight;// kg
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal sellingPrice;

    @Column(precision = 10, scale = 2)
    private BigDecimal costPrice; // optional, ishchi / material xarajatlari uchun

    @Column(nullable = false)
    private int stockQuantity;

    private String color; // rangi

    @OneToMany(mappedBy = "productVariant", fetch = FetchType.EAGER)
    private List<Document> images; // optional: variant rasmi

    @Column(length = 1000)
    private String description; // optional: qo‘shimcha info, material, color

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal price; // standart narx

    @Column(length = 100)
    private String material; // yog‘och, MDF, metal, plastic

    @Builder.Default
    @Column(nullable = false)
    private boolean isActive = true; // Product mavjudmi yoki arxivlangan
}
