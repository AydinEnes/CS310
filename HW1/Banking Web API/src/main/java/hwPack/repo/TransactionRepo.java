package hwPack.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
//import org.springframework.data.mongodb.repository.Query;

import hwPack.model.Transaction;

public interface TransactionRepo extends MongoRepository<Transaction, String>{
	public List<Transaction> findByFromAccountId(String id);
	public List<Transaction> findByToAccountId(String id);
}
