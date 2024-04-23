package com.deltadc.quizletclone.folder;

import com.deltadc.quizletclone.folderset.FolderSet;
import com.deltadc.quizletclone.folderset.FolderSetRepository;
import com.deltadc.quizletclone.set.Set;
import com.deltadc.quizletclone.set.SetController;
import com.deltadc.quizletclone.set.SetDTO;
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

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FolderService {
    private final UserRepository userRepository;
    private final FolderRepository folderRepository;
    private final FolderSetRepository folderSetRepository;
    private final SetController setController;

    public ResponseEntity<?> createFolder(Folder folder) {
        if(folder.getTitle().length() <= 0 || folder.getDescription().length() <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("khong duoc de trong");
        }


        Folder createdFolder = new Folder();
        createdFolder.setTitle(folder.getTitle());
        createdFolder.setDescription(folder.getDescription());
        createdFolder.setPublic(folder.isPublic());
        createdFolder.setUser_id(folder.getUser_id());

        Folder savedFolder = folderRepository.save(createdFolder);
//        FolderDTO folderDTO = new FolderDTO();
//        folderDTO.setFolderId(savedFolder.getFolder_id());
//        folderDTO.setTitle(savedFolder.getTitle());
//        folderDTO.setDescription(savedFolder.getDescription());
//        folderDTO.setCreatedAt(savedFolder.getCreatedAt());
//        folderDTO.setUpdatedAt(savedFolder.getUpdatedAt());
//        folderDTO.setPublic(savedFolder.isPublic());

        return ResponseEntity.ok(savedFolder);
    }

    public ResponseEntity<?> getAllFolders() {
        List<Folder> folders = folderRepository.findAll();

        return ResponseEntity.ok(folders);
    }

    public ResponseEntity<?> getPublicFolders() {
        List<Folder> publicFolders = folderRepository.findByIsPublic(true);

        return ResponseEntity.ok(publicFolders);
    }

    public ResponseEntity<String> deleteFolder(Long folderId) {
        // Trích xuất thông tin người dùng từ JWT
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();


        // Tìm thông tin người dùng từ username = email và thiết lập trường user của Set
        User user = userRepository.findByEmail(username).orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Người dùng không tồn tại");
        }

        Folder f = folderRepository.findById(folderId).orElse(null);
        if(f == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Folder không tồn tại");
        }

        if (!f.getUser().getUser_id().equals(user.getUser_id())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Không được phép xóa");
        }

        folderRepository.deleteById(folderId);
        return ResponseEntity.ok("Xóa folder thành công");
    }


    public ResponseEntity<?> getSetsInFolder(Long folderId) {
        List<FolderSet> fsList = folderSetRepository.findByFolderId(folderId);

        List<SetDTO> setDTOList = new ArrayList<>();
        for(FolderSet fs : fsList) {
            //System.out.println(fs.getFolder_set_id() + " " + fs.getFolder().getFolder_id() + " " + fs.getSet().getSet_id());
            Set s = fs.getSet();
            Long userId = s.getUser_id();
            String username = userRepository.findById(userId).get().getName();
            SetDTO sDTO = setController.convertToDTO(s);
            sDTO.setOwnerName(username);
            setDTOList.add(sDTO);
        }

        return ResponseEntity.ok(setDTOList);
    }

    //tra ve toan bo folder cua user theo userId
    public List<Folder> getUserFolders(Long userId) {
        // Truy vấn tất cả các set thuộc về userId từ cơ sở dữ liệu
        return folderRepository.findByUserId(userId);
    }

    public ResponseEntity<?> editFolderById(Long folderId, Folder newFolder) {
        Folder folder = folderRepository.findById(folderId).orElseThrow();

        folder.setTitle(newFolder.getTitle());
        folder.setDescription(newFolder.getDescription());
        folder.setPublic(newFolder.isPublic());

        folderRepository.save(folder);

        return ResponseEntity.ok(folder);
    }

    public ResponseEntity<?> getFolderByTitle(String title) {
        List<Folder> folders = folderRepository.findByTitleContainingAndIsPublic(title, true);

        return ResponseEntity.ok(folders);
    }
}
