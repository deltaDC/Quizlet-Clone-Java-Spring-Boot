package com.deltadc.quizletclone.set;

import com.deltadc.quizletclone.user.User;
import com.deltadc.quizletclone.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SetService {
    private final SetRepository setRepository;
    private final UserRepository userRepository;

    private boolean isSetOwner(Set s) {
        // Trích xuất thông tin người dùng từ JWT
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();

        // Tìm thông tin người dùng từ username = email và thiết lập trường user của Set
        User user = userRepository.findByEmail(username).orElseThrow();
        Long userId = user.getUser_id();
        System.out.println("userId id " + userId);

        System.out.println("user id of set is " + s.getUser_id());

        return Objects.equals(s.getUser_id(), userId);
    }

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
        return setRepository.findByUserId(userId);
    }

    public ResponseEntity<String> deleteSet(Long setId) {
        Set s = setRepository.findById(setId).orElse(null);
        if(s == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Set không tồn tại");
        }

        if(!isSetOwner(s)) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Không được phép xóa nếu không phải chủ sở hữu");
        }

        setRepository.deleteById(setId);
        return ResponseEntity.ok("Xóa set thành công");
    }

    public ResponseEntity<?> getAllSets() {
        List<Set> setList = setRepository.findAll();

        return ResponseEntity.ok(setList);

    }

    public ResponseEntity<?> editSetById(Long setId, Set newSet) {
        Set set = setRepository.findById(setId).orElseThrow();

        if(!isSetOwner(set)) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Không được phép sửa nếu không phải chủ sở hữu");
        }

        set.setTitle(newSet.getTitle());
        set.setDescription(newSet.getDescription());
        set.setPublic(newSet.isPublic());

        setRepository.save(set);

        return ResponseEntity.ok(set);
    }

    public List<Set> getPublicSet() {
        return setRepository.findByIsPublic(true);
    }

    public ResponseEntity<?> getSetByTitle(String title) {
        List<Set> setList = setRepository.findByTitleContainingAndIsPublic(title, true);

        return ResponseEntity.ok(setList);
    }
}
