package com.deltadc.quizletclone.folderset;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FolderSetRepository extends JpaRepository<FolderSet, Long> {
    @Query("SELECT fs FROM FolderSet fs WHERE fs.folder.folder_id = :folderId")
    List<FolderSet> findByFolderId(Long folderId);
}
