package com.deltadc.quizletclone.folder;

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

@Service
@RequiredArgsConstructor
public class FolderService {
    private final UserRepository userRepository;
    private final FolderRepository folderRepository;

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


}
