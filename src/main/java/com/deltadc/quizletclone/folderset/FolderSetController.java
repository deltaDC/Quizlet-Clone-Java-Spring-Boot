package com.deltadc.quizletclone.folderset;

import com.deltadc.quizletclone.response.ResponseObject;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/folder-set")
public class FolderSetController {
    private final FolderSetService folderSetService;

    // tạo folder set mới
    @PostMapping("/create")
    public ResponseEntity<ResponseObject> createFolderSet(@NonNull @RequestBody FolderSet folderSet) {
        FolderSet fs = folderSetService.createFolderSet(folderSet);

        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("folder-set created")
                        .status(HttpStatus.OK)
                        .data(fs)
                        .build()
        );
    }

    // xóa folder set
    @DeleteMapping("/delete/{folderSetId}")
    public ResponseEntity<ResponseObject> deleteFolderSetById(@PathVariable("folderSetId") Long folderSetId) {
        folderSetService.deleteFolderSetById(folderSetId);

        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("folder-set deleted")
                        .status(HttpStatus.OK)
                        .build()
        );
    }

    //lay folder set theo filter
    @GetMapping("/list")
    public ResponseEntity<ResponseObject> getFolderSetsByFilter(@RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "30") int size,
                                                                @Nullable @RequestParam("folderSetId") Long folderSetId,
                                                                @Nullable @RequestParam("folderId") Long folderId,
                                                                @Nullable @RequestParam("setId") Long setId) {
        Page<FolderSet> folderSets = folderSetService.getFolderSetsByFilter(page, size, folderSetId, folderId, setId);

        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("folder-sets found")
                        .status(HttpStatus.OK)
                        .data(folderSets)
                        .build()
        );
    }

    //sua folder set
    @PutMapping("/edit/{folderSetId}")
    public ResponseEntity<ResponseObject> editFolderSetById(@PathVariable("folderSetId") Long folderSetId,
                                               @NonNull @RequestBody FolderSet newFolderSet) {
        FolderSet updatedFS = folderSetService.editFolderSetById(folderSetId, newFolderSet);

        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("folder-set updated")
                        .status(HttpStatus.OK)
                        .data(updatedFS)
                        .build()
        );
    }

    //xóa một set ra khỏi một folder
    @DeleteMapping("/delete/{folderSetId}/set/{setId}")
    public ResponseEntity<ResponseObject> deleteSetFromFolder(@PathVariable("folderSetId") Long folderSetId, @PathVariable("setId") Long setId) {
        folderSetService.deleteSetFromFolder(folderSetId, setId);

        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("set is deleted from folder")
                        .status(HttpStatus.OK)
                        .build()
        );
    }
}
