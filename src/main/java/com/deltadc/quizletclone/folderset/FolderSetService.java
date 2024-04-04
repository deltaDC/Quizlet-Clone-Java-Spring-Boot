package com.deltadc.quizletclone.folderset;

import com.deltadc.quizletclone.folder.Folder;
import com.deltadc.quizletclone.folder.FolderRepository;
import com.deltadc.quizletclone.set.Set;
import com.deltadc.quizletclone.set.SetRepository;
import com.deltadc.quizletclone.user.User;
import com.deltadc.quizletclone.user.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FolderSetService {
    private final FolderRepository folderRepository;
    private final SetRepository setRepository;
    private final FolderSetRepository folderSetRepository;
    private final UserRepository userRepository;

    public ResponseEntity<?> createFolderSet(FolderSet folderSet) {
        Folder f = folderRepository.findById(folderSet.getFolder_id()).orElse(null);
        if (f == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Folder không tồn tại");
        }

        Set s = setRepository.findById(folderSet.getSet_id()).orElse(null);
        if (s == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Set không tồn tại");
        }

        FolderSet fs = new FolderSet();
        fs.setFolder_id(f.getFolder_id());
        fs.setSet_id(s.getSet_id());

        folderSetRepository.save(fs);

        return ResponseEntity.ok(fs);
    }

    public ResponseEntity<String> deleteFolderSetById(Long folderSetId) {
        // Trích xuất thông tin người dùng từ JWT
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();

        // Tìm thông tin người dùng từ username = email và thiết lập trường user của Set
        User user = userRepository.findByEmail(username).orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Người dùng không tồn tại");
        }

        FolderSet fs = folderSetRepository.findById(folderSetId).orElse(null);
        if(fs == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Folder set không tồn tại");
        }

        folderSetRepository.deleteById(folderSetId);
        return ResponseEntity.ok("Xóa folder set thành công");
    }

    public ResponseEntity<?> getFolderSetById(Long folderSetId) {
        FolderSet folderSet = folderSetRepository.findById(folderSetId).orElseThrow();

        return ResponseEntity.ok(folderSet);
    }

    public ResponseEntity<?> getAllFolderSets() {
        List<FolderSet> folderSets = folderSetRepository.findAll();

        return ResponseEntity.ok(folderSets);
    }

    public ResponseEntity<?> getFolderSetByFolderId(Long folderId) {
        List<FolderSet> folderSets = folderSetRepository.findByFolderId(folderId);

        return ResponseEntity.ok(folderSets);
    }

    public ResponseEntity<?> getFolderSetBySetId(Long setId) {
        List<FolderSet> folderSets = folderSetRepository.findBySetId(setId);

        return ResponseEntity.ok(folderSets);
    }

    public ResponseEntity<?> editFolderSetById(Long folderSetId, FolderSet newFolderSet) {
        FolderSet folderSet = folderSetRepository.findById(folderSetId).orElseThrow();

        folderSet.setFolder_id(newFolderSet.getFolder_id());
        folderSet.setSet_id(newFolderSet.getSet_id());

        folderSetRepository.save(folderSet);

        return ResponseEntity.ok(folderSet);
    }
}
