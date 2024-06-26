package com.deltadc.quizletclone.set;

import com.deltadc.quizletclone.user.Role;
import com.deltadc.quizletclone.user.User;
import com.deltadc.quizletclone.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

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

        if(user.getRole().compareTo(Role.ADMIN) == 0) {
            System.out.println(user.getRole());
            return true;
        }

        return Objects.equals(s.getUser_id(), userId);
    }

    public Set createSet(Set set) {
        Set createdSet = new Set();
        createdSet.setTitle(set.getTitle());
        createdSet.setDescription(set.getDescription());
        createdSet.setPublic(set.isPublic());
        createdSet.setUser_id(set.getUser_id());

        return setRepository.save(createdSet);
    }

    //tra ve toan bo set cua nguoi dung theo userId
    public Page<Set> getUserSets(Long userId, int page, int size) {
        // Truy vấn tất cả các set thuộc về userId từ cơ sở dữ liệu
        Pageable pageable = PageRequest.of(page, size);

        return setRepository.findByUserId(userId, pageable);
    }

    public String deleteSet(Long setId) {
        Set s = setRepository.findById(setId).orElse(null);
        if(s == null) {
            return "Set is not exist";
        }

        if(!isSetOwner(s)) {
            return "Unauthorized";
        }

        setRepository.deleteById(setId);
        return "Set is deleted";
    }

    public Page<Set> getAllSets(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        return setRepository.findAll(pageable);
    }

    public Set editSetById(Long setId, Set newSet) {
        Set set = setRepository.findById(setId).orElseThrow();

        if(!isSetOwner(set)) {
            return null;
        }

        set.setTitle(newSet.getTitle());
        set.setDescription(newSet.getDescription());
        set.setPublic(newSet.isPublic());

        setRepository.save(set);

        return set;
    }

    public Page<Set> getPublicSet(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        return setRepository.findByIsPublic(true, pageable);
    }

    public Page<Set> getSetByTitle(String title, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        return setRepository.findByTitleContainingAndIsPublic(title, true, pageable);

    }
}
