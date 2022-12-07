package com.lisitsin.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@Entity
@Table(name = "events")
public class Event {

    @EqualsAndHashCode.Exclude
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Expose
    private Long id;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @Expose
    @Column(name = "event_name")
    private String name;
    @Expose
    @OneToMany(mappedBy = "event", cascade = CascadeType.PERSIST)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<MyFile> myFiles;

    public Event(User user, String name, List<MyFile> myFiles) {
        this.user = user;
        this.name = name;
        this.myFiles = myFiles;
    }

    public Event(User user, String name) {
        this.user = user;
        this.name = name;
    }

    public Event(Long id, User user, String name) {
        this.id = id;
        this.user = user;
        this.name = name;
    }


    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", myFiles=" + myFiles +
                '}';
    }
}
