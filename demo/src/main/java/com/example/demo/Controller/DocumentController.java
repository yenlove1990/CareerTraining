package com.example.demo.Controller;

import com.example.demo.Service.DocumentService;
import com.example.demo.Entity.Document;
import com.example.demo.Service.AlreadyExistingException;
import com.example.demo.Service.NotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/documents", consumes = "application/json")
public class DocumentController {
    private final DocumentService documentService;

    @Autowired
    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDocument(@PathVariable int id) {
        try {
            Document doc = documentService.getDocument(id);
            return new ResponseEntity<>(doc, HttpStatus.OK);
        } catch (NotExistException ne) {
            return new ResponseEntity<>(ne.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @GetMapping
    public ResponseEntity<List<Document>> listDocuments() {
        List<Document> documents = documentService.listDocuments();
        return new ResponseEntity<>(documents, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Document doc) {
        try {
            documentService.create(doc);
            return new ResponseEntity<>("Document created!", HttpStatus.OK);
        } catch (AlreadyExistingException ae) {
            return new ResponseEntity<>(ae.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        try {
            documentService.delete(id);
            return new ResponseEntity<>("Document deleted id :" + id, HttpStatus.OK);
        } catch (NotExistException ne) {
            return new ResponseEntity<>(ne.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody Document doc) {
        try {
            doc.setId(id);
            Document updatedDoc = documentService.update(doc);
            return new ResponseEntity<>(updatedDoc, HttpStatus.OK);
        } catch (NotExistException ne) {
            return new ResponseEntity<>(ne.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
