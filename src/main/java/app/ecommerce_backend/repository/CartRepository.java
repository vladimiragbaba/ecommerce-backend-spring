package app.ecommerce_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.ecommerce_backend.model.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

}
