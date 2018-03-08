package com.cinek.rozlitch.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Cinek on 09.11.2017.
 */
@Entity
public class MoneyRequest {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    @NotNull
    private User creator;
    @ManyToOne
    @NotNull
    private User requestedUser;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Item> items;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status;

    public MoneyRequest() {
        this.items  = new ArrayList();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public User getRequestedUser() {
        return requestedUser;
    }

    public void setRequestedUser(User requestedUser) {
        this.requestedUser = requestedUser;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
    public double getValue() {
        double sumValues = 0;
        for (Item item : items) {
            sumValues+= item.getValue();
        }
       return sumValues;
    }
    public void sortItems(Comparator<Item> comparator) {
        this.items.sort(comparator);
    }

    public void addItem(Item item) {
        this.items.add(item);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MoneyRequest)) return false;

        MoneyRequest that = (MoneyRequest) o;

        if (!id.equals(that.id)) return false;
        if (!creator.equals(that.creator)) return false;
        if (!requestedUser.equals(that.requestedUser)) return false;
        return status == that.status;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + creator.hashCode();
        result = 31 * result + requestedUser.hashCode();
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }
}
