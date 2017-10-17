package com.cmg.motorcycles.ejb;

public class MotorcycleDetails {

	public int Id;
	public String motorcycle_name;
	public String motorcycle_type;
	public String motorcycle_descrption;
	public String motorcycle_offer;
	public String motorcycle_offer_description;
	public String motorcycle_engine_cc;
	public int motorcycle_odometer_value;
	public int motorcycle_price;
	public String motorcycle_weekly_price;
	public String motorcycle_photo;
	public String motorcycle_manufacturer;
	public String motorcycle_orc_included;
	
	public String getMotorcycle_orc_included() {
		return motorcycle_orc_included;
	}
	public void setMotorcycle_orc_included(String motorcycle_orc_included) {
		this.motorcycle_orc_included = motorcycle_orc_included;
	}

	public String getMotorcycle_manufacturer() {
		return motorcycle_manufacturer;
	}
	public void setMotorcycle_manufacturer(String motorcycle_manufacturer) {
		this.motorcycle_manufacturer = motorcycle_manufacturer;
	}
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getMotorcycle_name() {
		return motorcycle_name;
	}
	public void setMotorcycle_name(String motorcycle_name) {
		this.motorcycle_name = motorcycle_name;
	}
	public String getMotorcycle_type() {
		return motorcycle_type;
	}
	public void setMotorcycle_type(String motorcycle_type) {
		this.motorcycle_type = motorcycle_type;
	}
	public String getMotorcycle_descrption() {
		return motorcycle_descrption;
	}
	public void setMotorcycle_descrption(String motorcycle_descrption) {
		this.motorcycle_descrption = motorcycle_descrption;
	}
	public String getMotorcycle_offer() {
		return motorcycle_offer;
	}
	public void setMotorcycle_offer(String motorcycle_offer) {
		this.motorcycle_offer = motorcycle_offer;
	}
	public String getMotorcycle_offer_description() {
		return motorcycle_offer_description;
	}
	public void setMotorcycle_offer_description(String motorcycle_offer_description) {
		this.motorcycle_offer_description = motorcycle_offer_description;
	}
	public String getMotorcycle_engine_cc() {
		return motorcycle_engine_cc;
	}
	public void setMotorcycle_engine_cc(String motorcycle_engine_cc) {
		this.motorcycle_engine_cc = motorcycle_engine_cc;
	}
	public int getMotorcycle_odometer_value() {
		return motorcycle_odometer_value;
	}
	public void setMotorcycle_odometer_value(int motorcycle_odometer_value) {
		this.motorcycle_odometer_value = motorcycle_odometer_value;
	}
	public int getMotorcycle_price() {
		return motorcycle_price;
	}
	public void setMotorcycle_price(int motorcycle_price) {
		this.motorcycle_price = motorcycle_price;
	}
	public String getMotorcycle_weekly_price() {
		return motorcycle_weekly_price;
	}
	public void setMotorcycle_weekly_price(String motorcycle_weekly_price) {
		this.motorcycle_weekly_price = motorcycle_weekly_price;
	}
	public String getMotorcycle_photo() {
		return motorcycle_photo;
	}
	public void setMotorcycle_photo(String motorcycle_photo) {
		this.motorcycle_photo = motorcycle_photo;
	}
}
