package nuvemdesoftware.ceramicpro.repository;

import nuvemdesoftware.ceramicpro.model.Client;
import nuvemdesoftware.ceramicpro.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ClientRepository extends PagingAndSortingRepository<Client, Long> {

    Page<Client> findByClientId(String clientId, Pageable pageable);
}
