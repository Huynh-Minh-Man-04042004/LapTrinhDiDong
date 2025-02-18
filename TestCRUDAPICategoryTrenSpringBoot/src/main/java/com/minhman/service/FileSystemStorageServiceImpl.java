package com.minhman.service;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.minhman.Exception.StorageException;
import com.minhman.config.StorageProperties;

@Service
public class FileSystemStorageServiceImpl implements IStorageService {
    private static final Logger logger = LoggerFactory.getLogger(FileSystemStorageServiceImpl.class);
    private final Path rootLocation;

    public FileSystemStorageServiceImpl(StorageProperties properties) {
    	if (properties.getLocation() == null || properties.getLocation().trim().isEmpty()) {
    	    throw new StorageException("Storage location is not configured");
    	}
    	else {
    		this.rootLocation = Paths.get(properties.getLocation());
    	}     
    }

    @Override
    public String getSorageFilename(MultipartFile file, String id) {
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || originalFilename.trim().isEmpty()) {
            throw new StorageException("File must have a name");
        }
        String ext = FilenameUtils.getExtension(originalFilename);
        return "p" + id + "." + ext;
    }

    @Override
    public void store(MultipartFile file, String storeFilename) {
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file");
            }

            if (storeFilename.contains("..")) {
                throw new StorageException("Invalid file path: " + storeFilename);
            }

            Path destinationFile = this.rootLocation.resolve(Paths.get(storeFilename)).normalize().toAbsolutePath();

            if (!destinationFile.startsWith(this.rootLocation)) {
                throw new StorageException("Cannot store file outside current directory");
            }

            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            }

        } catch (Exception e) {
            logger.error("Failed to store file: " + storeFilename, e);
            throw new StorageException("Failed to store file: " + storeFilename, e);
        }
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() && resource.isReadable()) {
                return resource;
            }
            throw new StorageException("Cannot read file: " + filename);
        } catch (Exception e) {
            logger.error("Could not read file: " + filename, e);
            throw new StorageException("Could not read file: " + filename, e);
        }
    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public void delete(String storeFilename) throws Exception {
        Path destinationFile = rootLocation.resolve(Paths.get(storeFilename)).normalize().toAbsolutePath();

        if (Files.exists(destinationFile)) {
            Files.delete(destinationFile);
        } else {
            logger.warn("File not found for deletion: " + storeFilename);
        }
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
            logger.info("Storage directory initialized at: " + rootLocation.toString());
        } catch (Exception e) {
            logger.error("Could not initialize storage", e);
            throw new StorageException("Could not initialize storage", e);
        }
    }
}
