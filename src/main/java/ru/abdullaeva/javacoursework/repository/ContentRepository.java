package ru.abdullaeva.javacoursework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.abdullaeva.javacoursework.model.base.Content;

import java.util.UUID;

@Repository
public interface ContentRepository extends JpaRepository<Content, UUID> {

}
