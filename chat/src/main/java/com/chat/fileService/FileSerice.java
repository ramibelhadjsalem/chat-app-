package com.chat.fileService;

import org.springframework.web.multipart.MultipartFile;

import java.util.stream.Stream;

public interface FileSerice {
    FileDB store(MultipartFile file);
    FileDB getFile(Long id);
    Stream<FileDB> getAllFiles();
}
