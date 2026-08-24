package com.steff.fishermanjournal.entity;

import java.time.LocalDateTime;

public class CatchRecord {

	private Long id;
	private String fishermanName;
	private String fishType;
	private int quantity;
	private int weight;
	private String waterBody;
	private RecordStatus status;
	private LocalDateTime createdAt;

	//
	public CatchRecord() {
	}

	//
	public CatchRecord(String fishermanName, String fishType, int quantity, int weight, String waterBody) {
		this.fishermanName = fishermanName;
		this.fishType = fishType;
		this.quantity = quantity;
		this.weight = weight;
		this.waterBody = waterBody;

	}

	//
	public CatchRecord(Long id, String fishermanName, String fishType, int quantity, int weight, String waterBody,
			RecordStatus status, LocalDateTime CreateAt) {
		this.id = id;
		this.fishermanName = fishermanName;
		this.fishType = fishType;
		this.quantity = quantity;
		this.weight = weight;
		this.waterBody = waterBody;
		this.status = status;
		this.createdAt = CreateAt;

	}

	//
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFishermanName() {
		return fishermanName;
	}

	public void setFishermanName(String fishermanName) {
		this.fishermanName = fishermanName;
	}

	public String getFishType() {
		return fishType;
	}

	public void setFishType(String fishType) {
		this.fishType = fishType;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public String getWaterBody() {
		return waterBody;
	}

	public void setWaterBody(String waterBody) {
		this.waterBody = waterBody;
	}

	public RecordStatus getStatus() {
		return status;
	}

	public void setStatus(RecordStatus status) {
		this.status = status;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	//
	public String toString() {
		return "CatchRecord [id=" + id + ", fishermanName=" + fishermanName + ", fishType=" + fishType + ", quantity="
				+ quantity + ", weight=" + weight + ", waterBody=" + waterBody + ", status=" + status + ", createdAt"
				+ createdAt + "]";
	}
}
