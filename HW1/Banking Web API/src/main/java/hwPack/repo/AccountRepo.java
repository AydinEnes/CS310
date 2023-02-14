package hwPack.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import hwPack.model.Account;

public interface AccountRepo extends MongoRepository<Account, String>{
	
}
