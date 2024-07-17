package com.deltadc.quizletclone.folder;

import com.deltadc.quizletclone.response.ResponseObject;
import com.deltadc.quizletclone.user.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/folder")
public class FolderController {
    private final FolderService folderService;
    private final UserService userService;

    private boolean isFolderOwner(Folder f) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();

        UserDTO user = userService.getUserByEmail(username);
        Long userId = user.getUser_id();

        if(user.getRole().compareTo(Role.ADMIN) == 0) {
            return true;
        }

        return Objects.equals(userId, f.getUser_id());
    }

    //tạo folder mới
    @PostMapping("/create-folder")
    public ResponseEntity<ResponseObject> createFolder(@NonNull @RequestBody Folder folder) {
        Folder createdFolder = folderService.createFolder(folder);

        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("folder created")
                        .status(HttpStatus.OK)
                        .data(createdFolder)
                        .build()
        );
    }

    //lay folder theo filter
    @GetMapping("/list")
    public ResponseEntity<ResponseObject> getFoldersByFilter(@RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "30") int size,
                                                             @Nullable @RequestParam("title") String title,
                                                             @Nullable @RequestParam("isPublic") Boolean isPublic,
                                                             @Nullable @RequestParam("userId") Long userId) {
        Page<Folder> folderPage = folderService.getFoldersByFilter(page, size, title, isPublic, userId);

        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("folders found")
                        .status(HttpStatus.OK)
                        .data(folderPage)
                        .build()
        );
    }

    // xóa folder dựa trên folderId
    @DeleteMapping("/{folderId}")
    public ResponseEntity<ResponseObject> deleteFolder(@PathVariable("folderId") Long folderId) {
        Folder f = folderService.getFolderById(folderId);

        if (!isFolderOwner(f)) {
            return ResponseEntity.ok(
                    ResponseObject.builder()
                            .message("can not delete because you are not the owner")
                            .status(HttpStatus.OK)
                            .build()
            );
        }

        folderService.deleteFolder(folderId);

        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("folder deleted")
                        .status(HttpStatus.OK)
                        .build()
        );
    }

    //lấy tất cả các set trong một folder dựa trên folderId
    @GetMapping("/{folderId}/sets")
    public ResponseEntity<?> getSetsInFolder(@PathVariable("folderId") Long folderId,
                                             @RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "30") int size) {
        return folderService.getSetsInFolder(folderId, page, size);
    }

    //edit folder theo folderId
    @PutMapping("/edit/{folderId}")
    public ResponseEntity<ResponseObject> editFolderById(@PathVariable("folderId") Long folderId,
                                                         @NonNull @RequestBody Folder newFolder) {
        Folder folder = folderService.getFolderById(folderId);

        if(!isFolderOwner(folder)) {
            return ResponseEntity.ok(
                    ResponseObject.builder()
                            .message("can not update because you are not the owner")
                            .status(HttpStatus.OK)
                            .build()
            );
        }

        Folder updatedFolder = folderService.editFolderById(folderId, newFolder);

        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("folder updated")
                        .status(HttpStatus.OK)
                        .data(updatedFolder)
                        .build()
        );
    }
}
