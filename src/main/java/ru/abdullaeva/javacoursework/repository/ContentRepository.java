package ru.abdullaeva.javacoursework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.abdullaeva.javacoursework.model.base.Content;

import java.util.List;
import java.util.UUID;

@Repository
public interface ContentRepository extends JpaRepository<Content, UUID> {

//    @Query("select content from Content content where content.state = \"PUBLISHED\"")
//    List<Content> findContentByStateIsPublished(List<Content> contents);

}
