package com.deltadc.quizletclone.folder;

import com.deltadc.quizletclone.folderset.FolderSet;
import com.deltadc.quizletclone.folderset.FolderSetRepository;
import com.deltadc.quizletclone.folderset.FolderSetService;
import com.deltadc.quizletclone.set.Set;
import com.deltadc.quizletclone.set.SetController;
import com.deltadc.quizletclone.set.SetDTO;
import com.deltadc.quizletclone.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FolderService {
    private final UserService userService;
    private final FolderRepository folderRepository;
    private final FolderSetRepository folderSetRepository;
    private final SetController setController;

    public Folder createFolder(Folder folder) {

        Folder createdFolder = Folder.builder()
                .user_id(folder.getUser_id())
                .title(folder.getTitle())
                .description(folder.getDescription())
                .isPublic(folder.isPublic())
                .build();

        return folderRepository.save(createdFolder);
    }

    public Folder getFolderById(Long id) {
        return folderRepository.findById(id).orElseThrow();
    }

    public void deleteFolder(Long folderId) {
        folderRepository.deleteById(folderId);
    }


    public ResponseEntity<?> getSetsInFolder(Long folderId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<FolderSet> fsPage = folderSetRepository.findByFolderIdWithPageable(folderId, pageable);

        List<SetDTO> setDTOList = fsPage.stream()
                .map(fs -> {
                    Set s = fs.getSet();
                    Long userId = s.getUser_id();
                    String username = userService.getUserById(userId).getUsername();
                    SetDTO sDTO = setController.convertToDTO(s);
                    sDTO.setOwnerName(username);
                    return sDTO;
                })
                .toList();

        Map<String, Object> response = new HashMap<>();
        response.put("content", setDTOList);
        response.put("totalPages", fsPage.getTotalPages());

        return ResponseEntity.ok(response);
    }

    public Folder editFolderById(Long folderId, Folder newFolder) {
        return folderRepository.findById(folderId)
                .map(folder -> {
                    Optional.ofNullable(newFolder.getTitle()).ifPresent(folder::setTitle);
                    Optional.ofNullable(newFolder.getDescription()).ifPresent(folder::setDescription);
                    Optional.of(newFolder.isPublic()).ifPresent(folder::setPublic);
                    return folderRepository.save(folder);
                })
                .orElse(null);
    }

    public Page<Folder> getFoldersByFilter(int page, int size, String title, Boolean isPublic, Long userId) {
        Pageable pageable = PageRequest.of(page, size);

        Specification<Folder> setSpecification = FolderSpecification.withDynamicQuery(title, isPublic, userId);

        return folderRepository.findAll(setSpecification, pageable);
    }
}
