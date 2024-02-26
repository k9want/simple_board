package com.example.simpleboard.post.db;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<PostEntity, Long> {

    // select * from post where id = ? and status = ? order by id desc limit 1
    Optional<PostEntity> findFirstByIdAndStatusOrderByIdDesc(Long id, String status);
}
