package com.deltadc.quizletclone.folder;

import com.deltadc.quizletclone.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/folder")
@CrossOrigin(origins = "http://localhost:3000")
public class FolderController {
    private final FolderService folderService;

    private FolderDTO convertToDTO(Folder folder) {
        FolderDTO folderDTO = new FolderDTO();
        folderDTO.setFolderId(folder.getFolder_id());
        folderDTO.setTitle(folder.getTitle());
        folderDTO.setDescription(folder.getDescription());
        folderDTO.setCreatedAt(folder.getCreatedAt());
        folderDTO.setUpdatedAt(folder.getUpdatedAt());
        folderDTO.setPublic(folder.isPublic());
        return folderDTO;
    }

    //tạo folder mới
    @PostMapping("/create-folder")
    public ResponseEntity<?> createFolder(@RequestBody Folder folder) {
        return folderService.createFolder(folder);
    }

    //lay toan bo cac folder hien co
    @GetMapping("/get-all-folders")
    public ResponseEntity<?> getAllFolders() {
        return folderService.getAllFolders();
    }

    //lay toan bo public folder
    @GetMapping("/get-public-folders")
    public ResponseEntity<?> getPublicFolders() {
        return folderService.getPublicFolders();
    }

    //lấy toàn bộ folder của người dùng dựa trên user_id
    @GetMapping("/{userId}/folders")
    public ResponseEntity<List<FolderDTO>> getUserFolders(@PathVariable("userId") Long userId) {
        List<Folder> userFolders = folderService.getUserFolders(userId);
        List<FolderDTO> userFolderDTOs = userFolders.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userFolderDTOs);
    }

    // xóa folder dựa trên folderId
    @DeleteMapping("/{folderId}")
    public ResponseEntity<String> deleteFolder(@PathVariable("folderId") Long folderId) {
        return folderService.deleteFolder(folderId);
    }

    //lấy tất cả các set trong một folder dựa trên folderId
    @GetMapping("/{folderId}/sets")
    public ResponseEntity<?> getSetsInFolder(@PathVariable("folderId") Long folderId) {
        return folderService.getSetsInFolder(folderId);
    }

    //edit folder theo folderId
    @PutMapping("/edit/{folderId}")
    public ResponseEntity<?> editFolderById(@PathVariable("folderId") Long folderId, @RequestBody Folder newFolder) {
        return folderService.editFolderById(folderId, newFolder);
    }
}
