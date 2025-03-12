package com.myTraining.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;

import javax.inject.Inject;

@Model(adaptables= Resource.class)
public class BooksHeader {

    @Inject
    @Default(values="https://youtu.be/dQw4w9WgXcQ?si=rdI6JoPi_XvQcHj0")
    String homelink;

    @Inject
    @Default(values="https://youtu.be/dQw4w9WgXcQ?si=rdI6JoPi_XvQcHj0")
    String bookslink;

    @Inject
    @Default(values="https://youtu.be/dQw4w9WgXcQ?si=rdI6JoPi_XvQcHj0")
    String aboutmelink;

    @Inject
    @Default(values="https://youtu.be/dQw4w9WgXcQ?si=rdI6JoPi_XvQcHj0")
    String reachmelink;

    public String getHomelink() {
        return homelink;
    }

    public String getBookslink() {
        return bookslink;
    }

    public String getAboutmelink() {
        return aboutmelink;
    }

    public String getReachmelink() {
        return reachmelink;
    }
}
