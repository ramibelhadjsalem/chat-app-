package com.chat.fileService;

import org.springframework.data.jpa.repository.JpaRepository;


public interface FileRepo extends JpaRepository<FileDB ,Long> {
}
