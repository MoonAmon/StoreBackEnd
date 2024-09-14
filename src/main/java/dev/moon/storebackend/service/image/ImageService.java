package dev.moon.storebackend.service.image;

import dev.moon.storebackend.dto.ImageDto;
import dev.moon.storebackend.model.Image;
import dev.moon.storebackend.model.Product;
import dev.moon.storebackend.service.product.IProductService;
import dev.moon.storebackend.service.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService {

    private final ImageRepository imageRepository;
    private final IProductService productService;

    @Override
    public Image getImageById(Long id) {
        return imageRepository.findById(id).orElseThrow(() -> new RuntimeException("Image not found! Id: " + id));
    }

    @Override
    public void deleteImageById(Long id) {
        imageRepository.findById(id).ifPresentOrElse(imageRepository::delete, () -> {
            throw new RuntimeException("Image not found! Id: " + id);
        });
    }

    @Override
    public List<ImageDto> saveImages(List<MultipartFile> file, Long productId) {
        Product product = productService.getProductById(productId);
        List<ImageDto> savedImagesDto = new ArrayList<>();

        for (MultipartFile multipartFile : file) {
            try {
                // Create a new Image object and set the properties
                Image image = new Image();
                image.setFileName(multipartFile.getOriginalFilename());
                image.setFileType(multipartFile.getContentType());
                image.setImage(new SerialBlob(multipartFile.getBytes()));
                image.setProduct(product);

                // Create a download URL for the image
                String buildDownloadUrl = "/api/v1/images/image/download/";
                String downloadUrl = buildDownloadUrl + image.getId();

                // Save the image to the database
                image.setDownloadUrl(downloadUrl);
                Image savedImage = imageRepository.save(image);

                // Create a new ImageDto object and set the properties
                savedImage.setDownloadUrl(buildDownloadUrl + savedImage.getId());
                ImageDto imageDto = new ImageDto();
                imageDto.setImageId(savedImage.getId());
                imageDto.setImageName(savedImage.getFileName());
                imageDto.setDownloadUrl(savedImage.getDownloadUrl());
                savedImagesDto.add(imageDto);


            } catch (IOException | SQLException e) {
                throw new RuntimeException("Failed to save image! Error: " + e.getMessage());
            }
        }
        return savedImagesDto;
    }

    @Override
    public void updateImage(MultipartFile file, Long imageId) {
        try {
            Image image = getImageById(imageId);
            image.setFileName(file.getOriginalFilename());
            image.setFileType(file.getContentType());
            image.setImage(new SerialBlob(file.getBytes()));
            imageRepository.save(image);
        } catch (IOException | SQLException e) {
            throw new RuntimeException("Failed to update image! Error: " + e.getMessage());
        }
    }
}
