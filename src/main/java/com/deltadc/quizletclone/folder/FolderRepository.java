package com.deltadc.quizletclone.folder;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FolderRepository extends JpaRepository<Folder, Long> {
    @Query("SELECT f FROM Folder f WHERE f.user.user_id = :userId")
    List<Folder> findByUserId(Long userId);


    List<Folder> findByIsPublic(boolean aTrue);
}
