package ru.itmo.wp.form;

import org.springframework.lang.Nullable;

import javax.validation.constraints.*;

public class PostWriteForm {
    @NotEmpty
    @NotNull
    @Size(min = 1, max = 60)
    String title;

    @NotEmpty
    @NotNull
    @Size(min = 1, max = 65000)
    String text;

    @NotBlank
    String tags;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}