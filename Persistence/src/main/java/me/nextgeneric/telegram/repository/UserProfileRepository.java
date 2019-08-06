package me.nextgeneric.telegram.repository;

import me.nextgeneric.telegram.entity.UserProfile;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserProfileRepository extends JpaRepository<UserProfile, Integer> {

    @Query("select u from UserProfile u where u.phone = :phone")
    Optional<UserProfile> findByUsername(@Param("phone") String phone);

    @Query("select u from UserProfile u join fetch u.userCredentials where u.phone = :phone")
    Optional<UserProfile> findByUsernameWithCredentials(@Param("phone") String phone);

}
