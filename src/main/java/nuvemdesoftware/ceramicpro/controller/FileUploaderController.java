package nuvemdesoftware.ceramicpro.controller;

import nuvemdesoftware.ceramicpro.exception.FileTooLargeException;
import nuvemdesoftware.ceramicpro.exception.MissingFileException;
import nuvemdesoftware.ceramicpro.model.Product;
import nuvemdesoftware.ceramicpro.repository.ProductsRepository;
import nuvemdesoftware.ceramicpro.utils.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "*")
@RestController
public class FileUploaderController {

    private static final Logger LOG = LoggerFactory.getLogger(FileUploaderController.class);

    @Autowired
    private FileUtil fileUtil;

    private final ProductsRepository productsRepository;

    @Autowired
    public FileUploaderController(ProductsRepository productsRepository){
        this.productsRepository=productsRepository;
    }

    @PostMapping("/uploadImage")
    public ResponseEntity<?> saveProfileImage(@RequestParam("imageFile") MultipartFile file, @RequestParam("fileType") String fileType, @RequestParam("idToUpdate") String idToUpdate) {

        //LOG.debug("User uploading is " + user);

        try {
            //fileUtil.copyFile(file, user);
            fileUtil.copyFile(file);

            if(fileType.equals("product")) {
                Product product = this.productsRepository.findByCustomerProductId(idToUpdate);
                product.setImage_name(file.getOriginalFilename());
                this.productsRepository.save(product);
            }


            return ResponseEntity.ok().build();
        } catch (FileTooLargeException fe) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        } catch (MissingFileException me) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
        }
    }
}
