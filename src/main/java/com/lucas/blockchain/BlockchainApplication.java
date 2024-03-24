package com.lucas.blockchain;

import com.lucas.blockchain.models.Block;
import com.lucas.blockchain.models.Blockchain;
import com.lucas.blockchain.models.Transaction;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class BlockchainApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlockchainApplication.class, args);
	}

	@Bean
	public Blockchain blockchain() {
		Block genesisBlock = createGenesisBlock();
		Blockchain blockchain = new Blockchain();
		blockchain.setChain(new ArrayList<>());
		blockchain.getChain().add(genesisBlock);
		return blockchain;
	}


	private static Block createGenesisBlock() {
		Block genesisBlock = new Block();
		genesisBlock.setIndex(0);
		genesisBlock.setTransactions(createGenesisTransaction());
		genesisBlock.setPreviousHash("0");
		genesisBlock.setHash("0");
		genesisBlock.setTimestamp(System.currentTimeMillis());


		return genesisBlock;
	}

	private static List<Transaction> createGenesisTransaction() {

		List<Transaction> genesisTransaction = new ArrayList<>();
		genesisTransaction.add(Transaction.builder()
						.amount(1.23)
						.recipient("0x10987654321zxcvb")
						.sender("0x12345678910abcdef")
				.build());

		return genesisTransaction;
	}

}
