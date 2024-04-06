package com.lucas.blockchain.services;


import com.lucas.blockchain.models.Block;
import com.lucas.blockchain.models.Blockchain;
import com.lucas.blockchain.models.Transaction;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
public class BlockchainService {


    private final Blockchain blockchain;
    private List<Transaction> pendingTransaction = new ArrayList<>();
    private final int blockSizeLimit = 3;

    public BlockchainService(Blockchain blockchain) {
        this.blockchain = blockchain;
    }

    public ResponseEntity<String> addTransaction(Transaction transaction) {

        pendingTransaction.add(transaction);
        if (pendingTransaction.size() >= blockSizeLimit) {
            log.info("block size complete, create new block");
            createBlock();
        }
        log.info("Sending from {} to {} with a value of {} has been added, soon the block will be created", transaction.getSender(), transaction.getRecipient(), String.format("%.8f",transaction.getAmount()));
        return ResponseEntity.ok("Transaction added, then a block will be created");
    }

    private void createBlock() {

        Block previusBlock = blockchain.getChain().get(blockchain.getChain().size() - 1);
        Block newBlock = new Block();
        newBlock.setIndex(previusBlock.getIndex() + 1);
        newBlock.setPreviousHash(previusBlock.getHash());
        newBlock.setTransactions(pendingTransaction);
        newBlock.setTimestamp(System.currentTimeMillis());
        newBlock.setHash(calculateHash(newBlock));

        blockchain.getChain().add(newBlock);
        log.info("Block created with translations: {}", newBlock.getTransactions());
        pendingTransaction.clear();
    }

    private String calculateHash(Block newBlock) {

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String blockData = newBlock.getIndex() + newBlock.getTimestamp() + newBlock.getPreviousHash() + newBlock.getTransactions(); // Concatenação dos dados do bloco
            byte[] hash = digest.digest(blockData.getBytes());

            // Converter o hash de bytes para uma representação hexadecimal
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }

    }

}
