package com.deltadc.quizletclone.folder;

import com.deltadc.quizletclone.response.ResponseObject;
import com.deltadc.quizletclone.user.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    private boolean isEmptyFolderInput(Folder folder) {
        return folder.getTitle().isEmpty() || folder.getDescription().isEmpty() || String.valueOf(folder.isPublic()).isEmpty();
    }

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
    public ResponseEntity<ResponseObject> createFolder(@RequestBody Folder folder) {
        if(isEmptyFolderInput(folder)) {
            return ResponseEntity.ok(
                    ResponseObject.builder()
                            .message("folder should not be empty")
                            .status(HttpStatus.NOT_ACCEPTABLE)
                            .build()
            );
        }

        Folder createdFolder = folderService.createFolder(folder);

        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("folder created")
                        .status(HttpStatus.OK)
                        .data(createdFolder)
                        .build()
        );
    }

    //lay toan bo cac folder hien co
    @GetMapping("/get-all-folders")
    public ResponseEntity<ResponseObject> getAllFolders(@RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "30") int size) {
        Page<Folder> folderPage = folderService.getAllFolders(page, size);

        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("folders found")
                        .status(HttpStatus.OK)
                        .data(folderPage)
                        .build()
        );
    }

    //lay toan bo public folder
    @GetMapping("/get-public-folders")
    public ResponseEntity<ResponseObject> getPublicFolders(@RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "30") int size) {
        Page<Folder> folderPage = folderService.getPublicFolders(page, size);

        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("public folders found")
                        .status(HttpStatus.OK)
                        .data(folderPage)
                        .build()
        );
    }

    //lấy toàn bộ folder của người dùng dựa trên user_id
    @GetMapping("/{userId}/folders")
    public ResponseEntity<ResponseObject> getUserFolders(@PathVariable("userId") Long userId) {
        List<FolderDTO> userFolderDTOs = folderService.getUserFolders(userId)
                .stream()
                .map(FolderDTO::fromFolderToFolderDTO)
                .toList();

        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("user's folders found")
                        .status(HttpStatus.OK)
                        .data(userFolderDTOs)
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
                                                         @RequestBody Folder newFolder) {
        if(isEmptyFolderInput(newFolder)) {
            return ResponseEntity.ok(
                    ResponseObject.builder()
                            .message("folder should not be empty")
                            .status(HttpStatus.OK)
                            .build()
            );
        }

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

    //tim folder theo title
    @GetMapping("/title/{title}")
    public ResponseEntity<ResponseObject> getFolderByTitle(@PathVariable("title") String title,
                                                           @RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "30") int size) {
        Page<Folder> folderPage = folderService.getFolderByTitle(title, page, size);

        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("folder updated")
                        .status(HttpStatus.OK)
                        .data(folderPage)
                        .build()
        );
    }
}
