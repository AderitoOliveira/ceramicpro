package nuvemdesoftware.ceramicpro.services;

import nuvemdesoftware.ceramicpro.exception.AlreadyExistsException;
import nuvemdesoftware.ceramicpro.exception.NotFoundException;
import nuvemdesoftware.ceramicpro.exception.ProductServiceException;
import nuvemdesoftware.ceramicpro.model.Product;
import nuvemdesoftware.ceramicpro.repository.ProductsRepository;
import nuvemdesoftware.ceramicpro.utils.CustomPageImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class ProductService {

    private static final Logger LOG = LoggerFactory.getLogger(ProductService.class);

    @Value( "${server.port}" )
    private String serverPort;

    private final ProductsRepository _productsRepository;

    @Autowired
    public ProductService(ProductsRepository productsRepository) {
        _productsRepository = productsRepository;
    }


    public CustomPageImpl getAllProducts(int page, int size, String search) throws UnknownHostException {

        String hostName = InetAddress.getLocalHost().getHostName();

        if(search.equals("")) {
            PageRequest pageRequest = PageRequest.of(page, size);
            Page<Product> pageResult = _productsRepository.findAll(pageRequest);

            pageResult.forEach(product -> product.setImage_path("http://" + hostName + ":" + serverPort + "/" + "images/?imageName=" + product.getImage_name()));

            List<Product> products = pageResult.stream().collect(toList());

            return new CustomPageImpl(products, pageRequest, pageResult.getTotalElements());
        } else {
            PageRequest pageRequest = PageRequest.of(page, size);
            Page<Product> pageResult = _productsRepository.findByCustProdIdProdNameInternalProdId(search, pageRequest);

            pageResult.forEach(product -> product.setImage_path("http://" + hostName + ":" + serverPort + "/" + "images/?imageName=" + product.getImage_name()));

            List<Product> products = pageResult
                    .stream()
                    .collect(toList());

            return new CustomPageImpl(products, pageRequest, pageResult.getTotalElements());
        }

    }

    public Product getProduct(String customerProductId) throws NotFoundException {

        String hostName = null;
        try {
             hostName = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException exc) {
            throw new NotFoundException("Couldn't retrieve the hostName to resolve!");
        }


        Product product = _productsRepository.findByCustomerProductId(customerProductId);
        if(product != null) {
            product.setImage_path("http://" + hostName + ":" + serverPort + "/" + "images/?imageName=" + product.getImage_name());
            return product;
        } else {
            LOG.error("The CustomerProductId " + customerProductId + " doesn't exist");
            throw new NotFoundException("The CustomerProductId " + customerProductId + " doesn't exist");
        }

    }

    public ResponseEntity saveProduct(@RequestBody Product product) throws AlreadyExistsException {

        Product productToUpdate = null;
        try {
            productToUpdate = this._productsRepository.findByCustomerProductId(product.getCustomer_product_id());

            if(productToUpdate != null) {
                throw new  AlreadyExistsException("O produto " + product.getCustomer_product_id()  + " já existe!!!");
            }
            productToUpdate.setInternal_product_id(product.getInternal_product_id());
            productToUpdate.setClient_name(product.getClient_name());
            productToUpdate.setProduct_name(product.getProduct_name());
            productToUpdate.setPrice_euro_1(product.getPrice_euro_1());
            productToUpdate.setPrice_euro_2(product.getPrice_euro_2());
            productToUpdate.setProduct_name_for_label(product.getProduct_name_for_label());
            productToUpdate.setIs_parent(product.getIs_parent());
            this._productsRepository.save(productToUpdate);
        } catch (DataIntegrityViolationException exception) {
            LOG.error(exception.getMessage());
            //throw new ProductServiceException("Problem saving product " + productToUpdate.getCustomer_product_id() + " - " + productToUpdate.getProduct_name());
            throw new AlreadyExistsException(exception.getMessage());
        }

        return ResponseEntity.ok(HttpStatus.CREATED);
    }
}
