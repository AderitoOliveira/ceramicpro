package nuvemdesoftware.ceramicpro.controller;

import nuvemdesoftware.ceramicpro.model.Stock;
import nuvemdesoftware.ceramicpro.repository.StockRepository;
import nuvemdesoftware.ceramicpro.utils.CustomPageImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@ResponseBody
@RestController
@CrossOrigin
public class StockController {

    private static final Logger LOG = LoggerFactory.getLogger(StockController.class);

    @Value( "${server.port}" )
    private String serverPort;

    private final StockRepository stockRepository;

    @Autowired
    public StockController(StockRepository stockRepository){
        this.stockRepository=stockRepository;
    }

    @GetMapping(path="/getAllStockProducts")
    public CustomPageImpl getAllProductsStock(@RequestParam(name = "page", defaultValue = "0") int page,
                                         @RequestParam(name = "size", defaultValue = "10") int size,
                                         @RequestParam(name = "search", defaultValue = "") String search) throws UnknownHostException {


        String hostName = InetAddress.getLocalHost().getHostName();

        if(search.equals("")) {
            PageRequest pageRequest = PageRequest.of(page, size);
            Page<Stock> pageResult = stockRepository.findAll(pageRequest);

            //pageResult.forEach(product -> product.setImage_path("http://" + hostName + ":" + serverPort + "/" + "images/?imageName=" + product.getImage_name()));

            List<Stock> products = pageResult.stream().collect(toList());

            return new CustomPageImpl(products, pageRequest, pageResult.getTotalElements());
        } else {
            PageRequest pageRequest = PageRequest.of(page, size);
            Page<Stock> pageResult = stockRepository.findByProdIdProdName(search, pageRequest);

            //pageResult.forEach(product -> product.setImage_path("http://" + hostName + ":" + serverPort + "/" + "images/?imageName=" + product.getImage_name()));

            List<Stock> products = pageResult
                    .stream()
                    .collect(toList());

            return new CustomPageImpl(products, pageRequest, pageResult.getTotalElements());
        }

    }

    @GetMapping(path="/getAllStockProductsForScanner")
    public List<Stock> getAllStockProductsForScanner() {

        List<Stock> stockProductsForScanner = stockRepository.findAll();

        return stockProductsForScanner;
    }

    @GetMapping(path="/getStockProductForScanner")
    public Stock getStockProductForScanner(@RequestParam(name = "barCodeNumber", defaultValue = "0") String barCodeNumber) {

        Stock stockProductForScanner = stockRepository.findAllByBarCodeNumber(barCodeNumber);

        return stockProductForScanner;
    }
}
