package server.controller.api;

import commons.TagEntity;
import dto.view.TagDto;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.service.TagService;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
public class TagRestController {


    private final TagService tagService;

    /**
     * constructor injection
     * @param tagService tagService
     */
    public TagRestController(TagService tagService) {
        this.tagService = tagService;
    }

    /**
     * Endpoint to get all tags
     * @return ResponseEntity with list of tags or appropriate error response
     */
    @GetMapping("/all")
    public ResponseEntity<List<TagEntity>> getAllTags() {
        List<TagEntity> tags = tagService.getAllTags();
        return new ResponseEntity<>(tags, HttpStatus.OK);
    }

    @PostMapping("/newtag")
    public ResponseEntity<String> createNewTag(@RequestBody TagDto request) {
        String tagName = request.getTagType(); // Assuming tagType holds the tag name
        String hexValue = request.getHexValue();

        boolean success = tagService.addTag(tagName, hexValue);
        if (success) {
            return new ResponseEntity<>("Tag created successfully", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Tag already exists", HttpStatus.BAD_REQUEST);
        }
    }

}
