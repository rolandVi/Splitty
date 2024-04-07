package server.controller.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.service.TagService;

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


}
