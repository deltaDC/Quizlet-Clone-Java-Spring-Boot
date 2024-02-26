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

    public ResponseEntity<?> createFolder(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(json);

            String title = node.get("title").asText();
            String description = node.get("description").asText();
            boolean isPublic = node.get("is_public").asBoolean();

            // Trích xuất thông tin người dùng từ JWT
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username = userDetails.getUsername();


            // Tìm thông tin người dùng từ username = email và thiết lập trường user của Set
            User user = userRepository.findByEmail(username).orElse(null);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Người dùng không tồn tại");
            }

            Folder createdFolder = new Folder();
            createdFolder.setTitle(title);
            createdFolder.setDescription(description);
            createdFolder.setPublic(isPublic);
            createdFolder.setUser(user);

            Folder savedFolder = folderRepository.save(createdFolder);
            FolderDTO folderDTO = new FolderDTO();
            folderDTO.setFolderId(savedFolder.getFolder_id());
            folderDTO.setTitle(savedFolder.getTitle());
            folderDTO.setDescription(savedFolder.getDescription());
            folderDTO.setCreatedAt(savedFolder.getCreatedAt());
            folderDTO.setUpdatedAt(savedFolder.getUpdatedAt());
            folderDTO.setPublic(savedFolder.isPublic());

            return ResponseEntity.ok(folderDTO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Không thể tạo folder");
        }
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
            SetDTO sDTO = setController.convertToDTO(s);
            setDTOList.add(sDTO);
        }

        return ResponseEntity.ok(setDTOList);
    }
}
