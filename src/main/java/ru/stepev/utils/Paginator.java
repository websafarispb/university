package ru.stepev.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Paginator {
	
	private int itemsPerPage;	
	private int numberOfPages;
	private int numberOfEntities;
	private int currentPage;
	private int offset;
	private String sortBy;

	public Paginator(int numberOfEntities, int currentPage,  String sortBy, int itemsPerPage) {
		this.itemsPerPage = itemsPerPage;
		this.sortBy = sortBy;
		this.numberOfEntities = numberOfEntities;
		this.currentPage = currentPage;
		this.numberOfPages = (numberOfEntities / itemsPerPage);
		if (numberOfEntities % itemsPerPage > 0) {
			numberOfPages++;
		}	
		this.offset = itemsPerPage * (currentPage - 1);
	}
}
