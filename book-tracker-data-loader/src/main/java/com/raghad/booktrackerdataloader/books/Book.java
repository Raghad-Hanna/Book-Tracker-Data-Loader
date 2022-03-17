package com.raghad.booktrackerdataloader.books;

import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.LocalDate;
import java.util.List;

@Table(value = "book_by_id")
public class Book {
    @Id
    @PrimaryKeyColumn(name = "id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    @CassandraType(type = CassandraType.Name.TEXT)
    private String id;

    @CassandraType(type = CassandraType.Name.TEXT)
    private String title;

    @CassandraType(type = CassandraType.Name.TEXT)
    private String description;

    @Column(value = "published_date")
    @CassandraType(type = CassandraType.Name.DATE)
    private LocalDate publishedDate;

    @Column(value = "covers_ids")
    @CassandraType(type = CassandraType.Name.LIST, typeArguments = CassandraType.Name.TEXT)
    private List<String> coversIds;

    @Column(value = "authors_names")
    @CassandraType(type = CassandraType.Name.LIST, typeArguments = CassandraType.Name.TEXT)
    private List<String> authorsNames;

    @Column(value = "authors_ids")
    @CassandraType(type = CassandraType.Name.LIST, typeArguments = CassandraType.Name.TEXT)
    private List<String> authorsIds;
}
