package app.ecommerce_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.ecommerce_backend.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
