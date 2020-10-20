package com.banco.api.service.account;

import com.banco.api.dto.account.AccountType;
import com.banco.api.dto.movement.MovementType;
import com.banco.api.model.account.Savings;
import com.banco.api.service.MovementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

@Service
public class InterestsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(InterestsService.class);

    @Autowired
    private SavingsService savingsService;
    @Autowired
    private MovementService movementService;

    // First day of the month at 02:00:00 (2020-10-01 02:00:00; 2020-11-01 02:00:00, etc.)
    @Scheduled(cron = "0 0 2 1 1/1 ? *")
    public void applyInterestRate() {
        LOGGER.info("Savings account monthly interests process started");
        List<Savings> savingsAccounts = savingsService.findAll();

        Calendar calendar = Calendar.getInstance();
        int daysOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        savingsAccounts.forEach(savings -> {
            float oldBalance = savings.getBalance();
            float monthInterests = (oldBalance * (savings.getInterestRate() / 365) * daysOfMonth) / 100;
            savings.deposit(monthInterests);
            savingsService.update(savings);

            movementService.deposit(monthInterests, oldBalance, savings, MovementType.INTERESTS);
//            movementService.depositAndExtract(monthInterests, oldBalance, AccountType.SAVINGS, savings, null, MovementType.INTERESTS);

            LOGGER.info("Savings account number {} month interests deposited. Month interests: {}, New balance: {}",
                    savings.getAccountNumber(), monthInterests, savings.getBalance());
        });
    }
}
