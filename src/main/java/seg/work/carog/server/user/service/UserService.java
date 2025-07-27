package seg.work.carog.server.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seg.work.carog.server.auth.dto.TokenUserInfo;
import seg.work.carog.server.common.constant.Message;
import seg.work.carog.server.common.exception.BaseException;
import seg.work.carog.server.user.dto.UserInfoResponse;
import seg.work.carog.server.user.entity.UserEntity;
import seg.work.carog.server.user.repository.UserRepository;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserInfoResponse getProfile(TokenUserInfo tokenUserInfo) {
        UserEntity userEntity = userRepository.findById(tokenUserInfo.getId()).orElseThrow(() -> new BaseException(Message.USER_NOT_FOUND));

        return new UserInfoResponse(userEntity);
    }
}
