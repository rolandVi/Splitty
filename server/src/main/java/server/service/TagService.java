package server.service;

import commons.EventEntity;
import commons.TagEntity;
import dto.view.EventTitleDto;
import dto.view.TagDto;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
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

    public boolean addTag(String tagType, String hex) {
        if (!tagExists(tagType)) {
            TagEntity tagEntity = new TagEntity(tagType, hex);
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


    /**
     * Initializes default tags when the service is constructed
     */
    @PostConstruct
    public void initDefaultTags() {
        addTag("food", "#ff0000");
        addTag("entrance fees", "#0000ff");
        addTag("travel", "#008000");
    }

    @Transactional
    public void updateTag(Long tagId, String newTagType, String newHexValue) {
        tagRepository.findById(tagId).ifPresent(tag -> {
            tag.setTagType(newTagType);
            tag.setHexValue(newHexValue);
            tagRepository.save(tag);
        });
    }

    @Transactional
    public boolean deleteTagById(Long tagId) {
        if (tagRepository.existsById(tagId)) {
            tagRepository.deleteById(tagId);
            return true;
        }
        return false;
    }

}
