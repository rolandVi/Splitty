package server.service;


import commons.TagEntity;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import server.exception.ObjectNotFoundException;
import server.repository.TagRepository;

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

    /**
     * finds tags by tagtypes
     * @param tagType the tagtype of a tag
     * @return true/false based on the existance of the tag
     */
    public boolean tagExists(String tagType) {
        return tagRepository.findByTagType(tagType) != null;
    }

    /**
     * adds a tag to the database
     * @param tagType tagtype
     * @param hex hex
     * @return true or false based on if it was successful
     */
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

    /**
     * finds tags by id
     * @param id id
     * @return the tagentity
     */
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

    /**
     * updates a tag
     * @param tagId the id
     * @param newTagType the new value for the tagtype
     * @param newHexValue the new hex
     */
    @Transactional
    public void updateTag(Long tagId, String newTagType, String newHexValue) {
        tagRepository.findById(tagId).ifPresent(tag -> {
            tag.setTagType(newTagType);
            tag.setHexValue(newHexValue);
            tagRepository.save(tag);
        });
    }

    /**
     * deletes tags on their id
     * @param tagId the tag id
     * @return true or false based on succession
     */
    @Transactional
    public boolean deleteTagById(Long tagId) {
        if (tagRepository.existsById(tagId)) {
            tagRepository.deleteById(tagId);
            return true;
        }
        return false;
    }

}
