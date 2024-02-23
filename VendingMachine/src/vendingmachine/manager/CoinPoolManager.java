package vendingmachine.manager;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import vendingmachine.exceptions.ExactChangeUnavailable;
import vendingmachine.exceptions.InsufficientMoneyProvided;
import vendingmachine.model.Denominations;

public class CoinPoolManager implements Manager {
	Map<Double, Integer> moneyPool;
	
	public CoinPoolManager() {
		this.moneyPool = new HashMap<Double, Integer>();
		initialize();
	}
	
	public void initialize() {
		this.moneyPool.put(Denominations.ONE_PENNY.getValueOfDenomination(), 0);//1
		this.moneyPool.put(Denominations.TWO_PENCE.getValueOfDenomination(), 0);//2
		this.moneyPool.put(Denominations.FIVE_PENCE.getValueOfDenomination(), 0);//5
		this.moneyPool.put(Denominations.TEN_PENCE.getValueOfDenomination(), 0);//10
		this.moneyPool.put(Denominations.TWENTY_PENCE.getValueOfDenomination(), 0);//20
		this.moneyPool.put(Denominations.FIFTY_PENCE.getValueOfDenomination(), 0);//50
		this.moneyPool.put(Denominations.ONE_POUND.getValueOfDenomination(), 10);//100
		this.moneyPool.put(Denominations.TWO_POUNDS.getValueOfDenomination(), 10);//200

	}
	
	public Double getTotalCashInMachine () {
		Double moneyInMachine = 0.0;
		for(Map.Entry<Double, Integer> entry : this.moneyPool.entrySet()) {
			moneyInMachine = moneyInMachine + (entry.getKey() * entry.getValue());
		}
		return moneyInMachine;
	}
	
	public void printCash()
	{
	    for (Double key : this.moneyPool.keySet()) {
	        System.out.println(Denominations.getDenomination(key) + ": " + this.moneyPool.get(key));
	    }
	}
	
	public Denominations getDenomination(String coinValue) throws Exception {
	    switch (coinValue) {
	        case "1p":
	        case "0.01":
	        	return Denominations.ONE_PENNY; 
	        case "2p":
	        case "0.02":
	        	return Denominations.TWO_PENCE; 
	        case "5p":
	        case "0.05":
	        	return Denominations.FIVE_PENCE; 
	        case "10p":
	        case "0.1":
	        case "0.10":
	        	return Denominations.TEN_PENCE; 
	        case "20p":
	        case "0.2":
	        case "0.20":
	        	return Denominations.TWENTY_PENCE; 
	        case "50p":
	        case "0.5":
	        case "0.50":
	        	return Denominations.FIFTY_PENCE; 
	        case "£1":
	        case "£1.00":
	    	case "1.00":
	        	return Denominations.ONE_POUND; 
	        case "£2":
	        case "£2.00":
	    	case "2.00":
	        	return Denominations.TWO_POUNDS; 	        
	        default:
	            throw new Exception("Denomination not found!");
	    }

	}
	
