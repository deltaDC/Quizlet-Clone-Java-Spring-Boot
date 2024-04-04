package com.deltadc.quizletclone.folderset;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FolderSetRepository extends JpaRepository<FolderSet, Long> {
    @Query("SELECT fs FROM FolderSet fs WHERE fs.folder_id = :folderId")
    List<FolderSet> findByFolderId(Long folderId);

    @Query("SELECT fs FROM FolderSet fs WHERE fs.set_id = :setId")
    List<FolderSet> findBySetId(Long setId);
}
