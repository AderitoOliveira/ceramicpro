package nuvemdesoftware.ceramicpro.repository;

import nuvemdesoftware.ceramicpro.model.Stock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface StockRepository extends PagingAndSortingRepository<Stock, Long> {

    Page<Stock> findByProductId(String productId, Pageable pageable);

    @Query("select prod from Stock prod where product_id like CONCAT(:searchValue,'%') or product_name like CONCAT(:searchValue,'%')")
    Page<Stock> findByProdIdProdName(String searchValue, Pageable pageable);

    Stock findByProductId(String productId);

    List<Stock> findAll();

    Stock findAllByBarCodeNumber(String barCodeNumber);
}