	public boolean isValidDenomination(String userInput) {
		try {
			getDenomination(userInput);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public boolean acceptCash(List<String> coinsInserted) throws Exception {
		for(String coin : coinsInserted) {
			addCoin(getDenomination(coin));
		}
		return true;
	}
	
	public List<Double> rejectCash(List<String> coinsInserted, boolean cashAccepted) throws Exception {
		List<Double> rejectedCoins = new ArrayList<Double>();
		for(String coin : coinsInserted) {
			Denominations denom = getDenomination(coin);
			rejectedCoins.add(denom.getValueOfDenomination());
			if(cashAccepted)
				removeCoin(denom);
		}
		return rejectedCoins;
	}
	
	public void addCoin(Denominations coin) {
		Double c = coin.getValueOfDenomination();
		this.moneyPool.put(c, this.moneyPool.get(c) + 1);
	}
	
	public void removeCoin(Denominations coin) {
		Double c = coin.getValueOfDenomination();
		this.moneyPool.put(c, this.moneyPool.get(c) - 1);
	}
	
	public Double moneyDeposited(List<String> coinsInserted) throws Exception {
		Double money = 0.0;
		for(String coin : coinsInserted) {
			money = money + getDenomination(coin).getValueOfDenomination();
		}
		
		return money;
	}
	
	public Double calculateChange(Double moneyDeposited, Double productPrice) {
		
		//return (Double) Math.floor((moneyDeposited - productPrice)*100) / 100;
		return round(moneyDeposited - productPrice, 2);
		
	}
	
	public List<Double> updateCash(Double difference) throws Exception {
	    Double cashTotal = getTotalCashInMachine();

	    if(cashTotal < difference)
	        throw new Exception ("Insufficient funds, exiting program...");
	    
	    //obtaining the list of coins sizes and ordering it in descending order
	    List<Double> keys = new ArrayList<Double>(this.moneyPool.keySet());
	    List<Double> moneyOutOfPool = new ArrayList<Double>();
	    keys.sort(Collections.reverseOrder());

	    //for each coin size, starting from higher values
	    for(Double key : keys)
	    {
	    	while (key <= difference && this.moneyPool.get(key) > 0) {
	    		int prevVal = this.moneyPool.get(key);
	            difference = (double) (Math.round((difference - key)*100))/100;
	            //difference = difference / 100;
	            this.moneyPool.put(key, prevVal - 1);
	            moneyOutOfPool.add(key);
	    	}
	    }

	    //difference = (Double) Math.floor(difference * 100) / 100;
	    difference = round(difference, 2);
	    
	    if(difference != 0) {
	    	for(Double key : moneyOutOfPool) {
	    		int prevVal = this.moneyPool.get(key);
	            this.moneyPool.put(key, prevVal + 1);
	    	}
	    	throw new ExactChangeUnavailable("Exact change not available! Returning money you entered! Please provide exact cash.");
	    }
	    
	    return moneyOutOfPool;
	}
	
	private double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = BigDecimal.valueOf(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
	
	public List<Double> processTransaction(List<String> coinsInserted, Double productValue) throws Exception {
		Double moneyInserted = moneyDeposited(coinsInserted);
		Double difference = calculateChange (moneyInserted, productValue);
		List<Double> coinsReturned = new ArrayList<Double>();
		boolean cashAccepted = false;
		try {
			Double change = calculateChange (moneyInserted, productValue);
			if(calculateChange (moneyInserted, productValue) < 0) {
				throw new InsufficientMoneyProvided (" Insufficient Money inserted. Please insert " + (change * -1));
			}
			cashAccepted = acceptCash(coinsInserted);
			coinsReturned = updateCash(difference);
		} catch (InsufficientMoneyProvided e) {
			System.out.println(e.getMessage());
			throw e;
		}
		catch (ExactChangeUnavailable e) {
			coinsReturned = rejectCash(coinsInserted, cashAccepted);
			System.out.println(e.getMessage());
			System.out.println(coinsReturned);
			throw e;
		}
		catch (Exception e) {
			coinsReturned = rejectCash(coinsInserted, cashAccepted);
			System.out.println(e.getMessage());
			System.out.println(coinsReturned);
			throw e;
		}
		
		return coinsReturned;		
	}

	public static void main (String args[]) throws Exception {
		CoinPoolManager cp = new CoinPoolManager();
		System.out.println(cp.getTotalCashInMachine());
		cp.printCash();
		System.out.println("****************************************************************************************************");
		
		List <String> coinsInserted = Arrays.asList(new String[]{"0.1", "0.05", "0.20", "0.02", "£2"});
		List<Double> cashReturned = cp.processTransaction(coinsInserted, 1.25);
		cp.printCash();
		System.out.println("Item Price : " + 1.25);
		System.out.println("Money inserted : " + coinsInserted + " . That is : " + cp.moneyDeposited(coinsInserted));

		System.out.println("Coins Returned : ");
		cashReturned.forEach(c -> System.out.println(c));
	}
}
