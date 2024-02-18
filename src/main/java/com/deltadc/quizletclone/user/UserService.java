package com.deltadc.quizletclone.user;

import com.deltadc.quizletclone.folder.Folder;
import com.deltadc.quizletclone.folder.FolderRepository;
import com.deltadc.quizletclone.set.Set;
import com.deltadc.quizletclone.set.SetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final SetRepository setRepository;
    private final FolderRepository folderRepository;

//
//    public boolean signUp(User user) {
//        // Kiểm tra xem người dùng có tồn tại dựa trên email hay không
//        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
//
//        if (existingUser.isPresent()) {
//            return false;
//        }
//
//        // Người dùng chưa tồn tại, thêm mới
//        userRepository.save(user);
//        return true;
//    }
//
//    public boolean login(User user) {
//        // Kiểm tra xem người dùng có tồn tại dựa trên email hay không
//        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
//
//        if (existingUser.isPresent()) {
//            return false;
//        }
//        return true;
//    }
    public List<Set> getUserSets(Long userId) {
        // Truy vấn tất cả các set thuộc về userId từ cơ sở dữ liệu
        List<Set> userSets = setRepository.findByUserId(userId);

        // Trả về danh sách các set cho frontend
        return userSets;
    }


    public List<Folder> getUserFolders(Long userId) {
        // Truy vấn tất cả các set thuộc về userId từ cơ sở dữ liệu
        List<Folder> userFolders = folderRepository.findByUserId(userId);

        // Trả về danh sách các set cho frontend
        return userFolders;
    }
}
