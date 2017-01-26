package ru.mrchebik.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.mrchebik.model.Post;

import java.util.List;

/**
 * Created by mrchebik on 14.01.17.
 */
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("select post.postId, post.title, post.text, post.date from ru.mrchebik.model.Post post where post.user.userId = :userId")
    List<Object[]> findByUser(@Param("userId") long id);

    @Modifying
    @Query("update ru.mrchebik.model.Post post set post.title = :title, post.text = :text where post.postId = :postId")
    void updatePost(@Param("postId") long id, @Param("title") String title, @Param("text") String text);
}
