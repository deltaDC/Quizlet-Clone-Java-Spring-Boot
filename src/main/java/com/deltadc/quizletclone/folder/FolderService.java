package com.deltadc.quizletclone.folder;

import com.deltadc.quizletclone.folderset.FolderSet;
import com.deltadc.quizletclone.folderset.FolderSetRepository;
import com.deltadc.quizletclone.set.Set;
import com.deltadc.quizletclone.set.SetController;
import com.deltadc.quizletclone.set.SetDTO;
import com.deltadc.quizletclone.user.Role;
import com.deltadc.quizletclone.user.User;
import com.deltadc.quizletclone.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class FolderService {
    private final UserRepository userRepository;
    private final FolderRepository folderRepository;
    private final FolderSetRepository folderSetRepository;
    private final SetController setController;



    public Folder createFolder(Folder folder) {

        Folder createdFolder = new Folder(
                folder.getUser_id(),
                folder.getTitle(),
                folder.getDescription(),
                folder.isPublic()
        );

        return folderRepository.save(createdFolder);
    }

    public Folder getFolderById(Long id) {
        return folderRepository.findById(id).get();
    }

    public Page<Folder> getAllFolders(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        return folderRepository.findAll(pageable);
    }

    public Page<Folder> getPublicFolders(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Folder> publicFolders = folderRepository.findByIsPublic(true, pageable);

        return publicFolders;
    }

    public void deleteFolder(Long folderId) {
        folderRepository.deleteById(folderId);
    }


    public ResponseEntity<?> getSetsInFolder(Long folderId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<FolderSet> fsPage = folderSetRepository.findByFolderIdWithPageable(folderId, pageable);

        List<SetDTO> setDTOList = new ArrayList<>();
        for(FolderSet fs : fsPage) {
            //System.out.println(fs.getFolder_set_id() + " " + fs.getFolder().getFolder_id() + " " + fs.getSet().getSet_id());
            Set s = fs.getSet();
            Long userId = s.getUser_id();
            String username = userRepository.findById(userId).get().getName();
            SetDTO sDTO = setController.convertToDTO(s);
            sDTO.setOwnerName(username);
            setDTOList.add(sDTO);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("content", setDTOList);
        response.put("totalPages", fsPage.getTotalPages());

        return ResponseEntity.ok(response);
    }

    //tra ve toan bo folder cua user theo userId
    public List<Folder> getUserFolders(Long userId) {
        // Truy vấn tất cả các set thuộc về userId từ cơ sở dữ liệu
        return folderRepository.findByUserId(userId);
    }

    public Folder editFolderById(Long folderId, Folder newFolder) {
        Folder folder = folderRepository.findById(folderId).get();

        folder.setTitle(newFolder.getTitle());
        folder.setDescription(newFolder.getDescription());
        folder.setPublic(newFolder.isPublic());

        folderRepository.save(folder);

        return folder;
    }

    public Page<Folder> getFolderByTitle(String title, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        return folderRepository.findByTitleContainingAndIsPublic(title, true, pageable);
    }
}
