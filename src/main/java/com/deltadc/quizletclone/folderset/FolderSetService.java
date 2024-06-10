package com.deltadc.quizletclone.folderset;

import com.deltadc.quizletclone.folder.Folder;
import com.deltadc.quizletclone.folder.FolderRepository;
import com.deltadc.quizletclone.set.Set;
import com.deltadc.quizletclone.set.SetRepository;
import com.deltadc.quizletclone.user.User;
import com.deltadc.quizletclone.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FolderSetService {
    private final FolderRepository folderRepository;
    private final SetRepository setRepository;
    private final FolderSetRepository folderSetRepository;
    private final UserRepository userRepository;

    public FolderSet createFolderSet(FolderSet folderSet) {
        Folder f = folderRepository.findById(folderSet.getFolder_id()).orElseThrow();

        Set s = setRepository.findById(folderSet.getSet_id()).orElseThrow();

        Optional<FolderSet> fs = folderSetRepository.findByFolderIdAndSetId(f.getFolder_id(), s.getSet_id());
        if(fs.isPresent()) {
            throw new RuntimeException("set is already in folder");
        } else {
            FolderSet new_fs = new FolderSet();
            new_fs.setFolder_id(f.getFolder_id());
            new_fs.setSet_id(s.getSet_id());

            folderSetRepository.save(new_fs);

            return new_fs;
        }
    }

    public void deleteFolderSetById(Long folderSetId) {

        FolderSet fs = folderSetRepository.findById(folderSetId).orElseThrow();

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

        folderSetRepository.save(folderSet);

        return folderSet;
    }

    public void deleteSetFromFolder(Long folderSetId, Long setId) {
        FolderSet fs = folderSetRepository.findByFolderIdAndSetId(folderSetId, setId).orElseThrow();

        folderSetRepository.delete(fs);
    }
}
