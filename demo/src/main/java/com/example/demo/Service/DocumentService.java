package com.example.demo.Service;

import com.example.demo.DAO.DocumentDAO;
import com.example.demo.Entity.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentService {
    @Autowired
    private final DocumentDAO documentDao;

    public DocumentService(DocumentDAO documentDao) {
        this.documentDao = documentDao;
    }


    public Document getDocument(int id) throws NotExistException {
        return documentDao.findById(id)
                .orElseThrow(() -> new NotExistException("Document not found"));

    }
    public List<Document> listDocuments() {
        return documentDao.findAll();

    }
    public void create(Document doc) throws AlreadyExistingException {
        if (documentDao.findById(doc.getId()).isPresent()) {
            throw new AlreadyExistingException("A document with id " + doc.getId() + " already exists.");
        }
        documentDao.save(doc);

    }
    public void delete(int id) throws NotExistException{
        if(!documentDao.existsById(id)){
            throw new NotExistException("Document with id "+ id+" not exist");
        }
        documentDao.deleteById(id);
    }
    public Document update(Document doc)throws NotExistException{
        if(!documentDao.existsById(doc.getId())){
            throw new NotExistException("Document with id "+ doc.getId()+" not exist");
        }
        documentDao.save(doc);
        return doc;
    }
}
