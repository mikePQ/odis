import { Injectable } from '@angular/core';

@Injectable()
export class PaginationService {

  constructor() {
  }

  getPager(totalItems: number, currentPage: number = 1, pageSize: number = 3): Pager {
    let totalPages = Math.ceil(totalItems / pageSize);

    let startPage: number, endPage: number;
    if (totalPages <= 10) {
      startPage = 1;
      endPage = totalPages;
    } else {
      if (currentPage <= 6) {
        startPage = 1;
        endPage = 10;
      } else if (currentPage + 4 >= totalPages) {
        startPage = totalPages - 9;
        endPage = totalPages;
      } else {
        startPage = currentPage - 5;
        endPage = currentPage + 4;
      }
    }

    let startIndex = (currentPage - 1) * pageSize;
    let endIndex = Math.min(startIndex + pageSize - 1, totalItems - 1);

    let pages = Array.from(Array(endPage + 1 - startPage), (_, i) => startPage + i);

    return new Pager(currentPage, totalPages, startIndex, endIndex, pages);
  }

}

export class Pager {
  constructor(public currentPage: number = 1,
              public totalPages: number = 1,
              public startIndex: number = 0,
              public endIndex: number = 0,
              public pages: Array<number> = []) {
  }

  static empty(): Pager {
    return new Pager();
  }
}
