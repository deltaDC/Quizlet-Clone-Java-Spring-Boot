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
    public ResponseEntity<?> createFolder(@RequestBody String json) {
        return folderService.createFolder(json);
    }

    //lấy toàn bộ folder của người dùng dựa trên user_id
    @GetMapping("/{user_id}/folders")
    public ResponseEntity<List<FolderDTO>> getUserFolders(@PathVariable("user_id") Long userId) {
        List<Folder> userFolders = folderService.getUserFolders(userId);
        List<FolderDTO> userFolderDTOs = userFolders.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userFolderDTOs);
    }

    // xóa folder dựa trên folderId
    @DeleteMapping("/{folder_id}")
    public ResponseEntity<String> deleteFolder(@PathVariable("folder_id") Long folderId) {
        return folderService.deleteFolder(folderId);
    }

    //lấy tất cả các set trong một folder dựa trên folderId
    @GetMapping("/{folder_id}/sets")
    public ResponseEntity<?> getSetsInFolder(@PathVariable("folder_id") Long folderId) {
        return folderService.getSetsInFolder(folderId);
    }
}
