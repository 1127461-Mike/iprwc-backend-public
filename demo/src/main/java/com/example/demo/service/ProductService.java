package com.example.demo.service;

import com.example.demo.dto.CreateProductDto;
import com.example.demo.dto.ProductUpdateDto;
import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }


    public Product save(CreateProductDto productDto) throws IOException {

        Product product = new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setBrandName(productDto.getBrandName());
        product.setImagePath(productDto.getImagePath());

        return productRepository.save(product);
    }

//    public Product updateProduct(UUID productId, ProductUpdateDto productUpdateDto) throws IOException {
//        Product product = productRepository.findById(productId)
//                .orElseThrow(() -> new RuntimeException("Product not found"));
//
//
//        if (productUpdateDto.getName() != null) {
//            product.setName(productUpdateDto.getName());
//        }
//        if (productUpdateDto.getDescription() != null) {
//            product.setDescription(productUpdateDto.getDescription());
//        }
//        if (productUpdateDto.getPrice() != null) {
//            product.setPrice(productUpdateDto.getPrice());
//        }
//        if (productUpdateDto.getBrandName() != null) {
//            product.setBrandName(productUpdateDto.getBrandName());
//        }
//        if (productUpdateDto.getImage() != null && !productUpdateDto.getImage().isEmpty()) {
//            String oldImagePath = product.getImagePath();
//
//            deleteOldImage(oldImagePath);
//
//            String newImagePath = saveImage(productUpdateDto.getImage());
//            product.setImagePath(newImagePath);
//        }
//
//        return productRepository.save(product);
//    }


    public Product updateProductAvailability(UUID productId, boolean isAvailable) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        product.setAvailable(isAvailable);
        return productRepository.save(product);
    }


    public Product findById(UUID product){
    Optional<Product> foundProduct = productRepository.findById(product);
        return foundProduct.orElse(null);
    }


//    private String saveImage(MultipartFile imageFile) throws IOException {
//        String uploadDir = "src/main/resources/static/images";
//
//        String filename = UUID.randomUUID().toString() + "-" + imageFile.getOriginalFilename();
//        Path uploadPath = Paths.get(uploadDir, filename);
//
//        Files.copy(imageFile.getInputStream(), uploadPath, StandardCopyOption.REPLACE_EXISTING);
//
//        return "/images/" + filename;
//    }

    private void deleteOldImage(String oldImagePath) {
        if (oldImagePath != null && !oldImagePath.isEmpty()) {
            try {
                Path path = Paths.get("src/main/resources/static" + oldImagePath);
                if (Files.exists(path)) {
                    Files.delete(path);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



}
