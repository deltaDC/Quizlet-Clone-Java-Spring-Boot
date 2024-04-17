package com.deltadc.quizletclone.folder;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FolderRepository extends JpaRepository<Folder, Long> {
    @Query("SELECT f FROM Folder f WHERE f.user.user_id = :userId")
    List<Folder> findByUserId(Long userId);


    List<Folder> findByIsPublic(boolean aTrue);

    List<Folder> findByTitleContainingAndIsPublic(String title, boolean b);
}
