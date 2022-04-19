package com.example.bankroute.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Document
@AllArgsConstructor
@NoArgsConstructor
public class Client {
    private String id;
    private String civility;
    private String fname;
    private String nname;
    private String lname;
    private String dob;
    private String resources;
    private String charges;
    private String mail;
    private String number;
    private String status;
}