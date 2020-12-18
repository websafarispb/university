package ru.stepev.utils;

import java.util.ArrayList;
import java.util.List;

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
	private List<Integer> pageNumbers;
	private List<Integer> currentPageNumbers;
	private int currentPage;
	private int currentBeginPagination;
	private int currentNumberOfPagesForPagination;
	private int beginOfPagination;
	private int endOfPagination;
	private int offset;

	public Paginator(int numberOfEntities, int currentPage, int currentBeginPagination, int itemsPerPage, int currentNumberOfPagesForPagination) {
		this.numberOfEntities = numberOfEntities;
		this.currentPage = currentPage;
		this.currentBeginPagination = currentBeginPagination;
		this.itemsPerPage = itemsPerPage;
		this.pageNumbers = new ArrayList<>();
		this.currentPageNumbers = new ArrayList<>();
		this.numberOfPages = (numberOfEntities / itemsPerPage);
		if (numberOfEntities % itemsPerPage > 0) {
			numberOfPages++;
		}	
		for (int i = 1; i <= numberOfPages; i++) {
			pageNumbers.add(i);
		}
		this.beginOfPagination = currentBeginPagination;
		this.endOfPagination = currentBeginPagination + currentNumberOfPagesForPagination;
		if (endOfPagination > pageNumbers.size()) {
			endOfPagination = pageNumbers.size();
		}
		currentPageNumbers = pageNumbers.subList(beginOfPagination, endOfPagination);
		this.offset = itemsPerPage * (currentPage - 1);
	}
}
