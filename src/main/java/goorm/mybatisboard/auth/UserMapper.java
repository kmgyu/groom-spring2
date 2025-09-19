package goorm.mybatisboard.auth;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.context.annotation.Profile;

import java.util.Optional;

@Profile("mybatis")
@Mapper
public interface UserMapper {
    
    Optional<User> findByEmail(@Param("email") String email);
    
    Optional<User> findById(@Param("id") Long id);
    
    boolean existsByEmail(@Param("email") String email);
    
    void insert(User user);
    
    void update(User user);
    
    void deleteById(@Param("id") Long id);
}