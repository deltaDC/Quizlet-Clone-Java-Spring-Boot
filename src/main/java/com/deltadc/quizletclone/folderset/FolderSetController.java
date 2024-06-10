package com.deltadc.quizletclone.folderset;

import com.deltadc.quizletclone.response.ResponseObject;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/folder-set")
public class FolderSetController {
    private final FolderSetService folderSetService;

    // tạo folder set mới
    @PostMapping("/create-folder-set")
    public ResponseEntity<ResponseObject> createFolderSet(@RequestBody FolderSet folderSet) {
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

    //lay folder set theo folderSetId
    @GetMapping("/{folderSetId}")
    public ResponseEntity<ResponseObject> getFolderSetById(@PathVariable("folderSetId") Long folderSetId) {
        FolderSet folderSet = folderSetService.getFolderSetById(folderSetId);

        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("folder-set found")
                        .status(HttpStatus.OK)
                        .data(folderSet)
                        .build()
        );
    }

    //lay tat ca cac folder set
    @GetMapping("/get-all-folder-sets")
    public ResponseEntity<ResponseObject> getAllFolderSets() {
        List<FolderSet> folderSet = folderSetService.getAllFolderSets();

        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("folder-sets found")
                        .status(HttpStatus.OK)
                        .data(folderSet)
                        .build()
        );
    }

    //lay tat ca cac folder set theo folderId
    @GetMapping("/folder/{folderId}")
    public ResponseEntity<ResponseObject> getFolderSetByFolderId(@PathVariable("folderId") Long folderId) {
        List<FolderSet> folderSets = folderSetService.getFolderSetByFolderId(folderId);

        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("folder-sets by folderId found")
                        .status(HttpStatus.OK)
                        .data(folderSets)
                        .build()
        );
    }

    //lay tat ca cac folder set theo setId
    @GetMapping("/set/{setId}")
    public ResponseEntity<?> getFolderSetBySetId(@PathVariable("setId") Long setId) {
        List<FolderSet> folderSets = folderSetService.getFolderSetBySetId(setId);

        return ResponseEntity.ok(
                ResponseObject.builder()
                        .message("folder-sets by setId found")
                        .status(HttpStatus.OK)
                        .data(folderSets)
                        .build()
        );
    }

    //sua folder set
    @PutMapping("/edit/{folderSetId}")
    public ResponseEntity<?> editFolderSetById(@PathVariable("folderSetId") Long folderSetId,
                                               @RequestBody FolderSet newFolderSet) {
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
