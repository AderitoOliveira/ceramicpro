package nuvemdesoftware.ceramicpro.repository;

import nuvemdesoftware.ceramicpro.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductsRepository extends  CrudRepository<Product, Long>, PagingAndSortingRepository<Product, Long> {

    Page<Product> findByCustomerProductId(String customerProductId, Pageable pageable);

    @Query("select prod from Product prod where customerProductId like CONCAT(:searchValue,'%') or productName like CONCAT(:searchValue,'%') or internalProductId like CONCAT(:searchValue,'%')")
    //@Query("select prod from Product prod where prod.customerProductId like CONCAT(:searchValue,'%')")
    Page<Product> findByCustProdIdProdNameInternalProdId(String searchValue, Pageable pageable);

    Product findByCustomerProductId(String customerProductId);
}
