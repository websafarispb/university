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

	private int numbersOfEntityForOnePage;
	private int numberOfPages;
	private int numberOfEntities;
	private List<Integer> pageNumbers;
	private List<Integer> currentPageNumbers;
	private int currentBeginOfEntities;
	private int currentEndOfEntities;
	private int currentPage;
	private int diapasonOfPages;
	private int sizeOfDiapason;
	private int beginOfDiapason;
	private int endOfDiapason;

	public Paginator(int numberOfEntities, int currentPage, int diapasonOfPages, int numbersOfEntityForOnePage, int sizeOfDiapason) {
		this.numberOfEntities = numberOfEntities;
		this.currentPage = currentPage;
		this.diapasonOfPages = diapasonOfPages;
		this.numbersOfEntityForOnePage = numbersOfEntityForOnePage;
		this.pageNumbers = new ArrayList<>();
		this.currentPageNumbers = new ArrayList<>();
		this.numberOfPages = (numberOfEntities / numbersOfEntityForOnePage);
		if (numberOfEntities % numbersOfEntityForOnePage > 0) {
			numberOfPages++;
		}	
		for (int i = 1; i <= numberOfPages; i++) {
			pageNumbers.add(i);
		}
		this.currentBeginOfEntities = ((numbersOfEntityForOnePage * currentPage) - numbersOfEntityForOnePage);
		this.currentEndOfEntities = (numbersOfEntityForOnePage * currentPage);
		if (currentEndOfEntities > numberOfEntities) {
			currentEndOfEntities = numberOfEntities;
		}
		this.beginOfDiapason = diapasonOfPages;
		this.endOfDiapason = diapasonOfPages + sizeOfDiapason;
		if (endOfDiapason > pageNumbers.size()) {
			endOfDiapason = pageNumbers.size();
		}
		currentPageNumbers = pageNumbers.subList(beginOfDiapason, endOfDiapason);
	}
}
