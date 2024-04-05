package com.deltadc.quizletclone.set;

import com.deltadc.quizletclone.user.User;
import com.deltadc.quizletclone.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SetService {
    private final SetRepository setRepository;
    private final UserRepository userRepository;

    public ResponseEntity<?> createSet(Set set) {
        Set createdSet = new Set();
        createdSet.setTitle(set.getTitle());
        createdSet.setDescription(set.getDescription());
        createdSet.setPublic(set.isPublic());
        createdSet.setUser_id(set.getUser_id());

        Set savedSet = setRepository.save(createdSet);

        // Trả về đối tượng set vừa được tạo trong phản hồi ResponseEntity
        return ResponseEntity.ok(savedSet);
    }

    //tra ve toan bo set cua nguoi dung theo userId
    public List<Set> getUserSets(Long userId) {
        // Truy vấn tất cả các set thuộc về userId từ cơ sở dữ liệu
        List<Set> userSets = setRepository.findByUserId(userId);

        return userSets;
    }

    public ResponseEntity<String> deleteSet(@PathVariable Long setId) {
        // Trích xuất thông tin người dùng từ JWT
//        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        String username = userDetails.getUsername();
//
//        // Tìm thông tin người dùng từ username = email và thiết lập trường user của Set
//        User user = userRepository.findByEmail(username).orElse(null);
//        if (user == null) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Người dùng không tồn tại");
//        }

        Set s = setRepository.findById(setId).orElse(null);
        if(s == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Set không tồn tại");
        }

//        if (!s.getUser_id().equals(user.getUser_id())) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Không được phép xóa");
//        }

        setRepository.deleteById(setId);
        return ResponseEntity.ok("Xóa set thành công");
    }

    public ResponseEntity<?> getAllSets() {
        List<Set> setList = setRepository.findAll();

        return ResponseEntity.ok(setList);

    }

    public ResponseEntity<?> editSetById(Long setId, Set newSet) {
        Set set = setRepository.findById(setId).orElseThrow();

        set.setTitle(newSet.getTitle());
        set.setDescription(newSet.getDescription());
        set.setPublic(newSet.isPublic());

        setRepository.save(set);

        return ResponseEntity.ok(set);
    }

    public ResponseEntity<?> getPublicSet() {
        List<Set> setList = setRepository.findByIsPublic(true);

        return ResponseEntity.ok(setList);
    }
}
