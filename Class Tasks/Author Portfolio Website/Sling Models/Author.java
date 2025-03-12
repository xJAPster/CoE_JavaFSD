package com.myTraining.core.models;

import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.api.resource.Resource;

import javax.inject.Inject;


//first sling model pagman
@Model(adaptables = Resource.class)
public class Author {

    @Inject
    @Default(values="kek")
    String aname;

    @Inject
    @Default(values="bio")
    String abio;

    public String getAname() {
        return aname;
    }

    public String getAbio() {
        return abio;
    }
}
