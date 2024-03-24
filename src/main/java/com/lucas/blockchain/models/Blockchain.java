package com.lucas.blockchain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;


import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class Blockchain {

    private List<Block> chain;
}
