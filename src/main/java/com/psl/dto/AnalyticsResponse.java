package com.psl.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnalyticsResponse {

	private long totalOrders;
	private double totalEarning;
	private long totalDelivered;
	private long totalCancelled;
	private int year;
	private String month;
	private int day;
}
