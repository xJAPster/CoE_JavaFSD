package com.myTraining.core.models;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Default;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.day.cq.wcm.api.Page;
import com.myTraining.core.beans.BooksList;


@Model(adaptables = Resource.class)
public class BooksModel {

    List<BooksList> bookArray;
    private static final Logger LOGGER=LoggerFactory.getLogger(BooksModel.class);

    @Self
    Resource resource;

    @Inject
    @Default(values="myTraining/book-list")
    private String rootpath;

    @PostConstruct
    public void init(){

        ResourceResolver resourceResolver=resource.getResourceResolver();
        Session session=resourceResolver.adaptTo(Session.class);
        QueryBuilder builder=resourceResolver.adaptTo(QueryBuilder.class);

        Map<String,String> predicate=new HashMap<>();
        predicate.put("path",rootpath);
        predicate.put("type","cq:Page");
        Query query=null;

        try{
            query=builder.createQuery(PredicateGroup.create(predicate), session);
        }catch(Exception e){
            LOGGER.error("error encountered while creating query",e);
        }

        SearchResult searchResult=query.getResult();
        bookArray=new ArrayList<>();
        for(Hit hit:searchResult.getHits()){
            String path=null;
            BooksList bl=new BooksList();
            try{
                path=hit.getPath();
                Resource bookResource=resourceResolver.getResource(path);
                Page book=bookResource.adaptTo(Page.class);
                String title=book.getTitle();
                String description=book.getDescription();

                //if book page content is stored as text
                if (description == null || description.isEmpty()) {
                    Resource textComponent = bookResource.getChild("jcr:content/root/container/text");
                    if (textComponent != null) {
                        ValueMap properties = textComponent.getValueMap();
                        description = properties.get("text", String.class); // Fetch from Text Component
                    }
                }

                bl.setDescription(description);
                bl.setPath(path);
                bl.setTitle(title);
                bl.setDescription(description);
                LOGGER.debug("Title:{}, Description:{}",title,description);
                bookArray.add(bl);
            }catch(RepositoryException e){
                throw new RuntimeException(e);
            }
        }

    }

    public List<BooksList> getBookArray(){
        LOGGER.debug("booklist called");
        return bookArray;
    }


}