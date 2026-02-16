package com.example.EmployeeManagement.entity;

import com.example.EmployeeManagement.entity.product.Product;
import com.example.EmployeeManagement.entity.product.ProductVariant;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "documents")
@SuperBuilder(toBuilder = true)
public class Document extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "file_name", nullable = false)
    private String fileName;  // original file name

    @Column(name = "url", nullable = false)
    private String url;       // S3 yoki storage link

    private String extension; // image/png, pdf, docx, etc

    private Long size;        // fayl hajmi byte

    @Column(name = "generated_name", unique = true, nullable = false, updatable = false)
    private String generatedName;

    @OneToOne(mappedBy = "profileImage", fetch = FetchType.LAZY)
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    private ProductVariant productVariant;
}
