package server.controller.api;

import commons.TagEntity;
import dto.view.TagDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import server.service.TagService;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TagRestControllerTest {

    @Mock
    private TagService tagService;

    private TagRestController tagRestController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        tagRestController = new TagRestController(tagService);
    }

    @Test
    void testGetAllTags() {
        List<TagEntity> tags = new ArrayList<>();
        tags.add(new TagEntity("Food", "#FF0000"));
        tags.add(new TagEntity("Travel", "#00FF00"));

        when(tagService.getAllTags()).thenReturn(tags);

        ResponseEntity<List<TagEntity>> responseEntity = tagRestController.getAllTags();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(tags, responseEntity.getBody());
    }

    @Test
    void testCreateNewTag_Success() {
        TagDto request = new TagDto(1L, "Food", "#FF0000");

        when(tagService.addTag("Food", "#FF0000")).thenReturn(true);

        ResponseEntity<String> responseEntity = tagRestController.createNewTag(request);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals("Tag created successfully", responseEntity.getBody());
    }

    @Test
    void testCreateNewTag_TagAlreadyExists() {
        TagDto request = new TagDto(1L, "Food", "#FF0000");

        when(tagService.addTag("Food", "#FF0000")).thenReturn(false);

        ResponseEntity<String> responseEntity = tagRestController.createNewTag(request);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Tag already exists", responseEntity.getBody());
    }

    @Test
    void testUpdateTag_Success() {
        Long id = 1L;
        TagDto request = new TagDto(1L, "Food", "#FF0000");
        TagEntity existingTag = new TagEntity("Old Tag", "#000000");

        when(tagService.findById(id)).thenReturn(existingTag);

        ResponseEntity<String> responseEntity = tagRestController.updateTag(id, request);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Tag updated successfully", responseEntity.getBody());
        assertEquals("Food", existingTag.getTagType());
        assertEquals("#FF0000", existingTag.getHexValue());
    }

    @Test
    void testUpdateTag_TagNotFound() {
        Long id = 1L;
        TagDto request = new TagDto(1L, "Food", "#FF0000");

        when(tagService.findById(id)).thenReturn(null);

        ResponseEntity<String> responseEntity = tagRestController.updateTag(id, request);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Tag not found", responseEntity.getBody());
    }

    @Test
    void testDeleteTag_Success() {
        Long id = 1L;

        when(tagService.deleteTagById(id)).thenReturn(true);

        ResponseEntity<String> responseEntity = tagRestController.deleteTag(id);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Tag deleted successfully", responseEntity.getBody());
    }

    @Test
    void testDeleteTag_TagNotFound() {
        Long id = 1L;

        when(tagService.deleteTagById(id)).thenReturn(false);

        ResponseEntity<String> responseEntity = tagRestController.deleteTag(id);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Tag not found", responseEntity.getBody());
    }
}
