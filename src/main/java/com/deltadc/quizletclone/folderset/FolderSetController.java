package com.deltadc.quizletclone.folderset;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/folderset")
public class FolderSetController {
    private final FolderSetService folderSetService;

    // tạo folder set mới
    @PostMapping("/create-folderset")
    public ResponseEntity<String> createFolderSet(@RequestBody String json) {
        return folderSetService.createFolderSet(json);
    }

    // xóa folder set
    @DeleteMapping("delete-folderset")
    public ResponseEntity<String> deleteFolderSet(@RequestBody String json) {
        return folderSetService.deleteFolderSet(json);
    }

}
