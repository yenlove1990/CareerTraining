package com.example.demo.DAO;

import com.example.demo.Entity.Document;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class DocumentDAO {
    private Map<Integer, Document> documents = new HashMap();

    public Document getDocument(int id){
        return documents.get(id);
    }
    public List<Document> listDocuments(){
        return documents.values().stream().toList();
    }
    public void create(Document doc){

        documents.put(doc.getId(),doc);
    }
    public Document delete(int id){
        return documents.remove(id);
    }
    public Document update(Document doc){
        return documents.put(doc.getId(),doc);
    }
}
