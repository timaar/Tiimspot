package com.timaar.tiimspot.repository;

import com.timaar.tiimspot.domain.Comment;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Comment entity.
 */
public interface CommentRepository extends JpaRepository<Comment,Long> {

}
