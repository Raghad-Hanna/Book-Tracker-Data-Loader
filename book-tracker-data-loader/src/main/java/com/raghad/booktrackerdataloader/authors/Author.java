package com.raghad.booktrackerdataloader.authors;

import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

@Table(value = "author_by_id")
public class Author {
    @Id
    @PrimaryKeyColumn(name = "id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    @CassandraType(type = CassandraType.Name.TEXT)
    private String id;

    @CassandraType(type = CassandraType.Name.TEXT)
    private String name;

    @Column(value = "personal_name")
    @CassandraType(type = CassandraType.Name.TEXT)
    private String personalName;

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPersonalName(String personalName) {
        this.personalName = personalName;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPersonalName() {
        return personalName;
    }
}
