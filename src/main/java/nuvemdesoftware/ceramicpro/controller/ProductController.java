package nuvemdesoftware.ceramicpro.controller;

import nuvemdesoftware.ceramicpro.model.Product;
import nuvemdesoftware.ceramicpro.repository.ProductsRepository;
import nuvemdesoftware.ceramicpro.utils.CustomPageImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@CrossOrigin
public class ProductController {

    private final ProductsRepository productsRepository;

    @Autowired
    public ProductController(ProductsRepository productsRepository){
        this.productsRepository=productsRepository;
    }

    @GetMapping(path="/getAllProducts")
    public CustomPageImpl getAllProducts(@RequestParam(name = "page", defaultValue = "0") int page,
                                         @RequestParam(name = "size", defaultValue = "10") int size,
                                         @RequestParam(name = "search", defaultValue = "") String search) {

        if(search.equals("")) {
            PageRequest pageRequest = PageRequest.of(page, size);
            Page<Product> pageResult = productsRepository.findAll(pageRequest);
            List<Product> products = pageResult
                    .stream()
                    //.map(Product::new)
                    .collect(toList());

            //return new PageImpl<>(products, pageRequest, pageResult.getTotalElements());
            return new CustomPageImpl(products, pageRequest, pageResult.getTotalElements());
        } else {
            PageRequest pageRequest = PageRequest.of(page, size);
            //Page<Product> pageResult2 = productsRepository.findByCustomerProductId(search,pageRequest);
            Page<Product> pageResult2 = productsRepository.findByCustProdIdProdNameInternalProdId(search, pageRequest);
            List<Product> products = pageResult2
                    .stream()
                    //.map(Product::new)
                    .collect(toList());

            //return new PageImpl<>(products, pageRequest, pageResult.getTotalElements());
            return new CustomPageImpl(products, pageRequest, pageResult2.getTotalElements());
        }

    }
}
