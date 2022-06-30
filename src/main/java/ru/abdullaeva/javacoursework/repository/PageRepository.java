package ru.abdullaeva.javacoursework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.abdullaeva.javacoursework.model.base.Page;

import java.util.Optional;
import java.util.UUID;

public interface PageRepository extends JpaRepository<Page, UUID> {

    Optional<Page> findByName(String pageName);
}
