package com.lucas.blockchain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Block {

    private int index;
    private List<Transaction> transactions;
    private String previousHash;
    private String hash;
    private long timestamp;
}
