package ru.stepev.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

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
	private int beginOfPagination;
	private int endOfPagination;
	private int offset;
	private String sortedParam;

	public Paginator(int numberOfEntities, int currentPage,  String sortedParam, int itemsPerPage) {
		this.itemsPerPage = itemsPerPage;
		this.sortedParam = sortedParam;
		this.numberOfEntities = numberOfEntities;
		this.currentPage = currentPage;
		this.pageNumbers = new ArrayList<>();
		this.currentPageNumbers = new ArrayList<>();
		this.numberOfPages = (numberOfEntities / itemsPerPage);
		if (numberOfEntities % itemsPerPage > 0) {
			numberOfPages++;
		}	
		for (int i = 1; i <= numberOfPages; i++) {
			pageNumbers.add(i);
		}
		this.offset = itemsPerPage * (currentPage - 1);
	}
}
