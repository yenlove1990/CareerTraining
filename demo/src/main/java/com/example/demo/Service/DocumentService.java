package com.example.demo.Service;

import com.example.demo.DAO.DocumentDAO;
import com.example.demo.Entity.Document;
import com.example.demo.Service.AlreadyExistingException;
import com.example.demo.Service.NotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentService {
    private final DocumentDAO documentDao;
    @Autowired
    public DocumentService(DocumentDAO documentDAO){
        this.documentDao= documentDAO;
    }
    public Document getDocument(int id) throws NotExistException {
        Document temp = documentDao.getDocument(id);
        if(temp==null){
            throw new NotExistException("Document with "+ id+" not exist");
        }
        return temp;

    }
    public List<Document> listDocuments() {
        return documentDao.listDocuments();

    }
    public void create(Document doc) throws AlreadyExistingException {
        if(documentDao.getDocument(doc.getId()) != null) {
            throw new AlreadyExistingException("A document with id " + doc.getId() + " already exists.");
        }
        documentDao.create(doc);
    }
    public void delete(int id) throws NotExistException{
        if(documentDao.delete(id)==null){
            throw new NotExistException("Document with id "+ id+" not exist");
        };
    }
    public Document update(Document doc)throws NotExistException{
        Document temp = documentDao.getDocument(doc.getId());
        if(temp == null){
            throw new NotExistException("Document with id "+ doc.getId()+" not exist");
        }
        return documentDao.update(doc);
    }
}
