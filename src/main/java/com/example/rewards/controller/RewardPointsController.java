package com.example.rewards.controller;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rewards")
public class RewardPointsController {

    @PostMapping("/calculate")
    public Map<String, Map<String, Long>> calculateRewardPoints(@RequestBody Map<String, Map<String, List<Double>>> transactions) {
        return calculateRewardPointsForThreeMonths(transactions);
    }

    private Map<String, Map<String, Long>> calculateRewardPointsForThreeMonths(Map<String, Map<String, List<Double>>> transactions) {
        Map<String, Map<String, Long>> rewardPointsMap = new HashMap<>();

        for (Map.Entry<String, Map<String, List<Double>>> customerEntry : transactions.entrySet()) {
            String customer = customerEntry.getKey();
            Map<String, List<Double>> transactionsByMonth = customerEntry.getValue();
            Map<String, Long> rewardPointsByMonth = new HashMap<>();

            for (Map.Entry<String, List<Double>> monthEntry : transactionsByMonth.entrySet()) {
                String month = monthEntry.getKey();
                List<Double> transactionAmounts = monthEntry.getValue();

                long rewardPoints = calculateRewardPointsForMonth(transactionAmounts);
                rewardPointsByMonth.put(month, rewardPoints);
            }

            rewardPointsMap.put(customer, rewardPointsByMonth);
        }

        return rewardPointsMap;
    }

    private long calculateRewardPointsForMonth(List<Double> transactionAmounts) {
        long rewardPoints = 0;
        for (Double amount : transactionAmounts) {
            if (amount > 100) {
                rewardPoints += (amount - 100) * 2 + 50;
            } else if (amount > 50) {
                rewardPoints += (amount - 50);
            }
        }
        return rewardPoints;
    }
}
