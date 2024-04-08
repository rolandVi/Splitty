package server.service;

import commons.TagEntity;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import server.exception.ObjectNotFoundException;
import server.repository.TagRepository;

import java.util.Arrays;
import java.util.List;

@Service
public class TagService {

    private final TagRepository tagRepository;
    private final ModelMapper modelMapper;

    /**
     * Constructor injection
     * @param tagRepository the tagRepository
     * @param modelMapper modelMapper
     */
    public TagService(TagRepository tagRepository, ModelMapper modelMapper) {
        this.tagRepository = tagRepository;
        this.modelMapper = modelMapper;
    }

    public boolean tagExists(String tagType) {
        return tagRepository.findByTagType(tagType) != null;
    }

    public boolean addTag(String tagType) {
        if (!tagExists(tagType)) {
            TagEntity tagEntity = new TagEntity(tagType);
            tagRepository.save(tagEntity);
            return true;
        }
        return false;
    }

    /**
     * Retrieves all tags from the database
     * @return List of all tags
     */
    public List<TagEntity> getAllTags() {
        return tagRepository.findAll();
    }

    public TagEntity findById(Long id){
        return this.tagRepository.findById(id)
                .orElseThrow(()-> new ObjectNotFoundException("No such tag found"));
    }
}
