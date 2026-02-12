package com.vitube.online_learning.service;

import com.vitube.online_learning.dto.UserDTO;
import com.vitube.online_learning.dto.request.UpdateUserRequest;
import com.vitube.online_learning.dto.request.UserCreationRequest;
import com.vitube.online_learning.entity.User;

import java.util.List;

/**
 * Interface cung cấp các phương thức liên quan đến người dùng.
 */
public interface UserService {

    /**
     * Tạo người dùng mới.
     *
     * @param request Yêu cầu tạo người dùng.
     * @return Phản hồi người dùng sau khi tạo.
     */
    UserDTO createUser(UserCreationRequest request);

    /**
     * Xóa người dùng theo ID.
     *
     * @param id ID của người dùng.
     */
    void deleteUser(String id);

    /**
     * Lấy danh sách tất cả người dùng.
     *
     * @return Danh sách phản hồi người dùng.
     */
    List<UserDTO> getAllUsers();

    /**
     * Lấy thông tin của người dùng hiện tại.
     *
     * @return Phản hồi thông tin của người dùng hiện tại.
     */
    UserDTO getMyInfo();

    /**
     * Cập nhật thông tin người dùng hiện tại.
     *
     * @param request Yêu cầu cập nhật người dùng.
     * @return Phản hồi người dùng sau khi cập nhật.
     */
    UserDTO updateMyInfo(UpdateUserRequest request);

    User getCurrentUser();

}