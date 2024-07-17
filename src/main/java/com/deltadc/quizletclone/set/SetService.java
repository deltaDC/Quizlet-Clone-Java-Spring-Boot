package com.deltadc.quizletclone.set;

import com.deltadc.quizletclone.user.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SetService {
    private final SetRepository setRepository;
    private final UserService userService;

    private boolean isSetOwner(Set s) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        String username = userDetails.getUsername();

        UserDTO user = userService.getUserByEmail(username);
        Long userId = user.getUser_id();

        if(user.getRole().compareTo(Role.ADMIN) == 0) {
            System.out.println(user.getRole());
            return true;
        }

        return Objects.equals(s.getUser_id(), userId);
    }

    public Set createSet(Set set) {
        Set createdSet = Set.builder()
                .title(set.getTitle())
                .description(set.getDescription())
                .isPublic(set.isPublic())
                .user_id(set.getUser_id())
                .build();

        return setRepository.save(createdSet);
    }

    public String deleteSet(Long setId) {
        Set s = setRepository.findById(setId).orElseThrow();

        if(!isSetOwner(s)) {
            return "Unauthorized";
        }

        setRepository.deleteById(setId);
        return "Set is deleted";
    }

    public Set editSetById(Long setId, Set newSet) {
        Set set = setRepository.findById(setId).orElseThrow();

        if(!isSetOwner(set)) {
            return null;
        }

        set.setTitle(newSet.getTitle());
        set.setDescription(newSet.getDescription());
        set.setPublic(newSet.isPublic());

        return setRepository.save(set);
    }

    public Set getSetById(Long setId) throws Exception {
        return setRepository.findById(setId)
                .orElseThrow(() -> new Exception("Set not found"));
    }

    public Page<Set> getSetByFilter(String title, Boolean isPublic, Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Specification<Set> setSpecification = SetSpecification.withDynamicQuery(title, isPublic, userId);

        return setRepository.findAll(setSpecification, pageable);
    }
}
