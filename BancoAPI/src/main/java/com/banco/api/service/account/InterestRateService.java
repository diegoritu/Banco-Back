package com.banco.api.service.account;

import com.banco.api.model.account.Savings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

@Service
public class InterestRateService {

    @Autowired
    private SavingsService savingsService;

    // First day of the month at 02:00:00 (2020-10-01 02:00:00; 2020-11-01 02:00:00, etc.)
    @Scheduled(cron = "0 0 2 1 1/1 ? *")
    private void applyInterestRate() {
        List<Savings> savingsAccounts = savingsService.findAll();

        Calendar calendar = Calendar.getInstance();
        int daysOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        savingsAccounts.forEach(savings -> {
            float monthInterests = (savings.getBalance() * (savings.getInterestRate() / 365) * daysOfMonth) / 100;
            savings.deposit(monthInterests);
            savingsService.update(savings);
        });
    }
}
