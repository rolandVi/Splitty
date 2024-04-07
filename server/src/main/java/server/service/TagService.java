package server.service;

import commons.TagEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import server.repository.TagRepository;

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

    public void addTag(String tagType) {
        if (!tagExists(tagType)) {
            TagEntity tagEntity = new TagEntity();
            tagEntity.setTagType(tagType);
            tagRepository.save(tagEntity);
        }
    }
}
