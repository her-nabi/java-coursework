package ru.abdullaeva.javacoursework.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.abdullaeva.javacoursework.model.base.Content;
import ru.abdullaeva.javacoursework.model.base.State;
import ru.abdullaeva.javacoursework.repository.ContentRepository;
import ru.abdullaeva.javacoursework.service.interf.ContentService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ContentServiceImpl implements ContentService {

    private final ContentRepository  contentRepository;
    @Autowired
    public ContentServiceImpl(ContentRepository contentRepository) {
        this.contentRepository = contentRepository;
    }

    @Override
    public void changeState(UUID id) {
      Optional<Content> contentOptional = contentRepository.findById(id);
        if(contentOptional.isPresent()) {
            Content item = contentOptional.get();
            if (item.getState() == State.DRAFT) {
                item.setState(State.PUBLISHED);
                contentRepository.save(item);
            }
        }
    }

    @Override
    public List<Content> getAllContent() {
        return contentRepository.findAll();
    }

}
