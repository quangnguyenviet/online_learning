package com.vitube.online_learning.service;

import java.util.List;

import com.vitube.online_learning.dto.request.UserRequest;
import com.vitube.online_learning.dto.response.UserResponse;
import com.vitube.online_learning.entity.User;

/**
 * Interface cung cấp các phương thức liên quan đến người dùng.
 */
public interface UserService {

    /**
     * Chuyển đổi đối tượng UserRequest thành User.
     *
     * @param user Yêu cầu người dùng.
     * @return Đối tượng người dùng.
     */
    User userRequestToUser(UserRequest user);

    /**
     * Chuyển đổi đối tượng User thành UserResponse.
     *
     * @param user Đối tượng người dùng.
     * @return Phản hồi người dùng.
     */
    UserResponse userToUserREsponse(User user);

    /**
     * Tạo người dùng mới.
     *
     * @param request Yêu cầu tạo người dùng.
     * @return Phản hồi người dùng sau khi tạo.
     */
    UserResponse createUser(UserRequest request);

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
    List<UserResponse> getAllUsers();

    /**
     * Lấy thông tin của người dùng hiện tại.
     *
     * @return Phản hồi thông tin của người dùng hiện tại.
     */
    UserResponse getMyInfo();
}