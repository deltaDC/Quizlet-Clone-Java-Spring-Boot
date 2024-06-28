package com.deltadc.quizletclone.folderset;

import com.deltadc.quizletclone.folder.FolderRepository;
import com.deltadc.quizletclone.set.SetRepository;
import com.deltadc.quizletclone.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class FolderSetService {
    private final FolderRepository folderRepository;
    private final SetRepository setRepository;
    private final FolderSetRepository folderSetRepository;
    private final UserRepository userRepository;

    public FolderSet createFolderSet(FolderSet folderSet) {
        Long folderId = folderSet.getFolder_id();
        Long setId = folderSet.getSet_id();

        folderRepository.findById(folderId)
                .orElseThrow(() -> new NoSuchElementException("Folder not found"));

        setRepository.findById(setId)
                .orElseThrow(() -> new NoSuchElementException("Set not found"));

        folderSetRepository.findByFolderIdAndSetId(folderId, setId)
                .ifPresent(fs -> {
                    throw new IllegalStateException("Set is already in folder");
                });

        FolderSet new_fs = new FolderSet(folderId, setId);

        return folderSetRepository.save(new_fs);
    }

    public void deleteFolderSetById(Long folderSetId) {
        folderSetRepository.findById(folderSetId).orElseThrow();

        folderSetRepository.deleteById(folderSetId);
    }

    public FolderSet getFolderSetById(Long folderSetId) {
        return folderSetRepository.findById(folderSetId).orElseThrow();
    }

    public List<FolderSet> getAllFolderSets() {
        return folderSetRepository.findAll();
    }

    public List<FolderSet> getFolderSetByFolderId(Long folderId) {
        return folderSetRepository.findByFolderId(folderId);
    }

    public List<FolderSet> getFolderSetBySetId(Long setId) {
        return folderSetRepository.findBySetId(setId);
    }

    public FolderSet editFolderSetById(Long folderSetId, FolderSet newFolderSet) {
        FolderSet folderSet = folderSetRepository.findById(folderSetId).orElseThrow();

        folderSet.setFolder_id(newFolderSet.getFolder_id());
        folderSet.setSet_id(newFolderSet.getSet_id());

        return folderSetRepository.save(folderSet);
    }

    public void deleteSetFromFolder(Long folderSetId, Long setId) {
        FolderSet fs = folderSetRepository.findByFolderIdAndSetId(folderSetId, setId).orElseThrow();

        folderSetRepository.delete(fs);
    }
}
