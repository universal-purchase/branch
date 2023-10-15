package com.chocolate.blogsch.search.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;

public class RssContainer {
    private Channel channel;

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public static class Channel {
        private Date lastBuildDate;
        private Integer total;
        private Integer start;
        private Integer display;
        private List<Item> item;

        @JsonProperty("lastBuildDate")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        public Date getLastBuildDate() {
            return lastBuildDate;
        }

        public void setLastBuildDate(Date lastBuildDate) {
            this.lastBuildDate = lastBuildDate;
        }

        public Integer getTotal() {
            return total;
        }

        public void setTotal(Integer total) {
            this.total = total;
        }

        public Integer getStart() {
            return start;
        }

        public void setStart(Integer start) {
            this.start = start;
        }

        public Integer getDisplay() {
            return display;
        }

        public void setDisplay(Integer display) {
            this.display = display;
        }

        public List<Item> getItem() {
            return item;
        }

        public void setItem(List<Item> item) {
            this.item = item;
        }
    }

    public static class Item {
        private String title;
        private String link;
        private String description;
        private String bloggername;
        private String bloggerlink;

        @JsonProperty("postdate")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        private Date postDate;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getBloggername() {
            return bloggername;
        }

        public void setBloggername(String bloggername) {
            this.bloggername = bloggername;
        }

        public String getBloggerlink() {
            return bloggerlink;
        }

        public void setBloggerlink(String bloggerlink) {
            this.bloggerlink = bloggerlink;
        }

        public Date getPostDate() {
            return postDate;
        }

        public void setPostDate(Date postDate) {
            this.postDate = postDate;
        }
    }
}

