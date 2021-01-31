package com.petersoft.mgl.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Status implements Comparable<Status> {

    @Id
    private int statusId;
    private String title;

    @OneToMany(mappedBy = "status")
    private List<Lepcso> lepcsoList;

    public Status() {

    }

    public Status(int statusId) {
        this.statusId = statusId;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Lepcso> getLepcsoList() {
        return lepcsoList;
    }

    public void setLepcsoList(List<Lepcso> lepcsoList) {
        this.lepcsoList = lepcsoList;
    }

    @Override
    public String toString() {
        return title == null ? "null" : title;
    }

    @Override
    public int compareTo(Status o) {
        return (this.getStatusId() - o.getStatusId());
    }
}
