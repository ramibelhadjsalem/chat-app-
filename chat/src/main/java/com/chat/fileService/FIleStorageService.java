package com.chat.fileService;


import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.stream.Stream;

@Service
public class FIleStorageService implements FileSerice{
    @Autowired private FileRepo fileRepo ;
    @SneakyThrows
    @Override
    public FileDB store(MultipartFile file){
        try {
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            FileDB fileDB = new FileDB(fileName, file.getContentType(),file.getBytes());


            return fileRepo.save(fileDB);
        }catch (IOException ex){
            throw new IOException("Error while saving the file");
        }

    }

    @Override
    public FileDB getFile(Long id) {
        return fileRepo.findById(id).get();
    }

    @Override
    public Stream<FileDB> getAllFiles() {
        return fileRepo.findAll().stream();
    }
}
