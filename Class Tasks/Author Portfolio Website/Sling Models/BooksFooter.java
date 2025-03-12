package com.myTraining.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;

import javax.inject.Inject;

@Model(adaptables = Resource.class)
public class BooksFooter {

    @Inject
    @Default(values="https://youtu.be/dQw4w9WgXcQ?si=rdI6JoPi_XvQcHj0")
    String aboutmelink;

    @Inject
    @Default(values="https://youtu.be/dQw4w9WgXcQ?si=rdI6JoPi_XvQcHj0")
    String reachmelink;

    @Inject
    @Default(values="https://youtu.be/dQw4w9WgXcQ?si=rdI6JoPi_XvQcHj0")
    String bookslink;

    public String getAboutmelink() {
        return aboutmelink;
    }

    public String getReachmelink() {
        return reachmelink;
    }

    public String getBookslink() {
        return bookslink;
    }
}
