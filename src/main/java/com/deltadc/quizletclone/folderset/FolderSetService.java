package com.deltadc.quizletclone.folderset;

import com.deltadc.quizletclone.folder.Folder;
import com.deltadc.quizletclone.folder.FolderRepository;
import com.deltadc.quizletclone.set.Set;
import com.deltadc.quizletclone.set.SetRepository;
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
public class FolderSetService {
    private final FolderRepository folderRepository;
    private final SetRepository setRepository;
    private final FolderSetRepository folderSetRepository;
    private final UserRepository userRepository;

    public ResponseEntity<String> createFolderSet(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(json);

            String folderId = node.get("folder_id").asText();
            String setId = node.get("set_id").asText();

            Folder f = folderRepository.findById(Long.valueOf(folderId)).orElse(null);
            if (f == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Folder không tồn tại");
            }

            Set s = setRepository.findById(Long.valueOf(setId)).orElse(null);
            if (s == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Set không tồn tại");
            }

            FolderSet fs = new FolderSet();
            fs.setFolder(f);
            fs.setSet(s);

            folderSetRepository.save(fs);

            return ResponseEntity.ok("Tạo folderset thành công");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Không thể tạo folderset");
        }
    }

    public ResponseEntity<String> deleteFolderSet(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(json);

            String folderSetId = node.get("folder_set_id").asText();

            // Trích xuất thông tin người dùng từ JWT
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username = userDetails.getUsername();

            // Tìm thông tin người dùng từ username = email và thiết lập trường user của Set
            User user = userRepository.findByEmail(username).orElse(null);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Người dùng không tồn tại");
            }

            FolderSet fs = folderSetRepository.findById(Long.valueOf(folderSetId)).orElse(null);
            if(fs == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Folderset không tồn tại");
            }

            folderSetRepository.deleteById(Long.valueOf(folderSetId));
            return ResponseEntity.ok("Xóa folderset thành công");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Không thể xóa folderset");
        }
    }
}
