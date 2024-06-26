package com.vmdev.eshop.mapper;

import com.vmdev.eshop.dto.ProductCreateEditDto;
import com.vmdev.eshop.entity.Product;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static java.util.function.Predicate.not;

@Component
public class ProductCreateEditMapper implements Mapper<ProductCreateEditDto, Product> {

    @Override
    public Product map(ProductCreateEditDto object) {
        Product product = new Product();
        copy(object, product);
        return product;
    }

    @Override
    public Product map(ProductCreateEditDto fromObject, Product toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    private static void copy(ProductCreateEditDto object, Product product) {
        product.setName(object.getName());
        product.setDescription(object.getDescription());
        product.setCost(object.getCost());
        product.setQuantity(object.getQuantity());
        product.setType(object.getType());
        product.setManufacturer(object.getManufacturer());

        Optional.ofNullable(object.getImage())
                .filter(not(MultipartFile::isEmpty))
                .ifPresent(image -> product.setImage(image.getOriginalFilename()));
    }
}
