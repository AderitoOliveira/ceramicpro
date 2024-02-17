package nuvemdesoftware.ceramicpro.controller;


import nuvemdesoftware.ceramicpro.exception.AlreadyExistsException;
import nuvemdesoftware.ceramicpro.exception.NotFoundException;
import nuvemdesoftware.ceramicpro.exception.ProductServiceException;
import nuvemdesoftware.ceramicpro.model.Product;
import nuvemdesoftware.ceramicpro.services.ProductService;
import nuvemdesoftware.ceramicpro.utils.CustomPageImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.UnknownHostException;

@ResponseBody
@RestController
@CrossOrigin
@RequestMapping("/api/products")
public class ProductController {

    private static final Logger LOG = LoggerFactory.getLogger(ProductController.class);

    @Value( "${server.port}" )
    private String serverPort;

    //private final ProductsRepository _productsRepository;
    private final ProductService _productService;

    @Autowired
    public ProductController(ProductService productService) {
        this._productService = productService;
    }

    //@Autowired
    //public ProductController(ProductsRepository productsRepository){
    //    this._productsRepository =productsRepository;
    //}

    @GetMapping(path="/v1/getAllProducts")
    public CustomPageImpl getAllProducts(@RequestParam(name = "page", defaultValue = "0") int page,
                                         @RequestParam(name = "size", defaultValue = "10") int size,
                                         @RequestParam(name = "search", defaultValue = "") String search) throws UnknownHostException {


        CustomPageImpl productsByPage = _productService.getAllProducts(page, size, search);

        return productsByPage;

    }

    @GetMapping(path="/v1/getProduct")
    public Product getProduct(@RequestParam(name = "customerProductId", defaultValue = "0") String customerProductId) throws UnknownHostException {

        try {
            Product product = _productService.getProduct(customerProductId);
            return product;
        } catch (NotFoundException notFoundExc) {
            throw notFoundExc;
        }
    }

    //@ExceptionHandler
    @PostMapping(path="/v1/saveProduct")
    public ResponseEntity saveProduct(@RequestBody Product product) throws ProductServiceException {

        ResponseEntity responseEntity = null;

        try {
            responseEntity = _productService.saveProduct(product);

            return ResponseEntity.ok(responseEntity.getBody());

        } catch (AlreadyExistsException alreadyExistsException) {
            throw alreadyExistsException;
        }
        /*
        Product productToUpdate = null;
        try {
            productToUpdate = this._productsRepository.findByCustomerProductId(product.getCustomer_product_id());
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
            throw new ProductServiceException(exception.getMessage());
        }

        return ResponseEntity.ok(HttpStatus.CREATED);
        */

    }
}
