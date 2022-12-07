package com.lisitsin.model;

import com.google.gson.annotations.Expose;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@Entity
@Table(name = "files")
public class MyFile {
    @Expose
    @EqualsAndHashCode.Exclude
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private Long id;
    @Expose
    @Column(name = "file_name")
    private String name;
    @Expose
    @Column(name = "file_path")
    private String filePath;
    @Expose
    @Column(name = "file_url")
    private String urlPath;

    @ToString.Exclude
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "event_id")
    private Event event;

    public MyFile(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public MyFile(Long id, String name, String urlPath, Event event) {
        this.id = id;
        this.name = name;
        this.urlPath = urlPath;
        this.event = event;
    }

    public MyFile(String name, Event event) {
        this.name = name;
        this.event = event;
    }

}
