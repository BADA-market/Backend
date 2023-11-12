package com.bada.Backend.domain.User.repository;

import com.bada.Backend.domain.User.entity.SocialType;
import com.bada.Backend.domain.User.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByNickname(String nickname);

    Optional<User> findByRefreshToken(String refreshToken);

    /*
    소셜 타입(카카오,네이버)와 소셜의 식별값으로 회원 찾기
    첫 소셜 로그인 시 DB에 저장되는데 기본정보 말고는 비워져 있어서 채워줘야 함
    채워주기 위해서는 DB에서 가져와야 하는데 그때 찾기 위한 메서드야
     */
    Optional<User> findBySocialTypeAndSocialId(SocialType socialType, String socialId);
}
