package com.diyiliu.web.guide.dto;

import javax.persistence.*;

/**
 * Description: Website
 * Author: DIYILIU
 * Update: 2017-10-19 11:01
 */

@Entity
@Table(name = "guide_site")
public class Website {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String url;

    private String image;

    @ManyToOne
    @JoinColumn(name = "type")
    private SiteType siteType;

    private int top;

    public void setUrl(String url) {
        String regex = "[hH][tT]{2}[pP]://|[hH][tT]{2}[pP][sS]://|/+$";
        this.url = url.replaceAll(regex, "");
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public SiteType getSiteType() {
        return siteType;
    }

    public void setSiteType(SiteType siteType) {
        this.siteType = siteType;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }
}
