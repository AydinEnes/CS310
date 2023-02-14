package hwPack.controller;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RestController;

import hwPack.model.Account;
import hwPack.model.AccountPayload;
import hwPack.model.ReturnData;
import hwPack.model.Summary;
import hwPack.model.Transaction;
import hwPack.model.TransactionDisplay;
import hwPack.model.TransactionPayload;
import hwPack.repo.AccountRepo;
import hwPack.repo.TransactionRepo;

import org.springframework.beans.factory.annotation.Autowired;

@RestController
public class BankingRestController {
	
	
	@Autowired private AccountRepo accountRepo;
	@Autowired private TransactionRepo transactionRepo;
	
	
	@PostConstruct
	public void init() {
		
		if(accountRepo.count() == 0) {
			
			Account acc1 = new Account("1111", "Jack Johns");
			Account acc2 = new Account("2222", "Henry Williams");
			
			accountRepo.save(acc1);
			accountRepo.save(acc2);
			
			Transaction trans1 = new Transaction("1111", "2222", 1500.0);
			Transaction trans2 = new Transaction("2222", "1111", 2500.0);
			
			transactionRepo.save(trans1);
			transactionRepo.save(trans2);
		}
		
	}
	
	@PostMapping("/account/save")
	public ReturnData<Account> saveAccount(@RequestBody AccountPayload account) {	
		
		try{
			if(account.getId() == null || account.getOwner() == null) {
				throw new Exception("ERROR:missing fields");
			}
			
			Account newAccount = new Account(account.getId(), account.getOwner());
			accountRepo.save(newAccount);
			return new ReturnData<Account>("SUCCESS", newAccount);
		}
		
		catch(Exception ex){
			return new ReturnData<Account>(ex.getMessage(), null);
		}
		
		
		
	}
	
	@PostMapping("transaction/save")
	public ReturnData<TransactionDisplay> saveTransaction(@RequestBody TransactionPayload transaction) {
		
		try{
			if(transaction.getAmount() == null || transaction.getFromAccountId() == null || transaction.getToAccountId() == null) {
				throw new Exception("ERROR:missing fields");
			}
			
			if(accountRepo.findById(transaction.getFromAccountId()).isPresent() == false || accountRepo.findById(transaction.getToAccountId()).isPresent() == false) {
				throw new Exception("ERROR: account id");
			}
			
			Transaction newTransaction = new Transaction(transaction.getFromAccountId(), transaction.getToAccountId(), transaction.getAmount());
			
			transactionRepo.save(newTransaction);
			
			TransactionDisplay transactionDisplay = new TransactionDisplay(newTransaction.getId(), accountRepo.findById(newTransaction.getFromAccountId()).get(), 
					accountRepo.findById(newTransaction.getToAccountId()).get(), newTransaction.getCreateDate(), newTransaction.getAmount());
					
			return new ReturnData<TransactionDisplay>("SUCCESS", transactionDisplay);
			
		}
		catch(Exception ex){
			return new ReturnData<TransactionDisplay>(ex.getMessage(), null);
		}
		
		
	}
	
	@GetMapping("/account/{id}")
	public ReturnData<Summary> accountSummary(@PathVariable String id){
		
		try {
			if(accountRepo.findById(id).isPresent() == false) {
				throw new Exception("ERROR:account doesnt exist!");
			}
			
			String owner = accountRepo.findById(id).get().getOwner();
			String createDate = accountRepo.findById(id).get().getCreateDate();
			
			List<Transaction> transactionsFrom = transactionRepo.findByFromAccountId(id);
			List<Transaction> transactionsTo = transactionRepo.findByToAccountId(id);
			
			double balance = 0;
			
			for(Transaction t : transactionsFrom) {
				balance -= t.getAmount();
			}
			
			for(Transaction t: transactionsTo) {
				balance += t.getAmount();
			}
			
			List<TransactionDisplay> displayIn = new ArrayList<TransactionDisplay>();
			for(Transaction t: transactionsTo) {
				TransactionDisplay tDisplay = new TransactionDisplay(t.getId(), 
						accountRepo.findById(t.getFromAccountId()).get(), accountRepo.findById(t.getToAccountId()).get(), 
						t.getCreateDate(), t.getAmount());
				displayIn.add(tDisplay);
			}
			
			List<TransactionDisplay> displayOut = new ArrayList<TransactionDisplay>();
			for(Transaction t: transactionsFrom) {
				TransactionDisplay tDisplay = new TransactionDisplay(t.getId(), 
						accountRepo.findById(t.getFromAccountId()).get(), accountRepo.findById(t.getToAccountId()).get(), 
						t.getCreateDate(), t.getAmount());
				displayOut.add(tDisplay);
			}
			

			Summary summary = new Summary(id, owner, createDate, displayOut, displayIn, balance);
			return new ReturnData<Summary>("SUCCESS", summary);
			
		}
		catch(Exception ex) {
			return new ReturnData<Summary>(ex.getMessage(), null);
		}
	}
	
	@GetMapping("/transaction/to/{id}")
	public ReturnData<List<TransactionDisplay>> transactionTo(@PathVariable String id){
		
		try {
			if(accountRepo.findById(id).isPresent() == false) {
				throw new Exception("ERROR:account doesnt exist!");
			}
			
			
			List<Transaction> transactionTo = transactionRepo.findByToAccountId(id);
			List<TransactionDisplay> displayIn = new ArrayList<TransactionDisplay>();
			
			for(Transaction t: transactionTo) {
				TransactionDisplay tDisplay = new TransactionDisplay(t.getId(), 
						accountRepo.findById(t.getFromAccountId()).get(), accountRepo.findById(t.getToAccountId()).get(), 
						t.getCreateDate(), t.getAmount());
				displayIn.add(tDisplay);
			}
			
			return new ReturnData<List<TransactionDisplay>>("SUCCESS", displayIn);
			
		}
		catch(Exception ex) { 
			return new ReturnData<List<TransactionDisplay>>(ex.getMessage(), null);
		}
		
	}
	
	@GetMapping("/transaction/from/{id}")
	public ReturnData<List<TransactionDisplay>> transactionFrom(@PathVariable String id){

		try {
			if(accountRepo.findById(id).isPresent() == false) {
				throw new Exception("ERROR:account doesnt exist!");
			}
			
			List<Transaction> transactionFrom = transactionRepo.findByFromAccountId(id);
			List<TransactionDisplay> displayOut = new ArrayList<TransactionDisplay>();
			
			for(Transaction t: transactionFrom) {
				TransactionDisplay tDisplay = new TransactionDisplay(t.getId(), 
						accountRepo.findById(t.getFromAccountId()).get(), accountRepo.findById(t.getToAccountId()).get(), 
						t.getCreateDate(), t.getAmount());
				displayOut.add(tDisplay);
			}
			
			return new ReturnData<List<TransactionDisplay>>("SUCCESS", displayOut);
			
		}
		catch(Exception ex) { 
			return new ReturnData<List<TransactionDisplay>>(ex.getMessage(), null);
		}
		
	}
}
